package biz.webgate.xpages.dbobserver.scanner;

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

import biz.webgate.xpages.dbobserver.bo.DesignElementType;

public class DesignInfo {

	public static DesignInfo buildDesignInfoFromForm(Form form) {
		return buildDesignInfo(form.getReplicaid(), form.getNoteinfo().getNoteid(), form.getName(), form.getAlias(), DesignElementType.FORM);
	}

	public static DesignInfo buildDesignInfoFromAgent(Agent agent) {
		return buildDesignInfo(agent.getReplicaid(), agent.getNoteinfo().getNoteid(), agent.getName(), agent.getAlias(),
				DesignElementType.AGENT);
	}

	public static DesignInfo buildDesignInfo(String repId, String noteId, String name, String alias, DesignElementType type) {
		return new DesignInfo(repId, noteId, name, alias, type);
	}

	public static DesignInfo buildDesignInfoFromDatabaseScript(Databasescript databasescirpt) {
		return buildDesignInfo(databasescirpt.getReplicaid(), databasescirpt.getNoteinfo().getNoteid(), "DatabaseScript", "",
				DesignElementType.DATABASESCRIPT);
	}

	public static DesignInfo buildDesignInfoFromFolder(Folder folder) {
		return buildDesignInfo(folder.getReplicaid(), folder.getNoteinfo().getNoteid(), folder.getName(), folder.getAlias(),
				DesignElementType.FOLDER);
	}

	public static DesignInfo buildDesignInfoFromFrameset(Frameset frameset) {
		return buildDesignInfo(frameset.getReplicaid(), frameset.getNoteinfo().getNoteid(), frameset.getName(), frameset.getAlias(),
				DesignElementType.FRAMESET);
	}

	public static DesignInfo buildDesignInfoFromScriptlibrary(Scriptlibrary scriptlibrary) {
		return buildDesignInfo(scriptlibrary.getReplicaid(), scriptlibrary.getNoteinfo().getNoteid(), scriptlibrary.getName(),
				scriptlibrary.getAlias(), DesignElementType.SCRIPT_LIBRARY);
	}

	public static DesignInfo buildDesignInfoFromOutline(Outline outline) {
		return buildDesignInfo(outline.getReplicaid(), outline.getNoteinfo().getNoteid(), outline.getName(), outline.getAlias(),
				DesignElementType.OUTLINE);
	}

	public static DesignInfo buildDesignInfoFromPage(Page page) {
		return buildDesignInfo(page.getReplicaid(), page.getNoteinfo().getNoteid(), page.getName(), page.getAlias(), DesignElementType.PAGE);
	}

	public static DesignInfo buildDesignInfoFromSharedfield(Sharedfield sharedfield) {
		return buildDesignInfo(sharedfield.getReplicaid(), sharedfield.getNoteinfo().getNoteid(), sharedfield.getName(), sharedfield
				.getAlias(), DesignElementType.SHARADEFIELD);
	}

	public static DesignInfo buildDesignInfoFromSubform(Subform subform) {
		return buildDesignInfo(subform.getReplicaid(), subform.getNoteinfo().getNoteid(), subform.getName(), subform.getAlias(),
				DesignElementType.SUBFORM);
	}

	public static DesignInfo buildDesignInfoFromView(View view) {
		return buildDesignInfo(view.getReplicaid(), view.getNoteinfo().getNoteid(), view.getName(), view.getAlias(), DesignElementType.VIEW);
	}

	public static DesignInfo buildDesignInfoFromWebservice(Webservice webservice) {
		return buildDesignInfo(webservice.getReplicaid(), webservice.getNoteinfo().getNoteid(), webservice.getName(),
				webservice.getAlias(), DesignElementType.WEBSERVICE);
	}

	private DesignInfo(String replicaId, String noteId, String name, String alias, DesignElementType type) {
		super();
		m_ReplicaId = replicaId;
		m_NoteId = noteId;
		m_Name = name == null ? "" : name;
		m_Alias = alias == null ? "" : alias;
		m_Type = type;
	}

	private final String m_ReplicaId;
	private final String m_NoteId;
	private final String m_Name;
	private final String m_Alias;
	private final DesignElementType m_Type;

	public String getReplicaId() {
		return m_ReplicaId;
	}

	public String getNoteId() {
		return m_NoteId;
	}

	public String getName() {
		return m_Name;
	}

	public String getAlias() {
		return m_Alias;
	}

	public DesignElementType getType() {
		return m_Type;
	}

	public String getKey() {
		return m_ReplicaId + "_" + m_NoteId + "_" + m_Type.name();
	}

}
