package biz.webgate.xpages.dbobserver.scanner;

import java.util.LinkedList;
import java.util.List;

import org.openntf.jaxb.model.dxl.Folder;

import biz.webgate.xpages.dbobserver.ICodeScanner;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.bulk.ActionListScanner;
import biz.webgate.xpages.dbobserver.scanner.bulk.CodeListScanner;
import biz.webgate.xpages.dbobserver.scanner.bulk.ColumnListScanner;

public enum FolderScanner implements ICodeScanner<Folder> {
	INSTANCE;

	public List<Hit> scanElement(Folder element, DesignInfo designInfo) {
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
		Folder folder = (Folder) element;
		folder.setReplicaid(replicaId);
		DesignInfo designInfo = DesignInfo.buildDesignInfoFromFolder(folder);
		return scanElement(folder, designInfo);
	}
}
