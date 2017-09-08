package com.balaprasad.testng;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import jxl.*;
import jxl.read.biff.BiffException;

import org.jpos.iso.*;
import org.json.*;

import com.balaprasad.constants.Constants;
import com.balaprasad.testng.TestLogger;

public class TestNGUtility extends TestNGConstant{

    private static int sequenceNumber = Integer.parseInt(new SimpleDateFormat("hhmmss").format(new Date()));
    private static int plccSequenceNumber = 0;

    public static List<JSONObject> getInputData(String processorName) throws BiffException, IOException, JSONException {
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        File file = new File(System.getProperty("user.dir") + FILE_PATH);
        Workbook workbook = Workbook.getWorkbook(file);
        Sheet sheet = workbook.getSheet(1);
        Cell [] labelRow =  sheet.getRow(1);
        for(int row = 2; row < sheet.getRows(); row++) {
            JSONObject jsonObject = new JSONObject();
            if (null != sheet.getCell(0, row).getContents() && sheet.getCell(0, row).getContents().equalsIgnoreCase(processorName)) {
            	for(int col = 1; col < labelRow.length; col++){
            		jsonObject.put(labelRow[col].getContents(), sheet.getCell(col, row).getContents());
            	}
                jsonObjects.add(jsonObject);
            }
        }
        return jsonObjects;
    }

    public static JSONObject selectRecordType(String entryType, String cardType, List<JSONObject> jsonObjects) throws JSONException{
        JSONObject resJSONObject = new JSONObject();
        for (JSONObject object : jsonObjects) {
            if (object.has(ENTRY_TYPE_FIELD) && object.has(CARD_TYPE_FIELD)) {
                if (object.getString(ENTRY_TYPE_FIELD).equalsIgnoreCase(entryType) && object.getString(CARD_TYPE_FIELD).equalsIgnoreCase(cardType)) {
                    resJSONObject = object;
                    resJSONObject.remove(ENTRY_TYPE_FIELD);
                    resJSONObject.remove(CARD_TYPE_FIELD);
                    break;
                }
            }
        }
        return resJSONObject;
    }

    public static int getServiceStatus(){
    	int status=0;
    	try{
	    	HttpURLConnection connection = null;
	    	URL url = new URL(AURUS_REQUEST_API);
	    	connection = (HttpURLConnection) url.openConnection();
	    	status = connection.getResponseCode();
	    	connection.disconnect();
    	}catch(Exception e){
    		TestLogger.fail("Web Service Down" + e);
    	}
    	return status;
    }

    public static JSONObject sendRequest(String request){
    	HttpURLConnection connection = null;
        JSONObject jsonObjectPayLoad = new JSONObject();
    	try{
	        JSONObject jsonObject = new JSONObject(request);
	        int status = getServiceStatus();
	        if(status == 503 || status == 404 || status == 500){
	        	System.out.println("Web Service Responded with Status: " + status);
	        	TestLogger.fail("Web Service Responded with Status: " + status);
	        }
	        
	        URL url = new URL(AURUS_REQUEST_API);
	        connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setDoOutput(true);
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.connect();
	        String payload = "STX" + ISOUtil.byte2hex(jsonObject.get("payload").toString().getBytes()) + "ETX";
	        jsonObject.put("payload", payload);
	        System.out.println("Request: " + jsonObject);
	        OutputStream out = connection.getOutputStream();
	        out.write(jsonObject.toString().getBytes());
	        out.flush();
	
	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder stringBuilder = new StringBuilder();
	        String res = new String();
	        while ((res = in.readLine()) != null) {
	            stringBuilder.append(res);
	        }
	        System.out.println("Response: " + stringBuilder);
	        if (null != stringBuilder) {
	            JSONObject jsonObject2 = new JSONObject(stringBuilder.toString());
	            String payLoad = new String(ISOUtil.hex2byte(jsonObject2.getString("response").replaceAll(Constants.STX, Constants.EMPTY_STRING).replaceAll(Constants.ETX, Constants.EMPTY_STRING)));
	            jsonObjectPayLoad = new JSONObject(payLoad);
	        }
    	}
        catch(Exception e){
        	TestLogger.fail("Exception Occured While Sending Request: " + e);
        }
        return jsonObjectPayLoad;
    }

    public static String getSequenceNumber(){
        sequenceNumber++;
        String outSequence="";
        try {
            outSequence =  ISOUtil.padleft(Integer.toString(sequenceNumber), 6, '0');
        } catch (ISOException e) {
            e.printStackTrace();
        }
        return outSequence;
    }

    public static String getPlccSequenceNumber(){
    	plccSequenceNumber++;
        String outSequence="";
        try {
            outSequence =  ISOUtil.padleft(Integer.toString(plccSequenceNumber), 3, '0');
        } catch (ISOException e) {
            e.printStackTrace();
        }
        return outSequence;
    }

    public static String getRandomNumberString(int length){
        String random="";
        Random r = new Random();
        for(int l=0; l<length; l++){
        	int rand = r.nextInt(10);
			if(random=="" && rand <= 0){
				length += 1;
			}
			else
				random = random + rand;
        }
        return random;
    }
}
