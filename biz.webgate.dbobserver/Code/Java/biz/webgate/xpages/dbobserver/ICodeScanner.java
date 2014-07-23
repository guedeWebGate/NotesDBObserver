package biz.webgate.xpages.dbobserver;

import java.util.List;

import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.DesignInfo;

public interface ICodeScanner<T> {

	public List<Hit> scanElement(T element, DesignInfo designInfo);
	public List<Hit> runScan(Object element, String replicaId);
}
