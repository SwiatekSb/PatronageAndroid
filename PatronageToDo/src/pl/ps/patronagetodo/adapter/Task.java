package pl.ps.patronagetodo.adapter;


public class Task {

	private long mId;
	private String mDescription;
	private String mDate;
	private int mStatus;
	
	public Task() {
		new Task(0, null, null, 0);
	}
	
	public Task(long id, String description, String date, int status) {
		mId = id;
		mDescription = description;
		mDate = date;
		mStatus = status;
	}
	
	public long getId() {
		return mId;
	}
	public void setId(long id) {
		mId = id;
	}
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String description) {
		mDescription = description;
	}
	public String getDate() {
		return mDate;
	}
	public void setDate(String date) {
		mDate = date;
	}
	public int getStatus() {
		return mStatus;
	}
	public void setStatus(int status) {
		mStatus = status;
	}
}
