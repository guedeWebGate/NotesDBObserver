package biz.webgate.xpages.dbobserver.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.openntf.xpt.core.dss.SingleObjectStore;

import biz.webgate.xpages.dbobserver.DXLHelper;
import biz.webgate.xpages.dbobserver.bo.Database;
import biz.webgate.xpages.dbobserver.bo.Scope;
import biz.webgate.xpages.dbobserver.bo.SearchPattern;
import biz.webgate.xpages.dbobserver.store.DatabaseStorageService;
import biz.webgate.xpages.dbobserver.store.SearchPatternStorageService;
import biz.webgate.xpages.dbobserver.util.ACLOpener;

public class AdminSessionFacade {

	private static final String BEAN_NAME = "adminBean";

	public static AdminSessionFacade get(FacesContext context) {
		AdminSessionFacade bean = (AdminSessionFacade) context.getApplication().getVariableResolver().resolveVariable(context, BEAN_NAME);
		return bean;
	}

	public static AdminSessionFacade get() {
		return get(FacesContext.getCurrentInstance());
	}

	public List<SearchPattern> getAllSearchPatterns() {
		return SearchPatternStorageService.getInstance().getAll("lupSearchPatternById");
	}

	public SingleObjectStore<SearchPattern> getSearchPatternSOS() {
		return new SingleObjectStore<SearchPattern>(SearchPatternStorageService.getInstance());
	}

	public List<Database> getAllDatabases() {
		return DatabaseStorageService.getInstance().getAll("lupDbById");
	}

	public List<Database> getDatabasesByScope(Scope scope) {
		return DatabaseStorageService.getInstance().getObjectsByForeignId(scope.name(), "lupDbByScope");
	}

	public SingleObjectStore<Database> getDatabaseSOS() {
		return new SingleObjectStore<Database>(DatabaseStorageService.getInstance());
	}

	public void setDbScope(String replicaID, String scopeName) {
		Database db = DatabaseStorageService.getInstance().getById(replicaID);
		Scope scope = getDBScope(scopeName);
		db.setScope(scope);
		DatabaseStorageService.getInstance().save(db);
	}

	public Scope getDBScope(String name) {
		try {
			return Scope.valueOf(name);
		} catch (Exception e) {
		}
		return Scope.NORMAL;
	}

	public static Map<String, String> getQueryMap(String query) {
		String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			map.put(name, value);
		}
		return map;
	}

	public void scanDB(Database scanDB) {
		try {
			if (ACLOpener.INSTANCE.openACL(scanDB)) {
				DXLHelper.INSTANCE.buildTree(scanDB.getServer(), scanDB.getPath(), getAllSearchPatterns());
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
