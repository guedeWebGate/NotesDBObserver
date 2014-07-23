package biz.webgate.xpages.dbobserver.scanner;

import java.util.LinkedList;
import java.util.List;

import org.openntf.jaxb.model.dxl.Form;

import biz.webgate.xpages.dbobserver.ICodeScanner;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.bulk.ActionListScanner;
import biz.webgate.xpages.dbobserver.scanner.bulk.CodeListScanner;
import biz.webgate.xpages.dbobserver.scanner.bulk.RichTextScanner;

public enum FormScanner implements ICodeScanner<Form> {
	INSTANCE;

	public List<Hit> scanElement(Form element, DesignInfo designInfo) {
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
		Form form = (Form) element;
		form.setReplicaid(replicaId);
		DesignInfo designInfo = DesignInfo.buildDesignInfoFromForm(form);
		return scanElement(form, designInfo);
	}
}
