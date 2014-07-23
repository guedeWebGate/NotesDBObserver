package biz.webgate.xpages.dbobserver.scanner;

import java.util.LinkedList;
import java.util.List;

import org.openntf.jaxb.model.dxl.Agent;

import biz.webgate.xpages.dbobserver.ICodeScanner;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.bulk.CodeListScanner;

public enum AgentScanner implements ICodeScanner<Agent> {
	INSTANCE;
	public List<Hit> scanElement(Agent element, DesignInfo designInfo) {
		List<Hit> hits = new LinkedList<Hit>();
		CodeListScanner.INSTANCE.scanCodes(hits, element.getCode(), "", designInfo);
		return hits;
	}

	public List<Hit> runScan(Object element, String replicaId) {
		Agent agent = (Agent)element;
		agent.setReplicaid(replicaId);
		DesignInfo designInfo = DesignInfo.buildDesignInfoFromAgent(agent); 
		return scanElement(agent, designInfo);
	}

	

}
