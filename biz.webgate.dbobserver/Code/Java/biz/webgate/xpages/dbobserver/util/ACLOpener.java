package biz.webgate.xpages.dbobserver.util;

import lotus.domino.Agent;

import com.ibm.xsp.extlib.util.ExtLibUtil;

import biz.webgate.xpages.dbobserver.bo.Database;

public enum ACLOpener {
	INSTANCE;

	public boolean openACL(Database dbScan) {
		try {
			Agent agent = ExtLibUtil.getCurrentDatabase().getAgent("aaPashACLUP");
			int run = agent.runOnServer(dbScan.getDocNoteId());
			agent.recycle();
			return run == 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
