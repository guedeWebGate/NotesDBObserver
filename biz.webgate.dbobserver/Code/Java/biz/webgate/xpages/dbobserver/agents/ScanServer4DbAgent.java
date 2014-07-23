package biz.webgate.xpages.dbobserver.agents;

import lotus.domino.Database;
import lotus.domino.DbDirectory;
import lotus.domino.NotesException;
import lotus.domino.Session;

import org.openntf.xpt.agents.XPageAgentJob;
import org.openntf.xpt.agents.annotations.ExecutionMode;
import org.openntf.xpt.agents.annotations.XPagesAgent;

import biz.webgate.xpages.dbobserver.store.DatabaseStorageService;

@XPagesAgent(Alias = "scanServer4Db", Name = "Sync Server 4 Databases", executionMode = ExecutionMode.ON_REQUEST)
public class ScanServer4DbAgent extends XPageAgentJob {

	public ScanServer4DbAgent(String name) {
		super(name);
	}

	@Override
	public int executeCode(Session session, Database db) {
		setTaskCompletion(0);
		setCurrentTaskStatus("Reading DBDirectory");
		try {
			int nCount = 0;
			DbDirectory dbDirectory = session.getDbDirectory(db.getServer());
			Database dbScanNext = dbDirectory.getFirstDatabase(DbDirectory.DATABASE);
			while (dbScanNext != null) {
				Database dbScan = dbScanNext;
				dbScanNext = dbDirectory.getNextDatabase();
				setCurrentTaskStatus("Scanning: " + dbScan.getTitle());
				scanDB(dbScan, db);
				dbScan.recycle();
				nCount++;
				setTaskCompletion(nCount / 10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private void scanDB(Database dbScan, Database dbCurrent) throws NotesException {
		biz.webgate.xpages.dbobserver.bo.Database databaseStub = DatabaseStorageService.getInstance().getByIdFrom(dbScan.getReplicaID(),
				dbCurrent);
		if (databaseStub == null) {
			databaseStub = new biz.webgate.xpages.dbobserver.bo.Database();
			databaseStub.setReplicaID(dbScan.getReplicaID());
			databaseStub.setServer(dbScan.getServer());
			databaseStub.setPath(dbScan.getFilePath());
			databaseStub.setName(dbScan.getTitle());
			DatabaseStorageService.getInstance().saveTo(databaseStub, dbCurrent);
		}
	}

}
