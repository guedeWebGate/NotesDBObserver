package biz.webgate.xpages.dbobserver.scanner.bulk;

import java.util.Arrays;
import java.util.List;

import org.openntf.jaxb.model.domino.DominoUtils;
import org.openntf.jaxb.model.dxl.Actionhotspot;
import org.openntf.jaxb.model.dxl.Attachmentref;
import org.openntf.jaxb.model.dxl.Button;
import org.openntf.jaxb.model.dxl.Code;
import org.openntf.jaxb.model.dxl.Computedtext;
import org.openntf.jaxb.model.dxl.Databaselink;
import org.openntf.jaxb.model.dxl.Doclink;
import org.openntf.jaxb.model.dxl.Embeddednavigator;
import org.openntf.jaxb.model.dxl.Field;
import org.openntf.jaxb.model.dxl.Formula;
import org.openntf.jaxb.model.dxl.Layer;
import org.openntf.jaxb.model.dxl.Namedelementlink;
import org.openntf.jaxb.model.dxl.Objectref;
import org.openntf.jaxb.model.dxl.Par;
import org.openntf.jaxb.model.dxl.Pardef;
import org.openntf.jaxb.model.dxl.Picture;
import org.openntf.jaxb.model.dxl.Popup;
import org.openntf.jaxb.model.dxl.Region;
import org.openntf.jaxb.model.dxl.Richtext;
import org.openntf.jaxb.model.dxl.Run;
import org.openntf.jaxb.model.dxl.Span;
import org.openntf.jaxb.model.dxl.Table;
import org.openntf.jaxb.model.dxl.Tablecell;
import org.openntf.jaxb.model.dxl.Tablerow;
import org.openntf.jaxb.model.dxl.Urllink;
import org.openntf.jaxb.model.dxl.Viewlink;

import com.ibm.commons.util.StringUtil;

import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.bo.PatternType;
import biz.webgate.xpages.dbobserver.scanner.DesignInfo;

public enum RichTextScanner {
	INSTANCE;

	public void scanRichTextItem(List<Hit> hits, Richtext richtext, DesignInfo designInfo) {
		String path = "body/";
		scanParPardefTable(hits, richtext.getParOrPardefOrTable(), designInfo, path);
	}

	private void scanParPardefTable(List<Hit> hits, List<Object> parPardefTable, DesignInfo designInfo, String path) {
		for (Object obj : parPardefTable) {
			if (obj instanceof Par) {
				Par par = (Par) obj;
				scanParContentList(hits, par.getContent(), path, designInfo);
			}
			if (obj instanceof Pardef) {
				Pardef pardef = (Pardef) obj;
				scanParDef(hits, pardef, path, designInfo);
			}
			if (obj instanceof Table) {
				Table table = (Table) obj;
				scanTable(hits, table, path, designInfo);
			}
		}
	}

	private void scanTable(final List<Hit> hits, Table table, final String path, final DesignInfo designInfo) {
		for (Tablerow row : table.getTablerow()) {
			for (Tablecell cell : row.getTablecell()) {
				scanParPardefTable(hits, cell.getParOrPardefOrTable(), designInfo, path + "table/row/cell/");
			}
		}

	}

	private void scanParDef(final List<Hit> hits, final Pardef pardef, final String path, final DesignInfo designInfo) {
		CodeListScanner.INSTANCE.scanCodes(hits, Arrays.asList(pardef.getCode()), path + "pardef(=" + pardef.getId() + ")/", designInfo);
	}

	private void scanParContentList(final List<Hit> hits, final List<?> content, final String path, final DesignInfo designInfo) {
		for (Object obj : content) {
			
			//PLACEHOLDERS
			if (obj instanceof Region) {
				scanParContentList(hits, ((Region) obj).getContent(), path + "region/", designInfo);
			}
			if (obj instanceof Run) {
				scanParContentList(hits, ((Run) obj).getContent(), path + "run/", designInfo);
			}
			if (obj instanceof Urllink) {
				scanParContentList(hits, ((Urllink) obj).getContent(), path + "urllink/", designInfo);
			}

			if (obj instanceof Attachmentref) {
				scanParContentList(hits, ((Attachmentref) obj).getContent(), path + "attachmentref/", designInfo);
			}

			if (obj instanceof Databaselink) {
				// TODO: Record DB Link
				scanParContentList(hits, ((Databaselink) obj).getContent(), path + "databaselink/", designInfo);
			}
			if (obj instanceof Layer) {
				scanParPardefTable(hits, ((Layer) obj).getParOrPardefOrTable(), designInfo, path + "layer/");

			}
			if (obj instanceof Objectref) {
				scanParContentList(hits, ((Objectref) obj).getRunOrBreakOrField(), path + "objectref/", designInfo);
			}
			if (obj instanceof Span) {
				scanParContentList(hits, ((Span) obj).getContent(), path + "span/", designInfo);
			}
			if (obj instanceof Popup) {
				scanParContentList(hits, ((Popup) obj).getContent(), path + "popup/", designInfo);
			}
			if (obj instanceof Viewlink) {
				scanParContentList(hits, ((Viewlink) obj).getContent(), path + "viewlink/", designInfo);
			}
			if (obj instanceof Button) {
				scanParContentList(hits, ((Button) obj).getContent(), path + "button(name=" + ((Button) obj).getName() + ")/", designInfo);
			}
			if (obj instanceof Doclink) {
				scanParContentList(hits, ((Viewlink) obj).getContent(), path + "doclink/", designInfo);
			}
			if (obj instanceof Namedelementlink) {
				scanParContentList(hits, ((Namedelementlink) obj).getContent(), path + "namedelementlink/", designInfo);
			}
			if (obj instanceof Actionhotspot) {
				scanParContentList(hits, ((Actionhotspot) obj).getContent(), path + "actionhotspot/", designInfo);
			}

			// finalElements
			if (obj instanceof Embeddednavigator) {
				Embeddednavigator nav = (Embeddednavigator) obj;
				CodeListScanner.INSTANCE.scanCodes(hits, Arrays.asList(nav.getCode()), path + "embeddednavigator/", designInfo);
			}
			if (obj instanceof Field) {
				Field field = (Field) obj;
				FieldScanner.INSTANCE.scanField(hits, field, path, designInfo);
			}
			if (obj instanceof Picture) {
				Picture picture = (Picture) obj;
				CodeListScanner.INSTANCE.scanCodes(hits, picture.getCode(), path + "picture(name=" + picture.getName() + ")/", designInfo);
			}
			if (obj instanceof Computedtext) {
				Computedtext computedText = (Computedtext) obj;
				CodeListScanner.INSTANCE.scanCodes(hits, Arrays.asList(computedText.getCode()), path + "computedtest/", designInfo);
			}
			if (obj instanceof Code) {
				Code code = ((Code) obj);
				CodeListScanner.INSTANCE.scanCodes(hits, Arrays.asList(code), path + "computedtest/", designInfo);
			}
			if (obj instanceof Formula) {
				Formula formula = ((Formula) obj);
				String sc = DominoUtils.INSTANCE.serializeToString(formula.getContent());
				if (!StringUtil.isEmpty(sc)) {
					Hit hit = Hit.buildHit(designInfo, "", path + "formula", sc, PatternType.FORMULA);
					hits.add(hit);
				}
			}
		}
	}
}
