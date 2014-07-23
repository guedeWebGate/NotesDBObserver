package biz.webgate.xpages.dbobserver.store;

import org.openntf.xpt.core.dss.AbstractStorageService;

import biz.webgate.xpages.dbobserver.bo.Database;

public class DatabaseStorageService extends AbstractStorageService<Database> {

	private static final DatabaseStorageService m_Service = new DatabaseStorageService();

	private DatabaseStorageService() {
	}

	public static DatabaseStorageService getInstance() {
		return m_Service;
	}

	@Override
	protected Database createObject() {
		return new Database();
	}

}
