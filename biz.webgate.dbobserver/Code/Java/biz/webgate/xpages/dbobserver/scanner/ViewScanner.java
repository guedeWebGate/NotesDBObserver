package biz.webgate.xpages.dbobserver.scanner;

import java.util.LinkedList;
import java.util.List;

import org.openntf.jaxb.model.dxl.View;

import biz.webgate.xpages.dbobserver.ICodeScanner;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.bulk.ActionListScanner;
import biz.webgate.xpages.dbobserver.scanner.bulk.CodeListScanner;
import biz.webgate.xpages.dbobserver.scanner.bulk.ColumnListScanner;

public enum ViewScanner implements ICodeScanner<View> {
	INSTANCE;

	public List<Hit> scanElement(View element, DesignInfo designInfo) {
		List<Hit> hits = new LinkedList<Hit>();
		CodeListScanner.INSTANCE.scanCodes(hits, element.getCode(), "", designInfo);
		if (element.getGlobals() != null) {
			CodeListScanner.INSTANCE.scanCodes(hits, element.getGlobals().getCode(), "Globals", designInfo);
		}
		if (element.getActionbar() != null) {
			ActionListScanner.INSTANCE.scanActionList(hits, element.getActionbar().getActionOrSharedactionref(), designInfo);
		}
		ColumnListScanner.INSTANCE.scanColumns(hits, element.getColumnOrSharedcolumnref(), designInfo);
		return hits;
	}

	public List<Hit> runScan(Object element,String replicaId) {
		View view = (View) element;
		view.setReplicaid(replicaId);
		DesignInfo designInfo = DesignInfo.buildDesignInfoFromView(view);
		return scanElement(view, designInfo);
	}
}
