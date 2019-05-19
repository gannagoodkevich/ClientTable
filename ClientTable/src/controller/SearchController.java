package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class SearchController {
	
	private Client client;

	public SearchController() throws IOException{
		this.client = new Client();
	}

	public List<String[]> listenerSearchByFaculty(String faculty, String degreeeName) {
		List<String[]> rowList = new ArrayList<String[]>();
		//server part
		return rowList;
	}

	public List<String[]> listenerSearchByName(String department, String name) {
		List<String[]> rowList = new ArrayList<String[]>();
		//server
		return rowList;

	}

	public List<String[]> listenerSearchByYear(String year1, String year2) {
		List<String[]> rowList = new ArrayList<String[]>();
		//server
		return rowList;
	}

}
