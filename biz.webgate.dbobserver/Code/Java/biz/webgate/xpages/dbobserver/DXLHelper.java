package biz.webgate.xpages.dbobserver;

import java.util.LinkedList;
import java.util.List;

import lotus.domino.DxlExporter;
import lotus.domino.NoteCollection;

import org.openntf.jaxb.model.domino.DominoUtils;
import org.openntf.jaxb.model.dxl.Database;

import biz.webgate.xpages.dbobserver.bo.DesignElementType;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.bo.SearchPattern;
import biz.webgate.xpages.dbobserver.store.HitActionItemStorageService;
import biz.webgate.xpages.dbobserver.store.HitStorageService;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public enum DXLHelper {
	INSTANCE;

	public List<Hit> buildTree(final String server, final String filePath, List<SearchPattern> patterns, String replicaId)
			throws DBObserverException {
		List<Hit> hits = new LinkedList<Hit>();
		Database dbDXL = getDatabaseDXL(server, filePath);
		scanForCode(hits, dbDXL, replicaId);
		saveHits(hits);
		checkPattern(hits, patterns);
		return hits;
	}

	private void checkPattern(List<Hit> hits, List<SearchPattern> patterns) {
		for (Hit hit : hits) {
			hit.resetHitReason();
			for (SearchPattern search : patterns) {
				if (hit.getPatternType() == search.getType()) {
					if (hit.getHitContent().toLowerCase().contains(search.getPattern().toLowerCase())) {
						hit.addHitReason(search.getPattern(), search.getImportance());
						HitStorageService.getInstance().save(hit);
						HitActionItemStorageService.getInstance().findItem(hit.getID(), hit.getReplicaID());
					}
				}
			}
		}
	}

	private void saveHits(List<Hit> hits) {
		for (Hit hit : hits) {
			HitStorageService.getInstance().save(hit);
		}
	}

	private void scanForCode(List<Hit> hits, Database dbDXL, String replicaId) {
		for (Object obj : dbDXL.getNoteOrDocumentOrProfiledocument()) {
			DesignElementType designElementTyp = DesignElementType.getDesignType(obj);
			if (designElementTyp == null) {
			} else {
				List<Hit> currentHits = designElementTyp.getCodeScaner().runScan(obj, replicaId);
				hits.addAll(currentHits);
			}
		}
	}

	private Database getDatabaseDXL(final String server, final String filePath) throws DBObserverException {
		DxlExporter dxle = null;
		try {
			dxle = ExtLibUtil.getCurrentSession().createDxlExporter();
			dxle.setOutputDOCTYPE(false);
			dxle.setExitOnFirstFatalError(false);
			lotus.domino.Database db = ExtLibUtil.getCurrentSession().getDatabase(server, filePath);
			NoteCollection nc = db.createNoteCollection(false);
			nc.selectAllDesignElements(true);
			nc.buildCollection();
			Database dxldb = null;
			dxldb = DominoUtils.INSTANCE.getDXLDatabase(dxle, nc);
			db.recycle();
			nc.recycle();
			return dxldb;
		} catch (Exception e) {
			String log = "";
			;
			String logComment = "";
			try {
				log = dxle.getLog();
				logComment = dxle.getLogComment();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			throw new DBObserverException("Error during getDatabaseDXL", e, log, logComment);
		}
	}
}
