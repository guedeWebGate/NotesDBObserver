package biz.webgate.xpages.dbobserver.scanner;

import java.util.LinkedList;
import java.util.List;

import org.openntf.jaxb.model.dxl.Outline;
import org.openntf.jaxb.model.dxl.Outlineentry;

import biz.webgate.xpages.dbobserver.ICodeScanner;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.bulk.CodeListScanner;

public enum OutlineScanner implements ICodeScanner<Outline> {
	INSTANCE;

	public List<Hit> scanElement(Outline element, DesignInfo designInfo) {
		List<Hit> hits = new LinkedList<Hit>();
		for (Outlineentry entry : element.getOutlineentry()) {
			CodeListScanner.INSTANCE.scanCodes(hits, entry.getCode(), "OutlineEntry/name=" + entry.getLabel(), designInfo);
		}
		return hits;
	}
	public List<Hit> runScan(Object element,String replicaId) {
		Outline outline = (Outline) element;
		outline.setReplicaid(replicaId);
		DesignInfo designInfo = DesignInfo.buildDesignInfoFromOutline(outline);
		return scanElement(outline, designInfo);
	}
}
