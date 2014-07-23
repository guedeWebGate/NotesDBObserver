package biz.webgate.xpages.dbobserver.bo;

import java.io.Serializable;

import org.openntf.xpt.core.dss.annotations.DominoEntity;
import org.openntf.xpt.core.dss.annotations.DominoStore;

@DominoStore(Form = "frmSearchPattern", PrimaryFieldClass = String.class, PrimaryKeyField = "ID", View = "lupSearchPatternByID")
public class SearchPattern implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DominoEntity(FieldName = "ID")
	private String m_ID;
	@DominoEntity(FieldName = "Pattern")
	private String m_Pattern;
	@DominoEntity(FieldName = "Type")
	private PatternType m_Type = PatternType.FORMULA;
	@DominoEntity(FieldName = "Importance")
	private Importance m_Importance = Importance.INFORMATIONAL;

	public String getID() {
		return m_ID;
	}

	public void setID(String id) {
		m_ID = id;
	}

	public String getPattern() {
		return m_Pattern;
	}

	public void setPattern(String pattern) {
		m_Pattern = pattern;
	}

	public PatternType getType() {
		return m_Type;
	}

	public void setType(PatternType type) {
		m_Type = type;
	}

	public String getTypeTXT() {
		return m_Type.name();
	}

	public void setTypeTXT(String type) {
		m_Type = PatternType.valueOf(type);
	}

	public Importance getImportance() {
		return m_Importance;
	}

	public void setImportance(Importance importance) {
		m_Importance = importance;
	}

	public String getImportanceTXT() {
		return m_Importance.name();
	}

	public void setImportanceTXT(String importance) {
		m_Importance = Importance.valueOf(importance);
	}

}
