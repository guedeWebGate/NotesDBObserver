package biz.webgate.xpages.dbobserver;

public class DBObserverException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String m_Log;
	private final String m_LogComment;

	public DBObserverException(String arg0, Throwable arg1, String log, String logComment) {
		super(arg0, arg1);
		m_Log = log;
		m_LogComment = logComment;
	}

	public String getLog() {
		return m_Log;
	}

	public String getLogComment() {
		return m_LogComment;
	}

}
