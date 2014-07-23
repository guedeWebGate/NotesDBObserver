package biz.webgate.xpages.dbobserver.scanner.bulk;

import java.util.List;

import org.openntf.jaxb.model.dxl.Action;
import org.openntf.jaxb.model.dxl.Sharedactionref;

import biz.webgate.xpages.dbobserver.bo.Hit;
import biz.webgate.xpages.dbobserver.scanner.DesignInfo;

public enum ActionListScanner {
	INSTANCE;
	public void scanActionList(List<Hit> hits, List<Object> actionOrSharedActions, DesignInfo designInfo) {
		for (Object obj : actionOrSharedActions) {
			if (obj instanceof Action) {
				Action action = (Action) obj;
				CodeListScanner.INSTANCE.scanCodes(hits, action.getCode(), "Actionbar/Action=" + action.getTitle(), designInfo);
			}
			if (obj instanceof Sharedactionref) {
				Sharedactionref shared = (Sharedactionref) obj;
				if (shared.getAction() != null) {
					CodeListScanner.INSTANCE.scanCodes(hits, shared.getAction().getCode(), "Actionbar/SharedAction="
							+ shared.getAction().getTitle(), designInfo);
				}
			}

		}

	}

}
