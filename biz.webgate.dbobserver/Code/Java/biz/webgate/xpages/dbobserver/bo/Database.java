package biz.webgate.xpages.dbobserver.bo;

import java.io.Serializable;

import lotus.domino.Name;

import org.openntf.xpt.core.dss.annotations.DominoEntity;
import org.openntf.xpt.core.dss.annotations.DominoStore;

import com.ibm.xsp.extlib.util.ExtLibUtil;
import com.ibm.xsp.http.MimeMultipart;

@DominoStore(Form = "frmDatabase", PrimaryFieldClass = String.class, PrimaryKeyField = "ReplicaID", View = "lupDbByID")
public class Database implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DominoEntity(FieldName = "Server")
	private String m_Server;
	@DominoEntity(FieldName = "Path")
	private String m_Path;
	@DominoEntity(FieldName = "ReplicaID")
	private String m_ReplicaID;
	@DominoEntity(FieldName = "Name")
	private String m_Name;
	
	@DominoEntity(FieldName = "Body")
	private MimeMultipart m_Body;
	@DominoEntity(FieldName = "Scope")
	private Scope m_Scope = Scope.NORMAL;
	
	@DominoEntity(FieldName = "ScanStatus")
	private ScanStatus m_ScanStatus = ScanStatus.STUB;
	@DominoEntity(FieldName = "DevResponsible")
	private String m_DevResponsible;
	@DominoEntity(FieldName = "BusinessOwner")
	private String m_BusinessOwner;

	@DominoEntity(FieldName="@NoteId", isFormula=true)
	private String m_DocNoteId;
	public String getServer() {
		return m_Server;
	}

	public void setServer(String server) {
		m_Server = server;
	}

	public String getServerCN() {
		String serverName = m_Server;
		try {
			Name notesServerName = ExtLibUtil.getCurrentSession().createName(serverName);
			serverName = notesServerName.getCommon();
			notesServerName.recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverName;
	}
	
	public String getPath() {
		return m_Path;
	}

	public void setPath(String path) {
		m_Path = path;
	}

	public String getReplicaID() {
		return m_ReplicaID;
	}

	public void setReplicaID(String replicaID) {
		m_ReplicaID = replicaID;
	}

	public void setName(String name) {
		m_Name = name;
	}

	public String getName() {
		return m_Name;
	}

	public ScanStatus getScanStatus() {
		return m_ScanStatus;
	}

	public void setScanStatus(ScanStatus scanStatus) {
		m_ScanStatus = scanStatus;
	}

	public void setScope(Scope scope) {
		m_Scope = scope;
	}

	public Scope getScope() {
		return m_Scope;
	}

	public String getDevResponsible() {
		return m_DevResponsible;
	}

	public void setDevResponsible(String devResponsible) {
		m_DevResponsible = devResponsible;
	}

	public String getBusinessOwner() {
		return m_BusinessOwner;
	}

	public void setBusinessOwner(String businessOwner) {
		m_BusinessOwner = businessOwner;
	}

	public MimeMultipart getBody() {
		return m_Body;
	}

	public void setBody(MimeMultipart body) {
		m_Body = body;
	}

	public void setDocNoteId(String docNoteId) {
		m_DocNoteId = docNoteId;
	}

	public String getDocNoteId() {
		return m_DocNoteId;
	}
}
