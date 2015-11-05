package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class YahooAPI {

    
    public HashMap<String, Float> update(ArrayList<String> tickers) throws IOException {
        HashMap<String, Float> stocks = new HashMap<String, Float>();
    	String urlFHalf = "https://query.yahooapis.com/v1/public/yql?q=select%20Symbol%2CLastTradePriceOnly%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(";
        String urlSHalf = "%22APPL%22)&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    	String url = "";
    	ArrayList<String> arr = new ArrayList<String>();
    	
    	url = url+urlFHalf;
    	
    	for(String ticker : tickers){
    		url = url+"%22"+ticker+"%22%2C";
    	}
    	
    	url = url+urlSHalf;
    	
        URL YahooURL = new URL(url);
        HttpURLConnection con = (HttpURLConnection) YahooURL.openConnection();

        con.setRequestMethod("GET");
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);
	
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        inputLine = response.toString();
        in.close();
        
        while(inputLine.contains("<quote>")){
        	
        	int indexStart = inputLine.indexOf("<quote>")+"<quote>".length();
        	int indexEnd = inputLine.indexOf("</quote>");
        	arr.add(inputLine.substring(indexStart, indexEnd));
        	
        	inputLine = inputLine.substring(indexEnd+"</quote>".length());
        
        	
        }
        
        for(String data : arr){
        	int indexPrice = data.indexOf("<LastTradePriceOnly>")+"<LastTradePriceOnly>".length();
        	int indexPriceEnd = data.indexOf("</LastTradePriceOnly");
        	int indexTicker = (data.indexOf("<Symbol>")+("<Symbol>".length()));
        	int indexTickerEnd = data.indexOf("</Symbol>");
        	try{
        	stocks.put(data.substring(indexTicker, indexTickerEnd), Float.valueOf(data.substring(indexPrice,indexPriceEnd )));
        	}catch(Exception e1){
        		System.out.println("This stock has no data from Yahoo. Whooo");
        	}
        }
        
        //System.out.println(response.toString());
		return stocks;
        
        
    }
    
   
        
    
}
