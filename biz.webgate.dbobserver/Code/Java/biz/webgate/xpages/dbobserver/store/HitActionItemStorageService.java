package biz.webgate.xpages.dbobserver.store;

import org.openntf.xpt.core.dss.AbstractStorageService;

import biz.webgate.xpages.dbobserver.bo.HitActionItem;

public class HitActionItemStorageService extends AbstractStorageService<HitActionItem> {

	private final static HitActionItemStorageService m_Service = new HitActionItemStorageService();

	private HitActionItemStorageService() {
	}

	public static HitActionItemStorageService getInstance() {
		return m_Service;
	}

	@Override
	protected HitActionItem createObject() {
		return new HitActionItem();
	}

	public HitActionItem findItem(String id, String replicaId) {
		HitActionItem rc = getById(id);
		if (rc == null) {
			rc = createObject();
			rc.setID(id);
			rc.setReplicaId(replicaId);
			save(rc);
		}
		return rc;
	}
}
