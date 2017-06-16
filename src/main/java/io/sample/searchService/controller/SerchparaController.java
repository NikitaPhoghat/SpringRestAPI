package io.sample.searchService.controller;


import java.io.IOException;
import java.io.InputStream;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.sample.searchService.service.SearchparaService;

@RestController
public class SerchparaController {

		@Autowired
		private SearchparaService searchparaService;


		@RequestMapping(value = "/search", method = RequestMethod.GET,headers="Accept=application/json")
		public String getWordCountList() throws IOException, ParseException{	
			Resource resource = new ClassPathResource("para.txt");		
			Resource resourceJson = new ClassPathResource("searchText.json");		
			InputStream targetFile = resource.getInputStream();
			InputStream targetJsonFile = resourceJson.getInputStream();	
			return searchparaService.getWordCount(targetFile,targetJsonFile);

		}


		@RequestMapping(value = "/top/{count}", method = RequestMethod.GET,headers="Accept=application/csv")
		public String  getTopWordCount(@PathVariable int count) throws IOException{
			Resource resource = new ClassPathResource("para.txt");
			InputStream targetFile = resource.getInputStream();	
			return searchparaService.getHighestCount(targetFile, count);				
		}


	}
	

