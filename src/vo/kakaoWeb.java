package vo;

public class kakaoWeb {
	private String title; // ���� ����
	private String contents; // ���� ���� �� �Ϻ�
	private String url; // ���� URL
	private String datetime; // ���� �� �ۼ��ð�
	
	public kakaoWeb() {
		
	}

	public kakaoWeb(String title, String contents, String url, String datetime) {
		this.title = title;
		this.contents = contents;
		this.url = url;
		this.datetime = datetime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@Override
	public String toString() {
		return "kakaoWeb [title=" + title + ", contents=" + contents + ", url=" + url + ", datetime=" + datetime + "]";
	}
	
}