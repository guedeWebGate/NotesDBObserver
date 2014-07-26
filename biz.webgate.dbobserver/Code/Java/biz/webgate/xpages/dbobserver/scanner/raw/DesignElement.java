package biz.webgate.xpages.dbobserver.scanner.raw;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import lotus.domino.Document;
import lotus.domino.Item;

public class DesignElement implements Serializable {

	@SuppressWarnings("unchecked")
	public static DesignElement buildDesignElement(Document note) {
		try {
			Map<String,String> treeMap = new TreeMap<String, String>();
			Vector<Item> items = note.getItems();
			for (Item item : items) {
				if (item.getType() == Item.TEXT) {
					treeMap.put(item.getName(), item.getValueString());
				} else {
					treeMap.put(item.getName(), "BINARY DATA");					
				}
				
			}
			return new DesignElement(note.getNoteID(), note.getItemValueString("$Title"),note.getItemValueString("$Flags"), treeMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String m_NoteID;
	private final String m_Title;
	private final String m_Flags;
	private final Map<String, String> m_Items;
	private DesignElement(String noteId, String title, String flags, Map<String, String> items) {
		super();
		m_NoteID = noteId;
		m_Title = title;
		m_Flags = flags;
		m_Items = items;
	}
	public String getTitle() {
		return m_Title;
	}
	public String getFlags() {
		return m_Flags;
	}
	public Map<String, String> getItems() {
		return m_Items;
	}
	public String getNoteID() {
		return m_NoteID;
	}
	
	
}

