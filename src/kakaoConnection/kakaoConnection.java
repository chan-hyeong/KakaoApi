package kakaoConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import vo.kakaoBlog;
import vo.kakaoBook;
import vo.kakaoCafe;
import vo.kakaoImage;
import vo.kakaoTip;
import vo.kakaoVclip;
import vo.kakaoWeb;

public class kakaoConnection {
	private URL url;
	private HttpURLConnection conn;
	// �߱޹��� ApiKey ��
	private String apiKey;
	// header�� Authorization ������ ���� ����
	private String authorization = "KakaoAK ";
	// Api�� ������ �� ���������� ����� �ּҸ� ���� ����
	private String host = "https://dapi.kakao.com/v2/search/";
	// ���� ����� �����ϴ� ����
	private String method = "GET";
	private String contentType = "application/json;charset=UTF-8";
	// blog, book, cafe, image, tip, vclip, web �� � �˻��� ���� ���� ����
	private String request;
	// ������ Api�� ������ �ּҸ� ���� ����
	private String address;
	// �˻��� ������ �����ϴ� ���� - �ʼ�
	private String query;
	// ���� ����� �����ϴ� ���� | accuracy, recency | �⺻�� : accuracy
	private String sort;
	// �� ��° �������� ������� ������ ���� ���ϴ� ����
	// 1 ~ 50 ���� | �⺻�� : 1
	private String page;
	// �˻��� ��������� � ������ ���� ���ϴ� ����
	// 1 ~ 50 ���� | �⺻�� : 10
	private String size;
	
	public kakaoConnection() {
	}
	
	/*
	 * sort, page, size�� kakao���� �����ϴ� �⺻������ ����ϴ� ����� ������
	 */
	public kakaoConnection(String apiKey, String request, String query) throws UnsupportedEncodingException {
		this.apiKey = apiKey;
		this.authorization += this.apiKey;
		this.request = request;
		this.address = this.host + this.request;
		this.query = URLEncoder.encode(query, "UTF-8");
		
		if(!"".equals(this.query))
			address += "?query=" + this.query;
	}
	/*
	 * sort, page, size�� ���� �����ϴ� ������
	 */
	public kakaoConnection(String apiKey, String request, String query, String sort, String page, String size) throws UnsupportedEncodingException {
		this.apiKey = apiKey;
		this.authorization += this.apiKey;
		this.request = request;
		this.address = this.host + this.request;
		this.query = URLEncoder.encode(query, "UTF-8");
		this.sort = sort;
		this.page = page;
		this.size = size;
		
		if(!"".equals(this.query))
			address += "?query=" + this.query;
		if(!"".equals(this.query))
			address += "&sort=" + this.sort;
		if(!"".equals(this.query))
			address += "&page=" + this.page;
		if(!"".equals(this.query))
			address += "&size=" + this.size;
	}
	
	/*
	 * ������ �õ��ϴ� �޼ҵ�
	 * ���ӿ� �������ο� ������� kakao�κ��� ���� ���� �����ϸ�
	 * 200�̸� ���ӿ� ������ ����.
	 */
	public int getResponseCode() {
		int getResponseCode = 0;
		try {
			url = new URL(address);
			conn = (HttpURLConnection)url.openConnection();
			
			conn.setRequestMethod(method);
			conn.setRequestProperty("Authorization", authorization);
			conn.setRequestProperty("Content-Type", contentType);
			conn.connect();
			
			getResponseCode = conn.getResponseCode();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return getResponseCode;
	}
	
	/*
	 * getResponseCode()�� ���� 200�� ��ȯ �޾Ҵٸ�
	 * ���������̹Ƿ� �� �޼ҵ带 ���� ��� ������ �޾ƿ� �� ����.
	 */
	public JSONObject getResponseData() {
		JSONObject json = new JSONObject();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String inputLine;
			
			while((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			
			json = new JSONObject(sb.toString());
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/*
	 * ������ �����ϴ� �޼ҵ�
	 */
	public void disConnection() {
		conn.disconnect();
	}
	
	/*
	 * ��(web) �˻� ����� �Ľ��ϴ� �޼ҵ�
	 */
	public kakaoWeb webSearch(JSONObject json) {
		kakaoWeb kakaoweb = new kakaoWeb();
		
		if(!json.isNull("documents")) {
			JSONArray ja = json.getJSONArray("documents");
			
			for(int i = 0; i < ja.length(); i++) {
				String title = ja.getJSONObject(i).getString("title");
				String contents = ja.getJSONObject(i).getString("contents");
				String url = ja.getJSONObject(i).getString("url");
				String datetime = ja.getJSONObject(i).getString("datetime");
				
				kakaoweb.setTitle(title);
				kakaoweb.setContents(contents);
				kakaoweb.setUrl(url);
				kakaoweb.setDatetime(datetime);
			}
		}
		else
			System.out.println("�˻��� ����� �����ϴ�.");
		
		return kakaoweb;
	}
	
	/*
	 * ������(vclip) �˻� ����� �Ľ��ϴ� �޼ҵ�
	 */
	public kakaoVclip vclipSearch(JSONObject json) {
		kakaoVclip kakaovclip = new kakaoVclip();
		
		if(!json.isNull("documents")) {
			JSONArray ja = json.getJSONArray("documents");
			
			for(int i = 0; i < ja.length(); i++) {
				String title = ja.getJSONObject(i).getString("title");
				String url = ja.getJSONObject(i).getString("url");
				String datetime = ja.getJSONObject(i).getString("datetime");
				int play_time = 0;
				if(!ja.getJSONObject(i).getString("price").equals(""))
					play_time = ja.getJSONObject(i).getInt("play_time");
				String thumbnail = ja.getJSONObject(i).getString("thumbnail");
				String author = ja.getJSONObject(i).getString("author");
				
				kakaovclip.setTitle(title);
				kakaovclip.setUrl(url);
				kakaovclip.setDatetime(datetime);
				kakaovclip.setPlay_time(play_time);
				kakaovclip.setThumbnail(thumbnail);
				kakaovclip.setAuthor(author);
			}
		}
		else
			System.out.println("�˻��� ����� �����ϴ�.");
		
		return kakaovclip;
	}
	
	/*
	 * �̹���(image) �˻� ����� �Ľ��ϴ� �޼ҵ�
	 */
	public kakaoImage imageSearch(JSONObject json) {
		kakaoImage kakaoimage = new kakaoImage();
		
		if(!json.isNull("documents")) {
			JSONArray ja = json.getJSONArray("documents");
			
			for(int i = 0; i < ja.length(); i++) {
				String collection = ja.getJSONObject(i).getString("collection");
				String thumbnail_url = ja.getJSONObject(i).getString("thumbnail_url");
				String image_url = ja.getJSONObject(i).getString("image_url");
				int width = 0;
				if(!ja.getJSONObject(i).getString("width").equals(""))
					width = ja.getJSONObject(i).getInt("width");
				int height = 0;
				if(!ja.getJSONObject(i).getString("height").equals(""))
					height = ja.getJSONObject(i).getInt("heigth");
				String display_sitename = ja.getJSONObject(i).getString("display_sitename");
				String doc_url = ja.getJSONObject(i).getString("doc_url");
				String datetime = ja.getJSONObject(i).getString("datetime");
				
				kakaoimage.setCollection(collection);
				kakaoimage.setThumbnail_url(thumbnail_url);
				kakaoimage.setImage_url(image_url);
				kakaoimage.setWidth(width);
				kakaoimage.setHeight(height);
				kakaoimage.setDisplay_sitename(display_sitename);
				kakaoimage.setDoc_url(doc_url);
				kakaoimage.setDatetime(datetime);
			}
		}
		else
			System.out.println("�˻��� ����� �����ϴ�.");
		
		return kakaoimage;
	}
	
	/*
	 * ��α�(blog) �˻� ����� �Ľ��ϴ� �޼ҵ�
	 */
	public kakaoBlog blogSearch(JSONObject json) {
		kakaoBlog kakaoblog = new kakaoBlog();
		
		if(!json.isNull("documents")) {
			JSONArray ja = json.getJSONArray("documents");
			
			for(int i = 0; i < ja.length(); i++) {
				String title = ja.getJSONObject(i).getString("title");
				String contents = ja.getJSONObject(i).getString("contents");
				String url = ja.getJSONObject(i).getString("url");
				String blogname = ja.getJSONObject(i).getString("blogname");
				String thumbnail = ja.getJSONObject(i).getString("thumbnail");
				String datetime = ja.getJSONObject(i).getString("datetime");
				
				kakaoblog.setTitle(title);
				kakaoblog.setContents(contents);
				kakaoblog.setUrl(url);
				kakaoblog.setBlogname(blogname);
				kakaoblog.setThumbnail(thumbnail);
				kakaoblog.setDatetime(datetime);
			}
		}
		else
			System.out.println("�˻��� ����� �����ϴ�.");
		
		return kakaoblog;
	}
	
	/*
	 * ��(tip) �˻� ����� ��ȯ�ϴ� �޼ҵ�
	 */
	public kakaoTip tipSearch(JSONObject json) {
		kakaoTip kakaotip = new kakaoTip();
		
		if(!json.isNull("documents")) {
			JSONArray ja = json.getJSONArray("documents");
			
			for(int i = 0; i < ja.length(); i++) {
				String title = ja.getJSONObject(i).getString("title");
				String contents = ja.getJSONObject(i).getString("contents");
				String q_url = ja.getJSONObject(i).getString("q_url");
				String a_url = ja.getJSONObject(i).getString("a_url");
				JSONArray thumbs = ja.getJSONObject(i).getJSONArray("thumbnails");
				String[] thumbnails = new String[thumbs.length()];
				for(int j = 0; j < thumbs.length(); j++) {
					thumbnails[j] = thumbs.get(j).toString();
				}
				String type = ja.getJSONObject(i).getString("type");
				String datetime = ja.getJSONObject(i).getString("datetime");
				
				kakaotip.setTitle(title);
				kakaotip.setContents(contents);
				kakaotip.setQ_url(q_url);
				kakaotip.setA_url(a_url);
				kakaotip.setThumbnails(thumbnails);
				kakaotip.setType(type);
				kakaotip.setDatetime(datetime);
			}
		}
		else
			System.out.println("�˻��� ����� �����ϴ�.");
		
		return kakaotip;
	}
	
	/*
	 * å(book) �˻� ����� �Ľ��ϴ� �޼ҵ�
	 */
	public kakaoBook bookSearch(JSONObject json) {
		kakaoBook kakaobook = new kakaoBook();
		
		if(!json.isNull("documents")) {
			JSONArray ja = json.getJSONArray("documents");
			
			for(int i = 0; i < ja.length(); i++) {
				String title = ja.getJSONObject(i).getString("title");
				String contents = ja.getJSONObject(i).getString("contents");
				String url = ja.getJSONObject(i).getString("url");
				String isbn = ja.getJSONObject(i).getString("isbn");
				String datetime = ja.getJSONObject(i).getString("datetime");
				JSONArray author = ja.getJSONObject(i).getJSONArray("authors");
				String[] authors = new String[author.length()];
				for(int j = 0; j < author.length(); j++) {
					authors[j] = author.get(j).toString();
				}
				String publisher = ja.getJSONObject(i).getString("publisher");
				JSONArray translator = ja.getJSONObject(i).getJSONArray("translators");
				String[] translators = new String[translator.length()];
				for(int j = 0; j < translator.length(); j++) {
					translators[j] = translator.get(j).toString();
				}
				int price = 0;
				if(!ja.getJSONObject(i).getString("price").equals(""))
					price = ja.getJSONObject(i).getInt("price");
				int sale_price = 0;
				if(!ja.getJSONObject(i).getString("sale_price").equals(""))
					sale_price = ja.getJSONObject(i).getInt("sale_price");
				String sale_yn = ja.getJSONObject(i).getString("sale_yn");
				String category = ja.getJSONObject(i).getString("category");
				String thumbnail = ja.getJSONObject(i).getString("thumbnail");
				String barcode = ja.getJSONObject(i).getString("barcode");
				String ebook_barcode = ja.getJSONObject(i).getString("ebook_barcode");
				String status = ja.getJSONObject(i).getString("status");
				
				kakaobook.setTitle(title);
				kakaobook.setContents(contents);
				kakaobook.setUrl(url);
				kakaobook.setIsbn(isbn);
				kakaobook.setDatetime(datetime);
				kakaobook.setAuthors(authors);
				kakaobook.setPublisher(publisher);
				kakaobook.setTranslators(translators);
				kakaobook.setPrice(price);
				kakaobook.setSale_price(sale_price);
				kakaobook.setSale_yn(sale_yn);
				kakaobook.setCategory(category);
				kakaobook.setThumbnail(thumbnail);
				kakaobook.setBarcode(barcode);
				kakaobook.setEbook_barcode(ebook_barcode);
				kakaobook.setStatus(status);
			}
		}
		else
			System.out.println("�˻��� ����� �����ϴ�.");
		
		return kakaobook;
	}
	
	/*
	 * ī��(cafe) �˻� ����� �Ľ��ϴ� �޼ҵ�
	 */
	public kakaoCafe cafeSearch(JSONObject json) {
		kakaoCafe kakaocafe = new kakaoCafe();
		
		if(!json.isNull("documents")) {
			JSONArray ja = json.getJSONArray("documents");
			
			for(int i = 0; i < ja.length(); i++) {
				String title = ja.getJSONObject(i).getString("title");
				String contents = ja.getJSONObject(i).getString("contents");
				String url = ja.getJSONObject(i).getString("url");
				String cafename = ja.getJSONObject(i).getString("cafename");
				String thumbnail = ja.getJSONObject(i).getString("thumbnail");
				String datetime = ja.getJSONObject(i).getString("datetime");
				
				kakaocafe.setTitle(title);
				kakaocafe.setContents(contents);
				kakaocafe.setUrl(url);
				kakaocafe.setCafename(cafename);
				kakaocafe.setThumbnail(thumbnail);
				kakaocafe.setDatetime(datetime);
			}
		}
		else
			System.out.println("�˻��� ����� �����ϴ�.");
		
		return kakaocafe;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getHost() {
		return host;
	}

	public String getMethod() {
		return method;
	}

	public String getContentType() {
		return contentType;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getAddress() {
		return address;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) throws UnsupportedEncodingException {
		this.query = URLEncoder.encode(query, "UTF-8");
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
		
		if(!"".equals(getQuery()))
			this.address += "&sort=" + this.sort;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
		
		if(!"".equals(getQuery()))
			this.address += "&page=" + this.page;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
		
		if(!"".equals(getQuery()))
			this.address += "&size=" + this.size;
	}
	
}