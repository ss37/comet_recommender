package edu.pitt.sis.documentmodel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RssReader {

	private Map<String,String> feedURLTitleMap;
	private List<String> feedURLList;
	
	public void readRssFeeds(String urlString){
		XmlReader reader = null;
		try {
			URL url = new URL(urlString);
		    reader = new XmlReader(url);
		    SyndFeedInput sfi = new SyndFeedInput();
		    SyndFeed feed = sfi.build(reader);
		    
		    feedURLTitleMap = new HashMap<String,String>();
		    feedURLList = new ArrayList<String>();
		    for (Iterator<SyndEntry> i = feed.getEntries().iterator(); i.hasNext();) {
		    	SyndEntry entry = (SyndEntry)i.next();
		    	feedURLTitleMap.put(entry.getLink(),entry.getTitle());
		    	feedURLList.add(entry.getLink());
		    }
		}
		catch (MalformedURLException e1) {
			e1.printStackTrace();
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    finally {
	        if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	     }
	}

	public List<String> getFeedURLList() {
		return feedURLList;
	}

	public void setFeedURLList(List<String> feedURLList) {
		this.feedURLList = feedURLList;
	}

	public Map<String, String> getFeedURLTitleMap() {
		return feedURLTitleMap;
	}

	public void setFeedURLTitleMap(Map<String, String> feedURLTitleMap) {
		this.feedURLTitleMap = feedURLTitleMap;
	}
}
