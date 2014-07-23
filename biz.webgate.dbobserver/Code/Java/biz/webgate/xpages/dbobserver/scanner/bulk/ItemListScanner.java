package biz.webgate.xpages.dbobserver.scanner.bulk;

import java.util.List;

import org.openntf.jaxb.model.domino.DominoUtils;
import org.openntf.jaxb.model.dxl.Item;

import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.bo.PatternType;
import biz.webgate.xpages.dbobserver.scanner.DesignInfo;

import com.ibm.commons.util.StringUtil;

public enum ItemListScanner {
	INSTANCE;

	public void scanItems(List<Hit> hits, List<Item> items, DesignInfo designInfo) {
		for (Item item : items) {
			if (item.getFormula() != null) {
				String sourceCode = DominoUtils.INSTANCE.serializeToString(item.getFormula().getContent());
				if (!StringUtil.isEmpty(sourceCode)) {
					Hit hit = Hit.buildHit(designInfo, "", "Item/" + item.getName(), sourceCode, PatternType.FORMULA);
					hits.add(hit);
				}
			}
		}

	}
}
