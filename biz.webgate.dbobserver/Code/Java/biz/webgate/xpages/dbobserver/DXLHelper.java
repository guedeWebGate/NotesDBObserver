package biz.webgate.xpages.dbobserver;

import java.util.LinkedList;
import java.util.List;

import lotus.domino.DxlExporter;
import lotus.domino.NoteCollection;

import org.openntf.jaxb.model.domino.DominoUtils;
import org.openntf.jaxb.model.dxl.Database;
import org.openntf.jaxb.model.dxl.Note;
import org.openntf.jaxb.model.dxl.SearchTypes;

import biz.webgate.xpages.dbobserver.bo.DesignElementType;
import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.bo.SearchPattern;
import biz.webgate.xpages.dbobserver.store.HitStorageService;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public enum DXLHelper {
	INSTANCE;

	public List<Hit> buildTree(final String server, final String filePath, List<SearchPattern> patterns) throws Throwable {
		List<Hit> hits = new LinkedList<Hit>();
		System.out.println("Pattern: "+patterns);
		Database dbDXL = getDatabaseDXL(server, filePath);
		scanForCode(hits, dbDXL);
		System.out.println(hits.size() + " result found!");
		saveHits(hits);
		checkPattern(hits, patterns);
		return hits;
	}

	private void checkPattern(List<Hit> hits, List<SearchPattern> patterns) {
		for (Hit hit : hits) {
			hit.resetHitReason();
			for (SearchPattern search : patterns) {
				if (hit.getPatternType() == search.getType()) {
					System.out.println("Check:"+ hit.getHitContent() +" against " + search.getPattern());
					if (hit.getHitContent().toLowerCase().contains(search.getPattern().toLowerCase())) {
						hit.addHitReason(search.getPattern(), search.getImportance());
						HitStorageService.getInstance().save(hit);
						System.out.println(search.getPattern() +" found!");
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

	private void scanForCode(List<Hit> hits, Database dbDXL) {
		for (Object obj : dbDXL.getNoteOrDocumentOrProfiledocument()) {
			DesignElementType designElementTyp = DesignElementType.getDesignType(obj);
			if (designElementTyp == null) {
			} else {
				List<Hit> currentHits = designElementTyp.getCodeScaner().runScan(obj, dbDXL.getReplicaid());
				hits.addAll(currentHits);
			}

		}
	}

	private Database getDatabaseDXL(String server, String filePath) throws Throwable {
		DxlExporter dxle = ExtLibUtil.getCurrentSession().createDxlExporter();

		lotus.domino.Database db = ExtLibUtil.getCurrentSession().getDatabase(server, filePath);
		NoteCollection nc = db.createNoteCollection(false);
		nc.selectAllDesignElements(true);
		nc.buildCollection();
		Database dxldb = DominoUtils.INSTANCE.getDXLDatabase(dxle, nc);
		db.recycle();
		nc.recycle();
		return dxldb;
	}

}
