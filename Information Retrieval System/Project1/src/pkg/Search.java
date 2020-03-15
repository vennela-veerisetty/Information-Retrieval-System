package pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Search
 */
@WebServlet(name="Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Search() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String searchtext=request.getParameter("searchtext");
		System.out.println(request.getParameter("searchtext"));
		List<String> topic_list=new ArrayList<String>();
		List<String> city_list=new ArrayList<String>();
		List<String> language_list=new ArrayList<String>();
		
		int topic_count,city_count,language_count;
		topic_count=city_count=language_count=0;
		if(request.getParameter("crime").equals("true")){
			topic_list.add("crime");
			topic_count++;}
		if(request.getParameter("politics").equals("true")){
			topic_list.add("politics");
			topic_count++;}
		if(request.getParameter("environment").equals("true")){
			topic_list.add("environment");
			topic_count++;}
		if(request.getParameter("infra").equals("true")){
			topic_list.add("infra");
			topic_count++;}
		if(request.getParameter("socialunrest").equals("true")){
			topic_list.add("social unrest");
			topic_count++;}
		if(request.getParameter("nyc").equals("true")){
			city_list.add("nyc");
			city_count++;}
		if(request.getParameter("delhi").equals("true")){
			city_list.add("delhi");
			city_count++;}
		if(request.getParameter("paris").equals("true")){
			city_list.add("paris");
			city_count++;}
		if(request.getParameter("mexicocity").equals("true")){
			city_list.add("mexico city");
			city_count++;}
		if(request.getParameter("bangkok").equals("true")){
			city_list.add("bangkok");
			city_count++;}
		if(request.getParameter("en").equals("true")){
			language_list.add("en");
			language_count++;}
		if(request.getParameter("hi").equals("true")){
			language_list.add("hi");
			language_count++;}
		if(request.getParameter("fr").equals("true")){
			language_list.add("fr");
			language_count++;}
		if(request.getParameter("es").equals("true")){
			language_list.add("es");
			language_count++;}
		if(request.getParameter("th").equals("true")){
			language_list.add("th");
			language_count++;}
		if(topic_count==5)
			topic_count=0;
		if(city_count==5)
			city_count=0;
		if(language_count==5)
			language_count=0;
		int count=0;
		if(topic_count!=0)
			count++;
		if(city_count!=0)
			count++;
		if(language_count!=0)
			count++;
		if(count!=0)
			searchtext="("+searchtext+")";
		if(count>0){
			if(topic_count!=0)
			searchtext+=" AND (";
		while(topic_list.size()>0){
			searchtext+="topic:"+topic_list.get(0);
			topic_list.remove(0);
		if(topic_list.size()>0)
			searchtext+=" OR ";
		if(topic_list.size()==0)
			searchtext+=")";}
		if(topic_count!=0)
		count--;}
		if(count>0){
			if(city_count!=0)
			searchtext+=" AND (";
		while(city_list.size()>0){
			searchtext+="city:"+city_list.get(0);
			city_list.remove(0);
		if(city_list.size()>0)
			searchtext+=" OR ";
		if(city_list.size()==0)
			searchtext+=")";}
		if(city_count!=0)
		count--;}
		if(count>0){
			if(language_count!=0)
			searchtext+=" AND (";
		while(language_list.size()>0){
			searchtext+="lang:"+language_list.get(0);
			language_list.remove(0);
		if(language_list.size()>0)
			searchtext+=" OR ";
		if(language_list.size()==0)
			searchtext+=")";}
		if(language_count!=0)
		count--;}
		PrintWriter out=response.getWriter();
		System.out.println(searchtext);
		
		String output=json_to_trec.jsontotrec(searchtext);
		System.out.println(output);
		JSONObject json=new JSONObject(output).getJSONObject("response");
	    
	    
	    JSONArray tweet_texts=(JSONArray)json.get("docs");
	    System.out.println(output);List<String> l;
        String result="{\"one\":";
        result+="\"";
        result+="<!DOCTYPE html><html><head><meta charset='UTF-8' pageEncoding='UTF-8'></head><body>";
	    for(int i=0;i<tweet_texts.length();i++){
	    output=((JSONObject) tweet_texts.get(i)).get("user.profile_image_url").toString();
	    output=output.substring(2, output.length()-2);
	    result+="<img src='"+output+"' alt='Trulli' width='35' height='22'>";
	    output=((JSONObject) tweet_texts.get(i)).get("user.name").toString();
	    output=output.substring(2, output.length()-2);
	    result+=" "+output;
	    output=((JSONObject) tweet_texts.get(i)).get("text").toString();
	    output=output.substring(2, output.length()-2);
	    result+="<p>"+output+"</p>";
	    String link="https://twitter.com/anyuser/status/"+((JSONObject) tweet_texts.get(i)).get("id").toString();
	    result+="<p><a href='"+link+"'"+"target='_blank'>";
		result+=link;
		result+="</a></p>";
		result+="<hr>";
		}
	    result+="</body></html>\"";
	    result+="}";
	    System.out.println(result);
	    out.println(result);
	    out.flush();
		out.close();
	}

}
