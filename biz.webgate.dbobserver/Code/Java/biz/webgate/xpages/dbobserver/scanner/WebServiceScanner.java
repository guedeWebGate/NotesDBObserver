package biz.webgate.xpages.dbobserver.scanner;

import java.util.LinkedList;
import java.util.List;

import org.openntf.jaxb.model.dxl.Webservice;

import biz.webgate.xpages.dbobserver.ICodeScanner;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.bulk.CodeListScanner;

public enum WebServiceScanner implements ICodeScanner<Webservice> {
	INSTANCE;

	public List<Hit> scanElement(Webservice element, DesignInfo designInfo) {
		List<Hit> hits = new LinkedList<Hit>();
		CodeListScanner.INSTANCE.scanCodes(hits, element.getCode(), "", designInfo);
		return hits;
	}

	public List<Hit> runScan(Object element, String replicaId) {
		Webservice webservice = (Webservice) element;
		webservice.setReplicaid(replicaId);
		DesignInfo designInfo = DesignInfo.buildDesignInfoFromWebservice(webservice);
		return scanElement(webservice, designInfo);
	}
}
