package controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniversityController {

	public Client client;
	
	public UniversityController() throws IOException{
		this.client = new Client();
	}
	
	public List<String[]> getUniversity() {
		List<String[]> rowList = new ArrayList<String[]>();
		//server
		return rowList;
	}
	
	
	public List<String> getFaculties() {
		//server requirenment
		List<String> faculties = new ArrayList<String>();
		return faculties;
	}
	
	public List<String> getDepartments() {
		List<String> departments = new ArrayList<String>();
		//server
		return departments;
	}	
	
	public Set<String> getDegrees(){
		//server requirenment
		Set<String> degreeT = new HashSet<>();
		return degreeT;
	}
	
	public Set<String> getDegreesSc(){
		Set<String> degreeScience = new HashSet<>();
		//server
		return degreeScience;
	}
	
}
