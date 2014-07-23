package biz.webgate.xpages.dbobserver.scanner.bulk;

import java.util.List;

import org.openntf.jaxb.model.dxl.Code;

import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.bo.PatternType;
import biz.webgate.xpages.dbobserver.scanner.DesignInfo;

import com.ibm.commons.util.StringUtil;

public enum CodeListScanner {
	INSTANCE;

	public void scanCodes(List<Hit> lstRC, List<Code> codes, String path, DesignInfo designInfo) {
		for (Code code : codes) {
			for (PatternType patternType : PatternType.values()) {
				String sourceCode = patternType.getCode(code);
				if (!StringUtil.isEmpty(sourceCode)) {
					Hit hit = Hit.buildHit(designInfo, code.getEvent(), path, sourceCode, patternType);
					lstRC.add(hit);
				}
			}
		}
	}
}