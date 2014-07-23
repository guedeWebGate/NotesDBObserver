package biz.webgate.xpages.dbobserver.scanner.bulk;

import java.util.List;

import org.openntf.jaxb.model.dxl.Field;

import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.DesignInfo;

public enum FieldScanner {
	INSTANCE;

	public void scanField(List<Hit> hits, Field field, String path, DesignInfo designInfo) {
		CodeListScanner.INSTANCE.scanCodes(hits, field.getCode(), path+"field(name="+field.getName()+"/", designInfo);

	}
}
