package vo;

public class kakaoBlog {
	private String title; // ��α� �� ����
	private String contents; // ��α� �� ���
	private String url; // ��α� �� URL
	private String blogname; // ��α��� �̸�
	private String thumbnail; // �˻��ý��ۿ��� ������ ��ǥ �����.
	private String datetime; // ��α� �� �ۼ��ð�
	
	public kakaoBlog() {
	}

	public kakaoBlog(String title, String contents, String url, String blogname, String thumbnail, String datetime) {
		this.title = title;
		this.contents = contents;
		this.url = url;
		this.blogname = blogname;
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

	public String getBlogname() {
		return blogname;
	}

	public void setBlogname(String blogname) {
		this.blogname = blogname;
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
		return "kakaoBlog [title=" + title + ", contents=" + contents + ", url=" + url + ", blogname=" + blogname
				+ ", thumbnail=" + thumbnail + ", datetime=" + datetime + "]";
	}
	
}