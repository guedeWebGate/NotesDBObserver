package biz.webgate.xpages.dbobserver.util;

import lotus.domino.Agent;
import biz.webgate.xpages.dbobserver.bo.Database;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public enum ACLOpener {
	INSTANCE;

	private static final String AGENT_ACL_UP = "aaPushACLUP";
	private static final String AGENT_ACL_DOWN = "aaPushACLDOWN";

	public boolean openACL(Database dbScan) {
		try {
			Agent agent = ExtLibUtil.getCurrentDatabase().getAgent(AGENT_ACL_UP);
			int run = agent.runOnServer(dbScan.getDocNoteId());
			agent.recycle();
			return run == 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean closeACL(Database dbScan) {
		try {
			Agent agent = ExtLibUtil.getCurrentDatabase().getAgent(AGENT_ACL_DOWN);
			int run = agent.runOnServer(dbScan.getDocNoteId());
			agent.recycle();
			return run == 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
