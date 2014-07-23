package biz.webgate.xpages.dbobserver.scanner;

import java.util.LinkedList;
import java.util.List;

import org.openntf.jaxb.model.dxl.Databasescript;

import biz.webgate.xpages.dbobserver.ICodeScanner;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.bulk.CodeListScanner;

public enum DatabaseScriptScanner implements ICodeScanner<Databasescript> {
	INSTANCE;

	public List<Hit> scanElement(Databasescript element, DesignInfo designInfo) {
		List<Hit> hits = new LinkedList<Hit>();
		CodeListScanner.INSTANCE.scanCodes(hits, element.getCode(), "", designInfo);
		return hits;
	}

	public List<Hit> runScan(Object element,String replicaId) {
		Databasescript databasescirpt = (Databasescript)element;
		databasescirpt.setReplicaid(replicaId);
		DesignInfo designInfo = DesignInfo.buildDesignInfoFromDatabaseScript(databasescirpt); 
		return scanElement(databasescirpt, designInfo);
	}

}
