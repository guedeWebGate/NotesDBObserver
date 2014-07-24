package biz.webgate.xpages.dbobserver.api;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.openntf.xpt.core.dss.SingleObjectStore;

import biz.webgate.xpages.dbobserver.DBObserverException;
import biz.webgate.xpages.dbobserver.DXLHelper;
import biz.webgate.xpages.dbobserver.bo.Database;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.bo.HitActionItem;
import biz.webgate.xpages.dbobserver.bo.HitActionType;
import biz.webgate.xpages.dbobserver.bo.ScanStatus;
import biz.webgate.xpages.dbobserver.bo.Scope;
import biz.webgate.xpages.dbobserver.bo.SearchPattern;
import biz.webgate.xpages.dbobserver.store.DatabaseStorageService;
import biz.webgate.xpages.dbobserver.store.HitActionItemStorageService;
import biz.webgate.xpages.dbobserver.store.SearchPatternStorageService;
import biz.webgate.xpages.dbobserver.util.ACLOpener;

public class AdminSessionFacade {

	private static final String BEAN_NAME = "adminBean";
	private static final List<String> OWNER_FIELDS = Arrays.asList("DevResponsible", "BusinessOwner");

	public static AdminSessionFacade get(FacesContext context) {
		AdminSessionFacade bean = (AdminSessionFacade) context.getApplication().getVariableResolver().resolveVariable(context, BEAN_NAME);
		return bean;
	}

	public static AdminSessionFacade get() {
		return get(FacesContext.getCurrentInstance());
	}

	public List<SearchPattern> getAllSearchPatterns() {
		return SearchPatternStorageService.getInstance().getAll("lupSearchPatternById");
	}

	public SingleObjectStore<SearchPattern> getSearchPatternSOS() {
		return new SingleObjectStore<SearchPattern>(SearchPatternStorageService.getInstance());
	}

	public List<Database> getAllDatabases() {
		return DatabaseStorageService.getInstance().getAll("lupDbById");
	}

	public List<Database> getMyDatabases() {

		return DatabaseStorageService.getInstance().getAllMyObjects("lupDbById", OWNER_FIELDS);
	}

	public List<Database> getDatabasesByScope(Scope scope) {
		return DatabaseStorageService.getInstance().getObjectsByForeignId(scope.name(), "lupDbByScope");
	}

	public SingleObjectStore<Database> getDatabaseSOS() {
		return new SingleObjectStore<Database>(DatabaseStorageService.getInstance());
	}

	public void setDbScope(String replicaID, String scopeName) {
		Database db = DatabaseStorageService.getInstance().getById(replicaID);
		Scope scope = getDBScope(scopeName);
		db.setScope(scope);
		DatabaseStorageService.getInstance().save(db);
	}

	public Scope getDBScope(String name) {
		try {
			return Scope.valueOf(name);
		} catch (Exception e) {
		}
		return Scope.NORMAL;
	}

	public static Map<String, String> getQueryMap(String query) {
		String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			map.put(name, value);
		}
		return map;
	}

	public void scanDB(Database scanDB) {
		try {
			if (ACLOpener.INSTANCE.openACL(scanDB)) {
				List<Hit> hits = DXLHelper.INSTANCE.buildTree(scanDB.getServer(), scanDB.getPath(), getAllSearchPatterns(), scanDB
						.getReplicaID());
				scanDB.setCodeElementsCount(hits.size());
				ACLOpener.INSTANCE.closeACL(scanDB);
				scanDB.setScanStatus(ScanStatus.SCANNED);
				updateActionItemCount(scanDB);
				DatabaseStorageService.getInstance().save(scanDB);
			}
		} catch (DBObserverException dbOe) {
			scanDB.setScanStatus(ScanStatus.ERROR);
			scanDB.setError(dbOe.getMessage());
			scanDB.setErrorLog(dbOe.getLog());
			scanDB.setErrorLogComment(dbOe.getLogComment());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			dbOe.printStackTrace(pw);
			scanDB.setStackTrace(sw.toString());
			DatabaseStorageService.getInstance().save(scanDB);
		} catch (Throwable t) {
			t.printStackTrace();
			scanDB.setScanStatus(ScanStatus.ERROR);
			scanDB.setError(t.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			scanDB.setStackTrace(sw.toString());
			DatabaseStorageService.getInstance().save(scanDB);
		}
	}

	public void updateActionItemCount(Database scanDB) {
		List<HitActionItem> actionItems = HitActionItemStorageService.getInstance().getObjectsByForeignId(scanDB.getReplicaID(),
				"lupHitActionItemByReplicaID");
		scanDB.setTotalActionItems(actionItems.size());
		int openItem = 0;
		for (HitActionItem hai : actionItems) {
			if (hai.getHitActionType() == HitActionType.UNDER_INVESTIGATION) {
				openItem++;
			}
		}
		scanDB.setOpenActionItems(openItem);
	}
}
