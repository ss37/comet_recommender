package edu.pitt.sis.cometrecommender.models;

/**
 * This model is used to accept a request of bookmarked talks URL and an upcoming talks URL.
 * @SHRUTI
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TalkModel {

	private String bookmarks;
	private String upcoming;
	
	public TalkModel() {}
	public TalkModel(final String bookmarks, final String upcoming) {
		this.setBookmarks(bookmarks);
		this.setUpcoming(upcoming);
	}
	
	public String getBookmarks() {
		return bookmarks;
	}
	public void setBookmarks(String bookmarks) {
		this.bookmarks = bookmarks;
	}
	public String getUpcoming() {
		return upcoming;
	}
	public void setUpcoming(String upcoming) {
		this.upcoming = upcoming;
	}
}
