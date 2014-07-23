package biz.webgate.xpages.dbobserver.scanner.bulk;

import java.util.List;

import org.openntf.jaxb.model.dxl.Column;
import org.openntf.jaxb.model.dxl.Sharedcolumnref;

import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.DesignInfo;

public enum ColumnListScanner {
	INSTANCE;

	public void scanColumns(List<Hit> hits, List<Object> columnOrSharedcolumnref, DesignInfo designInfo) {
		for (Object obj : columnOrSharedcolumnref) {
			if (obj instanceof Column) {
				Column column = (Column) obj;
				CodeListScanner.INSTANCE
						.scanCodes(hits, column.getCode(), "Column/name=" + column.getColumnheader().getTitle(), designInfo);
			}
			if (obj instanceof Sharedcolumnref) {
				Sharedcolumnref scref = (Sharedcolumnref) obj;
				if (scref.getColumn() != null) {
					CodeListScanner.INSTANCE.scanCodes(hits, scref.getColumn().getCode(), "SharedColumn/name="
							+ scref.getColumn().getColumnheader().getTitle(), designInfo);
				}
			}
		}

	}
}
