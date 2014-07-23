package biz.webgate.xpages.dbobserver.scanner;

import java.util.LinkedList;
import java.util.List;

import org.openntf.jaxb.model.dxl.Sharedfield;

import biz.webgate.xpages.dbobserver.ICodeScanner;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.bulk.FieldScanner;

public enum SharedfieldScanner implements ICodeScanner<Sharedfield> {
	INSTANCE;

	public List<Hit> scanElement(Sharedfield element, DesignInfo designInfo) {
		List<Hit> hits = new LinkedList<Hit>();
		FieldScanner.INSTANCE.scanField(hits, element.getField(), "", designInfo);
		return hits;
	}
	public List<Hit> runScan(Object element,String replicaId) {
		Sharedfield sharedfield = (Sharedfield) element;
		sharedfield.setReplicaid(replicaId);
		DesignInfo designInfo = DesignInfo.buildDesignInfoFromSharedfield(sharedfield);
		return scanElement(sharedfield, designInfo);
	}
}
