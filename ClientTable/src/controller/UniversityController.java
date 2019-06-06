package controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniversityController {

	public Client client;
	public List<String[]> uni;
	
	public UniversityController(List<String[]> uni) throws IOException{
		//this.client = new Client();
		this.uni = uni;
	}
	
	public List<String[]> getUniversity() {
		List<String[]> rowList = uni;
		//server
		return uni;
	}
	
	
	public Set<String> getFaculties() {
		//server requirenment
		Set<String> faculties = new HashSet<String>();
		for(int i = 0; i< uni.size(); i++) {
			faculties.add(uni.get(i)[0]);
		}
		return faculties;
	}
	
	public Set<String> getDepartments() {
		Set<String> departments = new HashSet<String>();
		//server
		for(int i = 0; i< uni.size(); i++) {
			departments.add(uni.get(i)[1]);
		}
		
		return departments;
	}	
	
	public Set<String> getDegrees(){
		//server requirenment
		Set<String> degreeT = new HashSet<>();
		for(int i = 0; i< uni.size(); i++) {
			degreeT.add(uni.get(i)[3]);
		}
		return degreeT;
	}
	
	public Set<String> getDegreesSc(){
		Set<String> degreeScience = new HashSet<>();
		//server
		for(int i = 0; i< uni.size(); i++) {
			degreeScience.add(uni.get(i)[4]);
		}
		return degreeScience;
	}
	
}
