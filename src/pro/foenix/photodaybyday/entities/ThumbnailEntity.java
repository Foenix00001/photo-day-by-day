package pro.foenix.photodaybyday.entities;

public class ThumbnailEntity {

	private String url;
	private int order;
	private boolean isChecked;
	private boolean isEmpty;

	public ThumbnailEntity(int order, String url) {
		super();
		this.url = url;
		this.order = order;
		this.isChecked = false;
		if (url == null) {
			this.isEmpty = true;
		} else {
			this.isEmpty = false;
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		if (url == null) {
			this.isEmpty = true;
		} else {
			this.isEmpty = false;
		}
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
}
