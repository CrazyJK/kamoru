package jk.kamoru.tools;

public class DownloadResult {
	public Integer no;
	public Boolean result;
	public String message;
	
	public DownloadResult(Integer no, Boolean result) {
		super();
		this.no = no;
		this.result = result;
	}
	
	public DownloadResult(Integer no, Boolean result, String message) {
		super();
		this.no = no;
		this.result = result;
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("DownloadResult [no=%s, result=%s, message=%s]",
				no, result, message);
	}

	
}
