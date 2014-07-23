package biz.webgate.xpages.dbobserver.store;

import org.openntf.xpt.core.dss.AbstractStorageService;

import biz.webgate.xpages.dbobserver.bo.Hit;

public class HitStorageService extends AbstractStorageService<Hit> {

	private static final HitStorageService m_Service = new HitStorageService();

	private HitStorageService() {
	}

	public static HitStorageService getInstance() {
		return m_Service;
	}

	@Override
	protected Hit createObject() {
		return new Hit();
	}

}
