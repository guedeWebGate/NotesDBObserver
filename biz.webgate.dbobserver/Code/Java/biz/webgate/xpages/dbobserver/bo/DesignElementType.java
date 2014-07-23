package biz.webgate.xpages.dbobserver.bo;

import org.openntf.jaxb.model.dxl.Agent;
import org.openntf.jaxb.model.dxl.Databasescript;
import org.openntf.jaxb.model.dxl.Folder;
import org.openntf.jaxb.model.dxl.Form;
import org.openntf.jaxb.model.dxl.Frameset;
import org.openntf.jaxb.model.dxl.Outline;
import org.openntf.jaxb.model.dxl.Page;
import org.openntf.jaxb.model.dxl.Scriptlibrary;
import org.openntf.jaxb.model.dxl.Sharedfield;
import org.openntf.jaxb.model.dxl.Subform;
import org.openntf.jaxb.model.dxl.View;
import org.openntf.jaxb.model.dxl.Webservice;

import biz.webgate.xpages.dbobserver.ICodeScanner;
import biz.webgate.xpages.dbobserver.scanner.AgentScanner;
import biz.webgate.xpages.dbobserver.scanner.DatabaseScriptScanner;
import biz.webgate.xpages.dbobserver.scanner.FolderScanner;
import biz.webgate.xpages.dbobserver.scanner.FormScanner;
import biz.webgate.xpages.dbobserver.scanner.FramesetScanner;
import biz.webgate.xpages.dbobserver.scanner.LibraryScanner;
import biz.webgate.xpages.dbobserver.scanner.OutlineScanner;
import biz.webgate.xpages.dbobserver.scanner.PageScanner;
import biz.webgate.xpages.dbobserver.scanner.SharedfieldScanner;
import biz.webgate.xpages.dbobserver.scanner.SubFormScanner;
import biz.webgate.xpages.dbobserver.scanner.ViewScanner;
import biz.webgate.xpages.dbobserver.scanner.WebServiceScanner;

public enum DesignElementType {
	AGENT(AgentScanner.INSTANCE, Agent.class), SCRIPT_LIBRARY(LibraryScanner.INSTANCE, Scriptlibrary.class), WEBSERVICE(
			WebServiceScanner.INSTANCE, Webservice.class), SUBFORM(SubFormScanner.INSTANCE, Subform.class), FORM(FormScanner.INSTANCE,
			Form.class), VIEW(ViewScanner.INSTANCE, View.class), FOLDER(FolderScanner.INSTANCE, Folder.class), PAGE(PageScanner.INSTANCE,
			Page.class), FRAMESET(FramesetScanner.INSTANCE, Frameset.class), OUTLINE(OutlineScanner.INSTANCE, Outline.class), DATABASESCRIPT(
			DatabaseScriptScanner.INSTANCE, Databasescript.class), SHARADEFIELD(SharedfieldScanner.INSTANCE, Sharedfield.class);
	private final ICodeScanner<?> m_Scaner;
	private final Class<?> m_DxlClass;

	private DesignElementType(ICodeScanner<?> scaner, Class<?> dxlClass) {
		m_Scaner = scaner;
		m_DxlClass = dxlClass;
	}

	public Class<?> getDxlClass() {
		return m_DxlClass;
	}

	public ICodeScanner<?> getCodeScaner() {
		return m_Scaner;
	}

	public static DesignElementType getDesignType(Object obj) {
		for (DesignElementType de : values()) {
			if (de.getDxlClass() == obj.getClass()) {
				return de;
			}
		}
		return null;
	}
}
