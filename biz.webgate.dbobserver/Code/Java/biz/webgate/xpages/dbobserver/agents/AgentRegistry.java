package biz.webgate.xpages.dbobserver.agents;

import org.openntf.xpt.agents.XPageAgentRegistry;

public class AgentRegistry extends XPageAgentRegistry {

	@Override
	public void registerAgents() {
		initAgent(ScanServer4DbAgent.class);
	}

}
