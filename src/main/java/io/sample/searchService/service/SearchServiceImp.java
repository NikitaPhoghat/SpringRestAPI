package io.sample.searchService.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImp implements SearchparaService{

	@SuppressWarnings("unchecked")
	@Override
	public String getWordCount(InputStream targetFile, InputStream targetJsonFile) {
		// TODO Auto-generated method stub
		
		
		String str = "";
		Object obj = null;
		JSONParser parser = new JSONParser();
		try {
			str = IOUtils.toString(targetFile, "UTF-8");
			obj = parser.parse(IOUtils.toString(targetJsonFile, "UTF-8"));
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			return "Some Exception has occured";
		}

		JSONObject jsonObject = (JSONObject) obj;
		JSONArray wordList = (JSONArray) jsonObject.get("searchText");
		HashMap<String, Integer> mapCount = new HashMap<String, Integer>();

		for(Object word : wordList){

			String builder = word.toString().toLowerCase();
			int count = countOccurrences(str, builder);
			mapCount.put((String) word, count);

		}
		JSONObject json = new JSONObject();
		json.put("counts", mapCount);

		return json.toString();
		
	}

	@Override
	public String getHighestCount(InputStream targetFile, int count) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		Map<String, Integer> wordMap = new HashMap<String, Integer>();

		String str = null;
		int value =0;
		try {
			str = IOUtils.toString(targetFile, "UTF-8").toLowerCase().replaceAll("[,.;]", "");
			str = str.replace(System.getProperty("line.separator"), " ");
		} catch (IOException e) {
			e.printStackTrace();
			return "Some Exception has occured";
		}
		
		String words[] = str.split(" ");
		
		for(String tmp : words)
			if(wordMap.containsKey(tmp.trim())){
				wordMap.put(tmp, wordMap.get(tmp.trim())+1);
			} else {
				wordMap.put(tmp.trim(), 1);
			}

		List<Entry<String, Integer>> list = sortByValue(wordMap);
		for(Map.Entry<String, Integer> entry:list){
			++value;
			if(value<=count){
				sb.append(entry.getKey()+"|"+entry.getValue());
				sb.append(System.getProperty("line.separator"));
			}
		}		
		return sb.toString();
	}
		

	
	
	
	public static int countOccurrences(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0;
		int pos = 0;
		int idx;
		
		str = str.toLowerCase();
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}
	
	
	public static List<Entry<String, Integer>> sortByValue(Map<String, Integer> wordMap){
		Set<Entry<String, Integer>> set = wordMap.entrySet();
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
				{
			public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
			{
				return (o2.getValue()).compareTo( o1.getValue());
			}
				} );
		return list;
	}
}


