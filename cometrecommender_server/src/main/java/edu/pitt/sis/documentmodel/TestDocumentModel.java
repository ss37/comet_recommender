package edu.pitt.sis.documentmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.pitt.sis.cometrecommender.models.InterestingTalksModel;

public class TestDocumentModel {

	/**
	 * Receives URLs of bookmarked talks and upcoming talks 
	 * and uses TF*IDF algorithm to calculate a list of recommended upcoming talks.
	 * @SHRUTI
	 * @param bookmarksUrl
	 * @param upcomingUrl
	 * @return
	 */
	public List<InterestingTalksModel> getInterestingTalks(String bookmarksUrl, String upcomingUrl) {
		//Extract the list of stop-words from an external source
		//Stopwords are frequently occurring words in English language 
		//and add less meaning when computing recommendations.
		StopWords s = new StopWords(); 
		s.getStopwords();
		
		//Read the "interesting" RSS feeds
		RssReader readBookmarked = new RssReader();
		if(!bookmarksUrl.contains("http://"))
			bookmarksUrl = "http://"+bookmarksUrl;
		readBookmarked.readRssFeeds(bookmarksUrl);
		Iterator<String> iBookmarked = readBookmarked.getFeedURLList().iterator();
		
		//Process each document from the "interesting" RSS feeds
		List<List<String>> listBookmarked = new ArrayList<List<String>>();
		while(iBookmarked.hasNext()){
			String urlString = (String) iBookmarked.next();
			HTMLProcessor h = new HTMLProcessor(urlString, s.getStopwordSet());
			listBookmarked.add(h.getTokenList());
		}
		
		KeywordModelGenerator k = new KeywordModelGenerator();
		//Create an aggregate document model from the "interesting" RSS feeds
		Map<String,Integer> keywordsTFMapBookmarked = k.createAggregateKeywordsList(listBookmarked);
		Map<String,Double> keywordsIDFMapBookmarked = k.calculateInverseDocumentFrequency(listBookmarked);
		Map<String,Double> keywordsTfIdfMapBookmarked = k.calculateTF_IDF(keywordsTFMapBookmarked, keywordsIDFMapBookmarked);
		
		//Read the "incoming" RSS feeds
		RssReader readIncoming = new RssReader();
		if(!upcomingUrl.contains("http://"))
			upcomingUrl = "http://"+upcomingUrl;
		readIncoming.readRssFeeds(upcomingUrl);
		Iterator<String> iInteresting = readIncoming.getFeedURLList().iterator();
		
		//Process each document from the "incoming" RSS feeds
		Map<String,List<String>> mapInteresting = new HashMap<String,List<String>>();
		List<List<String>> listInteresting = new ArrayList<List<String>>();
		while(iInteresting.hasNext()){
			String urlString = (String) iInteresting.next();
			HTMLProcessor h = new HTMLProcessor(urlString, s.getStopwordSet());
			listInteresting.add(h.getTokenList());
			mapInteresting.put(urlString, h.getTokenList());
		}
		
		//Calculate the IDF for the keywords in "incoming" RSS feeds
		Map<String,Double> keywordsIDFMapInteresting = k.calculateInverseDocumentFrequency(listInteresting);
		Map<String,Double> resultRssFeeds = new HashMap<String,Double>();
		
		//Calculate the tf*idf for each incoming feed and calculate the cosine similarity with the "interesting" feeds
		for(Entry<String, List<String>> interestingEntry: mapInteresting.entrySet()){
			String urlString = interestingEntry.getKey();
			List<String> keywordListBookmarked = interestingEntry.getValue();
			Map<String,Integer> keywordsTFMapInteresting = k.calculateTermFrequency(keywordListBookmarked);
			Map<String,Double> keywordsTfIdfMapInteresting = k.calculateTF_IDF(keywordsTFMapInteresting, keywordsIDFMapInteresting);
			Double cosineSimilarity = k.calculateCosineSimilarity(keywordsTfIdfMapInteresting, keywordsTfIdfMapBookmarked);
			resultRssFeeds.put(urlString, cosineSimilarity);
		}
		
		//Sort the "incoming" RSS feeds in decreasing order of cosine similarity calculated 
		Set<Entry<String, Double>> entrySet = resultRssFeeds.entrySet();
        List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(entrySet);
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>(){
        	public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 ){
        		return (o2.getValue()).compareTo( o1.getValue() );
        	}
        });
        
        List<InterestingTalksModel> interestingList = new ArrayList<InterestingTalksModel>();
        //Display the results
        System.out.println("Resultant \"Incoming\" RSS feeds ->");
		for(Entry<String,Double> result: list){
			//display in the console for demonstration purposes and understanding
			//System.out.println(result.getKey()+" = "+result.getValue());
			interestingList.add(new InterestingTalksModel(readIncoming.getFeedURLTitleMap().get(result.getKey()),result.getKey()));
		}
		
		//display in the console for demonstration purposes and understanding
		//System.out.println("*** END ***");
		return interestingList;
	}

}
