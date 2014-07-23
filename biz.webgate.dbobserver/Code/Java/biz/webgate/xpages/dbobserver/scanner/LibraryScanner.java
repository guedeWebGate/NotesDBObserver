package biz.webgate.xpages.dbobserver.scanner;

import java.util.LinkedList;
import java.util.List;

import org.openntf.jaxb.model.dxl.Scriptlibrary;

import biz.webgate.xpages.dbobserver.ICodeScanner;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.bulk.CodeListScanner;

public enum LibraryScanner implements ICodeScanner<Scriptlibrary> {
	INSTANCE;

	public List<Hit> scanElement(Scriptlibrary element, DesignInfo designInfo) {
		List<Hit> hits = new LinkedList<Hit>();
		CodeListScanner.INSTANCE.scanCodes(hits, element.getCode(), "", designInfo);
		return hits;
	}
	public List<Hit> runScan(Object element,String replicaId) {
		Scriptlibrary scriptlibrary = (Scriptlibrary) element;
		scriptlibrary.setReplicaid(replicaId);
		DesignInfo designInfo = DesignInfo.buildDesignInfoFromScriptlibrary(scriptlibrary);
		return scanElement(scriptlibrary, designInfo);
	}
}
