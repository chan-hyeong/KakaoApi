package vo;

public class kakaoCafe {
	private String title; // ī�� �� ����
	private String contents; // ī�� �� ���� �� �Ϻ�
	private String url; // ī�� �� URL
	private String cafename; // ī�� �̸�
	private String thumbnail; // ī�� �� ��ǥ �����
	private String datetime; // ���� ���ǳ�¥
	
	public kakaoCafe() {
	}

	public kakaoCafe(String title, String contents, String url, String cafename, String thumbnail, String datetime) {
		this.title = title;
		this.contents = contents;
		this.url = url;
		this.cafename = cafename;
		this.thumbnail = thumbnail;
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

	public String getCafename() {
		return cafename;
	}

	public void setCafename(String cafename) {
		this.cafename = cafename;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@Override
	public String toString() {
		return "kakaoCafe [title=" + title + ", contents=" + contents + ", url=" + url + ", cafename=" + cafename
				+ ", thumbnail=" + thumbnail + ", datetime=" + datetime + "]";
	}
	
}