package biz.webgate.xpages.dbobserver;

import java.util.LinkedList;
import java.util.List;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.NoteCollection;

import com.ibm.commons.util.StringUtil;
import com.ibm.xsp.extlib.util.ExtLibUtil;

import biz.webgate.xpages.dbobserver.scanner.raw.DesignElement;

public enum RewDesignElementHelper {
	INSTANCE;

	public List<DesignElement> getAllRawElements(final String server, final String filePath) {
		List<DesignElement> allElements = new LinkedList<DesignElement>();
		try {
			Database db = ExtLibUtil.getCurrentSession().getDatabase(server, filePath);
			NoteCollection nc = db.createNoteCollection(false);
			nc.selectAllDesignElements(true);
			nc.buildCollection();
			String noteIDNext = nc.getFirstNoteID();
			while (!StringUtil.isEmpty(noteIDNext)) {
				String noteId = noteIDNext;
				Document doc = db.getDocumentByID(noteId);
				DesignElement element = DesignElement.buildDesignElement(doc);
				allElements.add(element);
				doc.recycle();
			}
			nc.recycle();
			db.recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allElements;
	}
	
	public DesignElement findElementByNoteID(final String id,final String server, final String filePath) {
		DesignElement de = null;
		try {
			Database db = ExtLibUtil.getCurrentSession().getDatabase(server, filePath);
			Document doc = db.getDocumentByID(id);
			de = DesignElement.buildDesignElement(doc);
			doc.recycle();
			db.recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return de;
	}

}
