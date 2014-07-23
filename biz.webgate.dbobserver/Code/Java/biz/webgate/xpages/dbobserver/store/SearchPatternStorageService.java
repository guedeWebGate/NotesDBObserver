package biz.webgate.xpages.dbobserver.store;

import java.util.UUID;

import org.openntf.xpt.core.dss.AbstractStorageService;

import biz.webgate.xpages.dbobserver.bo.SearchPattern;

public class SearchPatternStorageService extends AbstractStorageService<SearchPattern> {

	private static final SearchPatternStorageService m_Servcie = new SearchPatternStorageService();

	private SearchPatternStorageService() {
	}

	public static SearchPatternStorageService getInstance() {
		return m_Servcie;
	}

	@Override
	protected SearchPattern createObject() {
		SearchPattern searchPattern = new SearchPattern();
		searchPattern.setID(UUID.randomUUID().toString());
		return searchPattern;
	}

}
