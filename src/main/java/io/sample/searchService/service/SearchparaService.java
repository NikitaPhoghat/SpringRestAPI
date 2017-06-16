package io.sample.searchService.service;
import java.io.InputStream;
	
	public interface SearchparaService {
		
		public String getWordCount(InputStream targetFile,
				InputStream targetJsonFile);

		public String getHighestCount(InputStream targetFile, int count);

	}