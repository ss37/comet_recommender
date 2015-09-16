package edu.pitt.sis.documentmodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;

/**
 * Class deals with the processing of each HTML document
 * @author SHRUTI
 *
 */
public class HTMLProcessor {

	/**
	 * Property to store the HTML document source code
	 */
	private String htmlDocument;
	/**
	 * Property to store the text content of the HTML document
	 */
	private String textDocument;
	/**
	 * Property to store the tokens collected from the HTML document
	 */
	private List<String> tokenList;
	
	/**
	 * Deals with the invocation of methods for HTML document processing
	 * from the input URL and a given set of input stopwords
	 * @param urlString
	 * @param stopwordSet
	 */
	public HTMLProcessor(String urlString, Set<String> stopwordSet){
		readHTML(urlString);
		extractTextFromHtml();
		extractTokenFromText();
		removePunctuation();
		removeStopwords(stopwordSet);
		stemwords();
	}
	
	/**
	 * Reads the HTML source from a URL
	 * @param urlString
	 */
	public void readHTML(String urlString){
		URLConnection urlConnection = null;
		BufferedReader br = null;
		try{
			URL url = new URL(urlString);
			urlConnection = url.openConnection();
			br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			String inputLine = new String();
			StringBuilder stringBuilder = new StringBuilder();
			while ((inputLine = br.readLine())!=null){
				stringBuilder.append(inputLine+"\n");
			}
			htmlDocument = stringBuilder.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(br!=null)
			try{
				br.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Extracts the text content from the HTML. 
	 * It uses JSoup framework which removes the HTML, CSS, jQuery and JavaScript content.
	 */
	public void extractTextFromHtml(){
		textDocument = Jsoup.parse(htmlDocument).text();
	}
	
	/**
	 * Extracts tokens from the text content.
	 */
	public void extractTokenFromText(){
		StringTokenizer tk = new StringTokenizer(textDocument," ");
		tokenList = new ArrayList<String>();
		while(tk.hasMoreTokens()){
			tokenList.add(tk.nextToken());
		}
	}
	
	/**
	 * Removes punctuation and unwanted spaces from the token list.
	 */
	public void removePunctuation(){
		for(int i=0; i<tokenList.size(); i++){
			String token = tokenList.get(i).trim();
			token = token.replaceAll("[^a-zA-Z0-9 ]", "");
			tokenList.set(i, token);
		}
		tokenList.removeAll(Arrays.asList(null,""));
	}
	
	/**
	 * Removes stopwords from the token list.
	 * @param stopwordSet
	 */
	public void removeStopwords(Set<String> stopwordSet){
		for(int i=0; i<tokenList.size(); i++){
			if(stopwordSet.contains(tokenList.get(i))){
				tokenList.remove(tokenList.get(i));
			}
		}
	}
	
	/**
	 * Uses Porter Stemmer algorithm to stem the words in the token list.
	 */
	public void stemwords(){
		Stemmer stemmer = new Stemmer();
		for(int i=0; i<tokenList.size(); i++){
			String tokenString = tokenList.get(i).toLowerCase();
			char[] tokenCharArray = tokenString.toCharArray();
			stemmer.add(tokenCharArray,tokenCharArray.length);
			stemmer.stem();
			tokenList.set(i, stemmer.toString());
		}
	}
	
	/**
	 * Accessor for tokenList
	 * @return
	 */
	public List<String> getTokenList() {
		return tokenList;
	}
	/**
	 * Modifier for tokenList
	 * @param tokenList
	 */
	public void setTokenList(List<String> tokenList) {
		this.tokenList = tokenList;
	}
}
