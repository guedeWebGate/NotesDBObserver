package biz.webgate.xpages.dbobserver.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.openntf.xpt.core.dss.annotations.DominoEntity;
import org.openntf.xpt.core.dss.annotations.DominoStore;

import biz.webgate.xpages.dbobserver.scanner.DesignInfo;

@DominoStore(Form = "frmHit", PrimaryFieldClass = String.class, PrimaryKeyField = "ID", View = "lupHitByID")
public class Hit implements Serializable {

	public static Hit buildHit(DesignInfo designInfo, String event, String path, String content, PatternType pattern) {
		Hit hit = new Hit();
		hit.m_ID = designInfo.getKey() + "_" + event + "_" + path + "_" + pattern.name();
		hit.m_DesignAlias = designInfo.getAlias();
		hit.m_DesignElementType = designInfo.getType();
		hit.m_DesignName = designInfo.getName();
		hit.m_NoteID = designInfo.getNoteId();
		hit.m_Event = event;
		hit.m_HitContent = content;
		hit.m_PatternType = pattern;
		hit.m_ReplicaID = designInfo.getReplicaId();
		hit.m_Path = path;
		return hit;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DominoEntity(FieldName = "ID")
	private String m_ID;
	@DominoEntity(FieldName = "ReplicaID")
	private String m_ReplicaID;
	@DominoEntity(FieldName = "NoteID")
	private String m_NoteID;
	@DominoEntity(FieldName = "DesignAlias")
	private String m_DesignAlias;
	@DominoEntity(FieldName = "DesignName")
	private String m_DesignName;
	@DominoEntity(FieldName = "DesignElementType")
	private DesignElementType m_DesignElementType;
	@DominoEntity(FieldName = "Event")
	private String m_Event;
	@DominoEntity(FieldName = "HitContent")
	private String m_HitContent;
	@DominoEntity(FieldName = "PatternType")
	private PatternType m_PatternType;
	@DominoEntity(FieldName = "HitReason")
	private List<String> m_HitReason;

	@DominoEntity(FieldName = "Path")
	private String m_Path;

	public String getID() {
		return m_ID;
	}

	public void setID(String id) {
		m_ID = id;
	}

	public String getReplicaID() {
		return m_ReplicaID;
	}

	public void setReplicaID(String replicaID) {
		m_ReplicaID = replicaID;
	}

	public String getDesignAlias() {
		return m_DesignAlias;
	}

	public void setDesignAlias(String designAlias) {
		m_DesignAlias = designAlias;
	}

	public String getDesignName() {
		return m_DesignName;
	}

	public void setDesignName(String designName) {
		m_DesignName = designName;
	}

	public String getEvent() {
		return m_Event;
	}

	public void setEvent(String event) {
		m_Event = event;
	}

	public String getHitContent() {
		return m_HitContent;
	}

	public void setHitContent(String hitContent) {
		m_HitContent = hitContent;
	}

	public PatternType getPatternType() {
		return m_PatternType;
	}

	public void setPatternType(PatternType patternType) {
		m_PatternType = patternType;
	}

	public String getPath() {
		return m_Path;
	}

	public void setPath(String path) {
		m_Path = path;
	}

	public String getNoteID() {
		return m_NoteID;
	}

	public void setNoteID(String noteID) {
		m_NoteID = noteID;
	}

	public DesignElementType getDesignElementType() {
		return m_DesignElementType;
	}

	public void setDesignElementType(DesignElementType designElementType) {
		m_DesignElementType = designElementType;
	}

	public void resetHitReason() {
		m_HitReason = new ArrayList<String>();
	}

	public void setHitReason(List<String> reason) {
		m_HitReason = reason;
	}

	public List<String> getHitReason() {
		return m_HitReason;
	}

	public void addHitReason(String reason, Importance imp) {
		if (m_HitReason == null) {
			m_HitReason = new ArrayList<String>();
		}
		m_HitReason.add(reason + "$" + imp.name());
	}

	public boolean hasHitReason() {
		return m_HitReason != null && m_HitReason.size() > 0;
	}

	public String getHitContentHTML() {
		String contentHTML = m_HitContent;
		if (hasHitReason()) {
			for (String reason : m_HitReason) {
				System.out.println(reason);
				String[] reasonImp = reason.split("\\$");
				String pattern = reasonImp[0].replace("(", "\\(");
				System.out.println(pattern);
				contentHTML = contentHTML.replaceAll("(?i)("+pattern+")", "<b>$1</b>");
			}
		}
		contentHTML = contentHTML.replace("\n", "<br />");
		return contentHTML;
	}
}
