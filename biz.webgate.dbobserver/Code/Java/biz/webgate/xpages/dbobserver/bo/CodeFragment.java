package biz.webgate.xpages.dbobserver.bo;

import java.io.Serializable;

import com.ibm.commons.util.StringUtil;

public class CodeFragment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String m_FramentName;
	private final String m_Code;

	public CodeFragment(String framentName, String code) {
		super();
		m_FramentName = framentName;
		m_Code = code;
	}

	public String getFramentName() {
		return m_FramentName;
	}

	public String getCode() {
		return m_Code;
	}

	public String buildPath(String path) {
		if (StringUtil.isEmpty(m_FramentName)) {
			return path;
		}
		return path + "codeName=" + m_FramentName;
	}

}
