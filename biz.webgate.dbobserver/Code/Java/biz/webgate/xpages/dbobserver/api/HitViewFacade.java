package biz.webgate.xpages.dbobserver.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ibm.commons.util.StringUtil;

import biz.webgate.xpages.dbobserver.bo.DesignElementType;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.store.HitStorageService;

public class HitViewFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Hit> m_AllHits;
	private List<String> m_Expanded = new ArrayList<String>();

	public List<Hit> getAllHits(String repID) {
		checkHits(repID);
		return m_AllHits;
	}

	public List<Hit> getAllHitsWithReason(String repID) {
		checkHits(repID);
		List<Hit> hitsWithReason = new ArrayList<Hit>();
		for (Hit hit : m_AllHits) {
			if (hit.hasHitReason()) {
				hitsWithReason.add(hit);
			}
		}
		return hitsWithReason;
	}

	public Map<String, List<Hit>> getHitMap(String repID, String designElementName, boolean all) {
		Map<String, List<Hit>> map = new TreeMap<String, List<Hit>>();
		checkHits(repID);
		DesignElementType designElementType = DesignElementType.valueOf(designElementName);
		for (Hit hit : m_AllHits) {
			if (hit.getDesignElementType() == designElementType) {
				if (all || hit.hasHitReason()) {
					String name = hit.getDesignName();
					if (StringUtil.isEmpty(name)) {
						name = hit.getDesignAlias() == null ? hit.getNoteID() : hit.getDesignAlias();
					}
					List<Hit> hits = new ArrayList<Hit>();
					if (map.containsKey(name)) {
						hits = map.get(name);
					} else {
						map.put(name, hits);
					}
					hits.add(hit);
				}
			}
		}
		return map;
	}

	public void resetExpand() {
		m_Expanded = new ArrayList<String>();
	}

	public void toggleExpand(String name) {
		if (m_Expanded.contains(name)) {
			m_Expanded.remove(name);
		} else {
			m_Expanded.add(name);
		}
	}

	public boolean isExpanded(String name) {
		return m_Expanded.contains(name);
	}

	public void reloadHits(String repID) {
		m_AllHits = null;
		checkHits(repID);
	}

	private void checkHits(String repID) {
		if (m_AllHits == null) {
			m_AllHits = HitStorageService.getInstance().getObjectsByForeignId(repID, "lupHitByReplicaID");
		}
	}
}
