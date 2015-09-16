package edu.pitt.sis.documentmodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * Class deals with the list of stopwords extracted from an external source
 * @author SHRUTI
 *
 */
public class StopWords {
	
	/**
	 * URL for stopwords
	 */
	private static final String URLSTRING = "http://www.textfixer.com/resources/common-english-words.txt";
	/**
	 * Property to store the set of stopwords
	 */
	private Set<String> stopwordSet;

	/**
	 * Retrieves the list of stopwords from the external source and stores it
	 */
	public void getStopwords() {
		URLConnection urlConnection = null;
		BufferedReader br = null;
		try{
			URL url = new URL(URLSTRING);
			urlConnection = url.openConnection();
			br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			String inputLine = new String();
			StringBuilder stringBuilder = new StringBuilder();
			while ((inputLine = br.readLine())!=null){
				stringBuilder.append(inputLine);
			}
			String[] stopwordsArray = StringUtils.split(stringBuilder.toString(),",");
			stopwordSet = new HashSet<String>();
			for(int i=0; i<stopwordsArray.length; i++)
				stopwordSet.add(stopwordsArray[i]);
		}
		catch(MalformedInputException e){
			System.out.println(StopWords.class.toString()+": "+e.getMessage());
		}
		catch(IOException e){
			System.out.println(StopWords.class.toString()+": "+e.getMessage());
		}
		finally{
			if(br!=null){
				try{
					br.close();
				}
				catch(IOException e){
					System.out.println(StopWords.class.toString()+": "+e.getMessage());
				}
			}
		}
	}

	/**
	 * Accessor for stopwordSet
	 * @return
	 */
	public Set<String> getStopwordSet() {
		return stopwordSet;
	}

	/**
	 * Modifier for stopwordSet
	 * @param stopwordSet
	 */
	public void setStopwordSet(Set<String> stopwordSet) {
		this.stopwordSet = stopwordSet;
	}

}
