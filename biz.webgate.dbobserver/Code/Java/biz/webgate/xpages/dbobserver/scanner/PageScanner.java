package biz.webgate.xpages.dbobserver.scanner;

import java.util.LinkedList;
import java.util.List;

import org.openntf.jaxb.model.dxl.Page;

import biz.webgate.xpages.dbobserver.ICodeScanner;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.bulk.ActionListScanner;
import biz.webgate.xpages.dbobserver.scanner.bulk.CodeListScanner;
import biz.webgate.xpages.dbobserver.scanner.bulk.RichTextScanner;

public enum PageScanner implements ICodeScanner<Page> {
	INSTANCE;

	public List<Hit> scanElement(Page element, DesignInfo designInfo) {
		List<Hit> hits = new LinkedList<Hit>();
		CodeListScanner.INSTANCE.scanCodes(hits, element.getCode(), "", designInfo);
		if (element.getGlobals() != null) {
			CodeListScanner.INSTANCE.scanCodes(hits, element.getGlobals().getCode(), "global", designInfo);
		}
		if (element.getActionbar() != null) {
			ActionListScanner.INSTANCE.scanActionList(hits, element.getActionbar().getActionOrSharedactionref(), designInfo);
		}
		if (element.getBody() != null) {
			RichTextScanner.INSTANCE.scanRichTextItem(hits, element.getBody().getRichtext(), designInfo);
		}
		return hits;
	}

	public List<Hit> runScan(Object element,String replicaId) {
		Page page = (Page) element;
		page.setReplicaid(replicaId);
		DesignInfo designInfo = DesignInfo.buildDesignInfoFromPage(page);
		return scanElement(page, designInfo);
	}
}
