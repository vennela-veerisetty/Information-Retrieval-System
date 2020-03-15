package pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Analysis
 */
@WebServlet(name="Analysis")
public class Analysis extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Analysis() {
        super();
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
		//doGet(request, response);
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
		String field=request.getParameter("analysis");
		PrintWriter out=response.getWriter();
		System.out.println(searchtext);
		String result="";JSONObject json;
		int negative,neutral,positive;JSONArray tweet_texts;
		negative=0;neutral=0;positive=0;String output = "";
		if(!(request.getParameter("analysis").equals("sentimental"))){
		output=Analysis1.jsontotrec(searchtext,field);
		System.out.println(output);
		json=new JSONObject(output).getJSONObject("facet_counts");
		output=json.toString();
		json=new JSONObject(output).getJSONObject("facet_fields");
	    
	    tweet_texts=(JSONArray)json.get(request.getParameter("analysis"));}
		else{
			output=json_to_trec.jsontotrec(searchtext);
			 json=new JSONObject(output).getJSONObject("response");
		    tweet_texts=(JSONArray)json.get("docs");
	    System.out.println(output);
	    	for(int i=0;i<tweet_texts.length();i++){
	    	output=((JSONObject) tweet_texts.get(i)).get("text").toString();
	    int k=SentimentAnalyzer.findSentiment(output);
	    if(k==2)
	    	neutral++;
	    if(k>2)
	    	positive++;
	    if(k<2)
	    	negative++;}
	    double neutral1=neutral*100/(neutral+positive+negative);
	    double positive1=positive*100/(neutral+positive+negative);
	    double negative1=negative*100/(neutral+positive+negative);
	    result="{";
	    result+="\"neutral\":"+neutral1+",";
	    
	    result+="\"positive\":"+positive1+",";
	    result+="\"negative\":"+negative1;
	    result+="}";}
	    if(request.getParameter("analysis").equals("topic")){
	    int total=tweet_texts.getInt(1)+tweet_texts.getInt(3)+tweet_texts.getInt(5)
	    +tweet_texts.getInt(7)+tweet_texts.getInt(9);
	    
	    result="{";
	    
	    if(tweet_texts.getString(0).equals("social unrest"))
	    	result+="\""+"socialunrest"+"\":"+(double)tweet_texts.getInt(1)*100/total+",";
	    else
	    result+="\""+tweet_texts.getString(0)+"\":"+(double)tweet_texts.getInt(1)*100/total+",";
	    if(tweet_texts.getString(2).equals("social unrest"))
	    	result+="\""+"socialunrest"+"\":"+(double)tweet_texts.getInt(3)*100/total+",";
	    else
	    result+="\""+tweet_texts.getString(2)+"\":"+(double)tweet_texts.getInt(3)*100/total+",";
	    if(tweet_texts.getString(4).equals("social unrest"))
	    	result+="\""+"socialunrest"+"\":"+(double)tweet_texts.getInt(5)*100/total+",";
	    else
	    result+="\""+tweet_texts.getString(4)+"\":"+(double)tweet_texts.getInt(5)*100/total+",";
	    if(tweet_texts.getString(6).equals("social unrest"))
	    	result+="\""+"socialunrest"+"\":"+(double)tweet_texts.getInt(7)*100/total+",";
	    else
	    result+="\""+tweet_texts.getString(6)+"\":"+(double)tweet_texts.getInt(7)*100/total+",";
	    if(tweet_texts.getString(8).equals("social unrest"))
	    	result+="\""+"socialunrest"+"\":"+(double)tweet_texts.getInt(9)*100/total;
	    else
	    result+="\""+tweet_texts.getString(8)+"\":"+(double)tweet_texts.getInt(9)*100/total;
	    	    result+="}";}
	    
	    if(request.getParameter("analysis").equals("city")){
	    	tweet_texts=(JSONArray)json.get(request.getParameter("analysis"));
	    	
	    	int total=tweet_texts.getInt(1)+tweet_texts.getInt(3)+tweet_texts.getInt(5)
		    +tweet_texts.getInt(7)+tweet_texts.getInt(9);
		    
		    
	    result="{";
	    if(tweet_texts.getString(0).equals("mexico city"))
	    	result+="\""+"mexicocity"+"\":"+(double)tweet_texts.getInt(1)*100/total+",";
	    else
	    result+="\""+tweet_texts.getString(0)+"\":"+(double)tweet_texts.getInt(1)*100/total+",";
	    if(tweet_texts.getString(2).equals("mexico city"))
	    	result+="\""+"mexicocity"+"\":"+(double)tweet_texts.getInt(3)*100/total+",";
	    else
	    result+="\""+tweet_texts.getString(2)+"\":"+(double)tweet_texts.getInt(3)*100/total+",";
	    if(tweet_texts.getString(4).equals("mexico city"))
	    	result+="\""+"mexicocity"+"\":"+(double)tweet_texts.getInt(5)*100/total+",";
	    else
	    result+="\""+tweet_texts.getString(4)+"\":"+(double)tweet_texts.getInt(5)*100/total+",";
	    if(tweet_texts.getString(6).equals("mexico city"))
	    	result+="\""+"mexicocity"+"\":"+(double)tweet_texts.getInt(7)*100/total+",";
	    else
	    result+="\""+tweet_texts.getString(6)+"\":"+(double)tweet_texts.getInt(7)*100/total+",";
	    if(tweet_texts.getString(8).equals("mexico city"))
	    	result+="\""+"mexicocity"+"\":"+(double)tweet_texts.getInt(9)*100/total;
	    else
	    result+="\""+tweet_texts.getString(8)+"\":"+(double)tweet_texts.getInt(9)*100/total;
	    result+="}";}
	    
	    if(request.getParameter("analysis").equals("lang")){
	    	tweet_texts=(JSONArray)json.get(request.getParameter("analysis"));
	    int total=0;
	    result="{";int i;int a = 0,b=0,c=0,d=0,e=0;
	    for(i=0;i<tweet_texts.length();i=i+2){
	    	if(tweet_texts.getString(i).equals("en")){
	    	a=i+1;
	    	total+=tweet_texts.getInt(i+1);}
	    	if(tweet_texts.getString(i).equals("hi")){
		    	b=i+1;
		    	total+=tweet_texts.getInt(i+1);}
	    	if(tweet_texts.getString(i).equals("fr")){
		    	c=i+1;
		    	total+=tweet_texts.getInt(i+1);}
	    	if(tweet_texts.getString(i).equals("es")){
		    	d=i+1;
		    	total+=tweet_texts.getInt(i+1);}
	    	if(tweet_texts.getString(i).equals("th")){
		    	e=i+1;
	    	total+=tweet_texts.getInt(i+1);}}
	    result+="\"en\":"+(double)tweet_texts.getInt(a)*100/total+",";
	    result+="\"hi\":"+(double)tweet_texts.getInt(b)*100/total+",";
	    result+="\"fr\":"+(double)tweet_texts.getInt(c)*100/total+",";
	    result+="\"es\":"+(double)tweet_texts.getInt(d)*100/total+",";
	    result+="\"th\":"+(double)tweet_texts.getInt(e)*100/total;
	    result+="}";
	    }
	    if(request.getParameter("analysis").equals("tweet_date")){
	    	tweet_texts=(JSONArray)json.get(request.getParameter("analysis"));	    
	    result="{";
	    result+="\"st\":"+tweet_texts;
	   
	    result+="}";
	    }
	    if(request.getParameter("analysis").equals("hashtags")){
	    	tweet_texts=(JSONArray)json.get(request.getParameter("analysis"));	    
	    result="{";
	    result+="\"st\":"+tweet_texts;
	   
	    result+="}";
	    }
	    if(request.getParameter("analysis").equals("entities.user_mentions.name")){
	    	tweet_texts=(JSONArray)json.get(request.getParameter("analysis"));	    
	    	result="{";
	    result+="\"st\":"+tweet_texts;
	   
	    result+="}";
	    }
	    System.out.println(result);
	    out.println(result);
	    out.flush();
		out.close();
	}
	}

