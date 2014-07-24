package biz.webgate.xpages.dbobserver.bo;

public enum ScanStatus {
	STUB, SCANNED, CHECKED, OUTOFSCOPE, ERROR;

	public boolean showError() {
		return this == ERROR;
	}

	public boolean showHitList() {
		return this == SCANNED || this == CHECKED;
	}
}
