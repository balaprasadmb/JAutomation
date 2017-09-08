package com.balaprasad.testng.listener;

import java.util.*;

import org.testng.*;
import org.testng.xml.XmlSuite;
import org.testng.ISuite;
import org.testng.ISuiteResult;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.*;
import com.balaprasad.testng.TestLogger;
import com.balaprasad.testng.Mailer;
import com.balaprasad.testng.TestNGConstant;

public class AurusReportListener implements IReporter {

    private static final String OUTPUT_FOLDER = System.getProperty("user.dir") + "/target/";
    private static final String FILE_NAME = "Extent_Report.html";
    private ExtentReports extent;
    ExtentTest parent;
    ExtentTest test;
    String category;
    int testCount = 0;
    int passedCount = 0;
    int failCount = 0;
    HashMap<String, Integer []> testsCounts;

    public void generateReport(List <XmlSuite> xmlSuites, List <ISuite> suites, String outputDirectory) {
        init();
        testsCounts = new HashMap<String, Integer[]>();
        for (ISuite suite : suites){
        	Map <String, ISuiteResult> result = suite.getResults();
        	for (ISuiteResult r : result.values()) {
        		ITestContext context = r.getTestContext();
        		int total = context.getAllTestMethods().length;
        		testCount += total;
        		int pass = context.getPassedTests().size();
        		passedCount += pass;
        		int fail = context.getFailedTests().size();
        		failCount += fail;
        		Integer [] status = {total, pass, fail};
        		testsCounts.put(suite.getName(), status);
            }
        }
        extent.setTestRunnerOutput("<br/>Total Tests: " + testCount + "<br/>Passed Tests: " + passedCount + "<br/>Failed Tests: " + failCount
        		+ "<br/><h3>Pass: " + ((float)passedCount/testCount) * 100 + "%, Fail: " + ((float)failCount/testCount) * 100 + "%</h3>");
        for (ISuite suite : suites) {
        	category = suite.getName();
        	parent = extent.createTest(category);
        	Integer [] status = testsCounts.get(category);
        	extent.setTestRunnerOutput("<h3>" + category + "</h3>");
        	extent.setTestRunnerOutput("<h5>Total Tests:" + status[0] + ", Passed: " + status[1] + ", Failed: " + status[2] +
        			", Pass: " + ((float)status[1]/status[0]) * 100 + "%, Fail: " + ((float)status[2]/status[0]) * 100 + "%</h5>");
        	Map <String, ISuiteResult> result = suite.getResults(); 
            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();
                buildTestNodes(context.getFailedTests(), Status.FAIL);
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
                buildTestNodes(context.getPassedTests(), Status.PASS);
            }
        }
        for (String s : Reporter.getOutput()) {
            extent.setTestRunnerOutput(s);
        }
        extent.flush();
        Mailer.mail();
    }
    
    private void init() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
        htmlReporter.config().setDocumentTitle("Extent Report");
        htmlReporter.config().setReportName("Extent Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setReportUsesManualConfiguration(true);
        extent.setAnalysisStrategy(AnalysisStrategy.SUITE);
        extent.setSystemInfo("OS:", System.getProperty("os.name"));
        extent.setSystemInfo("Architecture:", System.getProperty("os.arch"));
        extent.setSystemInfo("System Version:", System.getProperty("os.version"));
        extent.setSystemInfo("Java Version:", System.getProperty("java.version"));
        extent.setSystemInfo("User:", System.getProperty("user.name"));
        extent.setSystemInfo("App Name:", TestNGConstant.REQUEST_API.substring(0, TestNGConstant.REQUEST_API.indexOf("/", 9)));
    }
    
    private void buildTestNodes(IResultMap tests, Status status) {
    	HashMap info_log;
    	String [] test_result = {"", ""};
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = parent.createNode(result.getMethod().getMethodName());
                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);
                if(TestLogger.logger.containsKey(result.getInstanceName())){
                	info_log = TestLogger.logger.get(result.getInstanceName());
                	if(info_log.containsKey(result.getMethod().getMethodName())){
                		test_result = (String[]) info_log.get(result.getMethod().getMethodName());
                		if(test_result[1] != ""){
                			test.debug(test_result[1]);
                		}
                	}
                }
                extent.setTestRunnerOutput("<pre style='background-color: cyan'>" + result.getMethod().getMethodName() + "</pre>");
                extent.setTestRunnerOutput(test_result[0]);
                test.log(status, "Test " + status.toString().toLowerCase() + "ed<br/>" + test_result[0]);
                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                }
                test.getModel().setStartTime(getTime(result.getStartMillis()));
                test.getModel().setEndTime(getTime(result.getEndMillis()));
            }
        }
    }
    
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();      
    }
}
