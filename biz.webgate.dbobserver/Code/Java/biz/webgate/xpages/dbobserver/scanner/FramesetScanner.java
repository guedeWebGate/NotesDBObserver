package biz.webgate.xpages.dbobserver.scanner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.openntf.jaxb.model.dxl.Frameset;

import biz.webgate.xpages.dbobserver.ICodeScanner;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.bulk.CodeListScanner;

public enum FramesetScanner implements ICodeScanner<Frameset> {
	INSTANCE;

	public List<Hit> scanElement(Frameset element, DesignInfo designInfo) {
		List<Hit> hits = new LinkedList<Hit>();
		CodeListScanner.INSTANCE.scanCodes(hits, Arrays.asList(element.getCode()), "", designInfo);
		// TODO: Wie kann ich die Frames scannen, damit ich an formeln komme?
		return hits;
	}

	public List<Hit> runScan(Object element, String replicaId) {
		Frameset frameset = (Frameset) element;
		frameset.setReplicaid(replicaId);
		DesignInfo designInfo = DesignInfo.buildDesignInfoFromFrameset(frameset);
		return scanElement(frameset, designInfo);
	}
}
