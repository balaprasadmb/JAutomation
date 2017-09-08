package com.balaprasad.testng;

import java.util.*;

public class TestLogger{
	public static HashMap <String , HashMap <String, String []>> logger = new HashMap<String, HashMap<String, String[]>> ();
	public static String currentClass;

	public static void initLogger(String client){
    	StackTraceElement [] st = Thread.currentThread().getStackTrace();
    	currentClass = st[2].getClassName();
    	logger.put(currentClass, new HashMap<String, String[]>());
    	ResponseFactory.setClient(client);
    }

	public static void log(String log_string, int logLevel){
    	StackTraceElement [] st = Thread.currentThread().getStackTrace();
    	int index = logLevel + 1;
    	System.out.println(log_string);
    	log_string = log_string.replace("\n", "<br/>");
    	if (! logger.get(currentClass).containsKey(st[index].getMethodName())){
    		String [] test_result = {log_string, ""};
    		logger.get(st[index].getClassName()).put(st[index].getMethodName(), test_result);
    	}
    	else{
    		String temp = logger.get(currentClass).get(st[index].getMethodName())[0];
    		String [] test_result = {temp + "<br/>" + log_string, ""};
    		((HashMap<String, String[]>)logger.get(currentClass)).put(st[index].getMethodName(), test_result);
    	}
    }
	
	public static void log(String log_string){
		log(log_string, 2);
	}
	
	public static void fail(String reason){
		StackTraceElement [] st = Thread.currentThread().getStackTrace();
		int index = 5;
		for(int it=0; it < st.length; it++)
			if(st[it].getClassName() == currentClass){
				index = it;
				break;
		}
		if (! logger.get(currentClass).containsKey(st[index].getMethodName())){
    		String [] test_result = {"", reason};
    		logger.get(currentClass).put(st[index].getMethodName(), test_result);
    	}
		else{
    		String temp = logger.get(currentClass).get(st[index].getMethodName())[0];
    		String [] test_result = {temp, reason};
    		((HashMap<String, String[]>)logger.get(currentClass)).put(st[index].getMethodName(), test_result);
    	}
	}
}
