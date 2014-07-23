package biz.webgate.xpages.dbobserver.bo;

import org.openntf.jaxb.model.domino.DominoUtils;
import org.openntf.jaxb.model.dxl.Code;
import org.openntf.jaxb.model.dxl.Java;
import org.openntf.jaxb.model.dxl.Javaarchive;
import org.openntf.jaxb.model.dxl.Javaresource;

public enum PatternType {
	FORMULA, JAVA, LOTUSSCRIPT, JAVASCRIPT;

	public String getCode(Code code) {
		if (code == null) {
			return "";
		}
		if (this == FORMULA) {
			if (code.getFormula() != null) {
				return DominoUtils.INSTANCE.serializeToString(code.getFormula().getContent());
			}
		}
		if (this == LOTUSSCRIPT) {
			if (code.getLotusscript() != null) {
				return DominoUtils.INSTANCE.serializeToString(code.getLotusscript().getContent());
			}
		}
		if (this == JAVASCRIPT) {
			if (code.getJavascript() != null) {
				return DominoUtils.INSTANCE.serializeToString(code.getJavascript().getContent());
			}
		}
		if (this == JAVA) {
			StringBuilder javaCode = new StringBuilder();
			if (code.getJavaproject() != null) {
				for (Object obj : code.getJavaproject().getJavaOrJavaresourceOrJavaarchive()) {
					if (obj instanceof Java) {
						Java javaElement = (Java) obj;
						javaCode.append("Name: " + javaElement.getName() + "\n");
						javaCode.append(DominoUtils.INSTANCE.serializeToString(javaElement.getContent()));
					}
					if (obj instanceof Javaresource) {
						Javaresource jr = (Javaresource) obj;
						javaCode.append("JAVA RESOURCE Name: " + jr.getName() + "\n");
						javaCode.append(jr.getValue());

					}
					if (obj instanceof Javaarchive) {
						Javaarchive jar = (Javaarchive) obj;
						javaCode.append("JAR RESOURCE Name: " + jar.getName() + "\n");
						javaCode.append(jar.getValue());

					}
				}
			}
			return javaCode.toString();
		}
		return "";
	}
}
