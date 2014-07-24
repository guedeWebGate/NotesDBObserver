package biz.webgate.xpages.dbobserver.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import biz.webgate.xpages.dbobserver.bo.Database;
import biz.webgate.xpages.dbobserver.bo.DesignElementType;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.bo.HitActionItem;
import biz.webgate.xpages.dbobserver.bo.HitActionType;
import biz.webgate.xpages.dbobserver.store.DatabaseStorageService;
import biz.webgate.xpages.dbobserver.store.HitActionItemStorageService;
import biz.webgate.xpages.dbobserver.store.HitStorageService;

import com.ibm.commons.util.StringUtil;

public class HitViewFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Hit> m_AllHits;
	private List<String> m_Expanded = new ArrayList<String>();
	private List<HitActionItem> m_ActionItems;

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

	public HitActionItem getHitActionItem(String repID, String actionItemId) {
		checkHitActionItems(repID);
		for (HitActionItem hai : m_ActionItems) {
			if (hai.getID().equals(actionItemId)) {
				return hai;
			}
		}
		return null;
	}

	public void updateHitActionItem(HitActionItem hai, String newStatus, String comment) {
		HitActionType type = HitActionType.valueOf(newStatus);
		hai.setHitActionType(type);
		hai.setInstructions(comment);
		HitActionItemStorageService.getInstance().save(hai);
		Database database = DatabaseStorageService.getInstance().getById(hai.getReplicaId());
		AdminSessionFacade.get().updateActionItemCount(database);
		DatabaseStorageService.getInstance().save(database);

	}

	public boolean hasHitActionItem(String repID, String actionItemId) {
		return getHitActionItem(repID, actionItemId) != null;
	}

	public void reloadActionItems(String repID) {
		m_ActionItems = null;
		checkHitActionItems(repID);
	}

	private void checkHitActionItems(String repID) {
		if (m_ActionItems == null) {
			m_ActionItems = HitActionItemStorageService.getInstance().getObjectsByForeignId(repID, "lupHitActionItemByReplicaID");
		}
	}
}
