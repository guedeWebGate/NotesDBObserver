package biz.webgate.xpages.dbobserver.bo;

import java.io.Serializable;

import org.openntf.xpt.core.dss.annotations.DominoEntity;
import org.openntf.xpt.core.dss.annotations.DominoStore;

@DominoStore(Form = "frmHitActionItem", PrimaryKeyField = "ID", PrimaryFieldClass = String.class, View = "lupHitActionItemByID")
public class HitActionItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DominoEntity(FieldName = "ID")
	private String m_ID;
	@DominoEntity(FieldName = "ReplicaId")
	private String m_ReplicaId;
	@DominoEntity(FieldName = "Instructions")
	private String m_Instructions;
	@DominoEntity(FieldName = "HitActionType")
	private HitActionType m_HitActionType = HitActionType.UNDER_INVESTIGATION;

	public String getID() {
		return m_ID;
	}

	public void setID(String id) {
		m_ID = id;
	}

	public String getReplicaId() {
		return m_ReplicaId;
	}

	public void setReplicaId(String replicaId) {
		m_ReplicaId = replicaId;
	}

	public String getInstructions() {
		return m_Instructions;
	}

	public void setInstructions(String instructions) {
		m_Instructions = instructions;
	}

	public HitActionType getHitActionType() {
		return m_HitActionType;
	}

	public void setHitActionType(HitActionType hitActionType) {
		m_HitActionType = hitActionType;
	}

}
