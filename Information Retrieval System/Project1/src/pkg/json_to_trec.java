package pkg;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class json_to_trec {

public static String jsontotrec(String query) {
	// TODO Auto-generated method stub
	try {
		query=URLEncoder.encode(query, "UTF-8").replaceAll("\\+", "%20");
	} catch (UnsupportedEncodingException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	System.out.println(query);
	String inurl="http://18.221.162.107:8984/solr/IRF18P4/select?indent=on&q="+query+"&wt=json&rows=15";
	URL url = null;
	try {
		url = new URL(inurl);
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	HttpURLConnection conn = null;
	try {
		conn = (HttpURLConnection)url.openConnection();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		conn.setRequestMethod("GET");
	} catch (ProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    conn.setAllowUserInteraction(false);
    conn.setDoOutput(true);
    conn.setRequestProperty( "Content-type", "application/json" );
    conn.setRequestProperty( "Accept", "application/json" );
    BufferedReader rd = null;
	try {
		rd = new BufferedReader(
		        new InputStreamReader(conn.getInputStream()));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    StringBuilder sb = new StringBuilder();
    String line;
    try {
		while ((line = rd.readLine()) != null) {
		    sb.append(line);
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    String output=sb.toString();
	return output;
}
}
