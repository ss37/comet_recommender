package edu.pitt.sis.documentmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class KeywordModelGenerator {
	
	/**
	 * Calculates the count of keywords in a given list - used for "interesting" documents set
	 * @param list
	 * @return
	 */
	public Map<String, Integer> createAggregateKeywordsList(List<List<String>> list){
		Map<String,Integer> aggregateKeywordsMap = new HashMap<String,Integer>();
		for(List<String> tokenlist: list){
			for(String token: tokenlist){
				if(aggregateKeywordsMap.containsKey(token))
					aggregateKeywordsMap.put(token, aggregateKeywordsMap.get(token)+1);
				else
					aggregateKeywordsMap.put(token,1);
			}
		}
		return aggregateKeywordsMap;
	}
	
	/**
	 * Calculates the term frequency (TF) of each keyword in the given list
	 * @param keywordsList
	 * @return
	 */
	public Map<String, Integer> calculateTermFrequency(List<String> keywordsList){
		Map<String,Integer> keywordsMap = new HashMap<String,Integer>();
		for(String keyword: keywordsList){
			if(keywordsMap.containsKey(keyword))
				keywordsMap.put(keyword, keywordsMap.get(keyword)+1);
			else
				keywordsMap.put(keyword, 1);
		}
		return keywordsMap;
	}
	
	/**
	 * Calculate the Inverse Document Frequency (IDF) for each keyword in the given list
	 * @param list
	 * @return
	 */
	public Map<String, Double> calculateInverseDocumentFrequency(List<List<String>> list){
		Map<String,Double> idfMap = new HashMap<String,Double>();
		
		List<Set<String>> documentlist = new ArrayList<Set<String>>();
		for(List<String> tokenlist: list){
			Set<String> keywordSet = new HashSet<String>();
			for(String token: tokenlist){
				keywordSet.add(token);
			}
			documentlist.add(keywordSet);
		}
		for(Set<String> document: documentlist){
			for(String tokenInDocument: document){
				if(idfMap.containsKey(tokenInDocument))
					idfMap.put(tokenInDocument, idfMap.get(tokenInDocument)+1);
				else
					idfMap.put(tokenInDocument, 1.0);
			}
		}
		Set<Entry<String,Double>> idfMapEntrySet = idfMap.entrySet();
		for(Entry<String,Double> idfMapEntry: idfMapEntrySet){
			idfMap.put(idfMapEntry.getKey(),Math.log(idfMapEntry.getValue()/list.size()));
		}
		
		return idfMap;
	}
	
	/**
	 * Calculate the TF*IDF 
	 * @param keywordsTFMap
	 * @param keywordsIDFMap
	 * @return
	 */
	public Map<String, Double> calculateTF_IDF(Map<String,Integer> keywordsTFMap, Map<String,Double> keywordsIDFMap){
		Map<String,Double> keywordsTfIdfMap = new HashMap<String,Double>();
		Set<Entry<String,Integer>> keywordsTFMapEntrySet = keywordsTFMap.entrySet();
		for(Entry<String,Integer> keywordsTFMapEntry: keywordsTFMapEntrySet){
			String keyword = keywordsTFMapEntry.getKey();
			Double TF_IDF = keywordsTFMapEntry.getValue()*keywordsIDFMap.get(keyword);
			keywordsTfIdfMap.put(keyword, TF_IDF);
		}
		return keywordsTfIdfMap;
	}
	
	/**
	 * Calculates the cosine similarity of the keywords in document and query
	 * @param queryMap
	 * @param documentMap
	 * @return
	 */
	public Double calculateCosineSimilarity(Map<String,Double> queryMap, Map<String,Double> documentMap){
		Double dotProduct = 0.0;
		
		Set<String> keywords = new HashSet<String>();
		keywords.addAll(queryMap.keySet());
		keywords.addAll(documentMap.keySet());
		
		for(String keyword: keywords){
			Double query = 0.0;
			if(queryMap.containsKey(keyword))
				query = queryMap.get(keyword);
			Double document = 0.0;
			if(documentMap.containsKey(keyword))
				document = documentMap.get(keyword);
			dotProduct = dotProduct + (query*document);
		}
		
		Double magQuery = 0.0;
		for(String queryKeyword: queryMap.keySet()){
			magQuery = magQuery + Math.pow(queryMap.get(queryKeyword),2);
		}
		magQuery = Math.sqrt(magQuery);
		
		Double magDocument = 0.0;
		for(String docKeyword: documentMap.keySet()){
			magDocument = magDocument + Math.pow(documentMap.get(docKeyword), 2);
		}
		magDocument = Math.sqrt(magDocument);
		
		Double cosineSimilarity = dotProduct/(magQuery*magDocument);
		return cosineSimilarity;
	}
}
