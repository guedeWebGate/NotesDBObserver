package biz.webgate.xpages.dbobserver.bo;

import java.util.ArrayList;
import java.util.List;

import org.openntf.jaxb.model.domino.DominoUtils;
import org.openntf.jaxb.model.dxl.Code;
import org.openntf.jaxb.model.dxl.Java;
import org.openntf.jaxb.model.dxl.Javaarchive;
import org.openntf.jaxb.model.dxl.Javaresource;

public enum PatternType {
	FORMULA, JAVA, LOTUSSCRIPT, JAVASCRIPT;

	public List<CodeFragment> getCode(Code code) {
		List<CodeFragment> fragments = new ArrayList<CodeFragment>();
		if (code == null) {
			return fragments;
		}
		if (this == FORMULA) {
			if (code.getFormula() != null) {
				fragments.add(new CodeFragment("", DominoUtils.INSTANCE.serializeToString(code.getFormula().getContent())));
			}
		}
		if (this == LOTUSSCRIPT) {
			if (code.getLotusscript() != null) {
				fragments.add(new CodeFragment("", DominoUtils.INSTANCE.serializeToString(code.getLotusscript().getContent())));
			}
		}
		if (this == JAVASCRIPT) {
			if (code.getJavascript() != null) {
				fragments.add(new CodeFragment("", DominoUtils.INSTANCE.serializeToString(code.getJavascript().getContent())));
			}
		}
		if (this == JAVA) {
			if (code.getJavaproject() != null) {
				for (Object obj : code.getJavaproject().getJavaOrJavaresourceOrJavaarchive()) {
					System.out.println(obj.getClass());
					if (obj instanceof Java) {
						Java javaElement = (Java) obj;
						fragments.add(new CodeFragment(javaElement.getName(), DominoUtils.INSTANCE.serializeToString(javaElement
								.getContent())));
					}
					if (obj instanceof Javaresource) {
						Javaresource jr = (Javaresource) obj;
						fragments.add(new CodeFragment(jr.getName(), "BINARYDATA"));
					}
					if (obj instanceof Javaarchive) {
						Javaarchive jar = (Javaarchive) obj;
						fragments.add(new CodeFragment(jar.getName(), "BINARYDATA"));
					}
				}
			}
		}
		return fragments;
	}
}
