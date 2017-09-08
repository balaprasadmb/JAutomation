package com.balaprasad.testng.testcases;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.balaprasad.testng.TestLogger;
import com.balaprasad.testng.TestNGUtility;
import com.balaprasad.testng.TestNGConstant;


public class ElavonCreditDebitProcessorTest extends TestNGConstant {

    private JSONObject emvCC = null;
    private JSONObject swipeCC = null;
    private JSONObject keyedCC = null;
    private JSONObject emvDB = null;
    private JSONObject swipeDB = null;
    private String currentDate = "";
    private String currentTime = "";

    @BeforeTest(groups = {"Init"})
    public void readXLSFile() {
    	try{
	        List<JSONObject> jsonObjects = TestNGUtility.getInputData(PROCESSOR_ELAVON);
	        emvCC = TestNGUtility.selectRecordType(ENTRY_TYPE_EMV, CARD_TYPE_CREDIT, jsonObjects);
	        swipeCC = TestNGUtility.selectRecordType(ENTRY_TYPE_SWIPE, CARD_TYPE_CREDIT, jsonObjects);
	        keyedCC = TestNGUtility.selectRecordType(ENTRY_TYPE_KEYED, CARD_TYPE_CREDIT, jsonObjects);
	        emvDB = TestNGUtility.selectRecordType(ENTRY_TYPE_EMV, CARD_TYPE_DEBIT, jsonObjects);
	        swipeDB = TestNGUtility.selectRecordType(ENTRY_TYPE_SWIPE, CARD_TYPE_DEBIT, jsonObjects);
	        Date currentDateTime = Calendar.getInstance().getTime();
	        currentDate = new SimpleDateFormat("MMddyyyy").format(currentDateTime);
	        currentTime = new SimpleDateFormat("HHmmss").format(currentDateTime);
	        swipeCC.put("4.18", currentDate);
	        emvCC.put("4.18", currentDate);
	        keyedCC.put("4.18", currentDate);
	        keyedCC.put("4.19", currentTime);
	        swipeCC.put("4.19", currentTime);
	        emvCC.put("4.19", currentTime);
	        emvDB.put("4.18", currentDate);
	        emvDB.put("4.19", currentTime);
	        swipeDB.put("4.18", currentDate);
	        swipeDB.put("4.19", currentTime);
	        System.out.println("******************** Testcase Execution Started for Elavon Processor ********************");
	        TestLogger.initLogger(PROCESSOR_ELAVON);
    	}catch(Exception e){
    		Assert.fail("Unable Read Parameter Excel File");
    		e.printStackTrace();
        }
    }

    // ***************************EMV CREDIT************************
    // *************************************************************

    @Test(priority = 1, groups = {"Credit-EMV"})
    public void testEMVCCSale_01() {
        System.out.println("Test Case #1:");
        try{
        	TestLogger.log("1 - SALE: ");
        	Tests.testSale(emvCC, CREDIT_SALE);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 2, groups = {"Credit-EMV"})
    public void testEMVCCSaleRefund_02() {
        System.out.println("Test Case #2:");
        try{
        	Tests.testSaleRefund(emvCC, CREDIT_SALE, CREDIT_REFUND); 
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 3, groups = {"Credit-EMV"})
    public void testEMVCCSaleVoid_03() {
        System.out.println("Test Case #3:");
        try{
        	Tests.testSaleVoid(emvCC, CREDIT_SALE, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 4, groups = {"Credit-EMV"})
    public void testEMVCCSaleRefundVoid_04() {
        System.out.println("Test Case #4:");
        try{
        	Tests.testSaleRefundVoid(emvCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 5, groups = {"Credit-EMV"})
    public void testEMVCCSaleRefundVoidVoidOfSale_05() {
        System.out.println("Test Case #5:");
        try{
        	Tests.testSaleRefundVoidVoidOfSale(emvCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 6, groups = {"Credit-EMV"})
    public void testEMVCCSaleVoidVoid_06() {
        System.out.println("Test Case #6:");
        try{
        	Tests.testSaleVoidVoid(emvCC, CREDIT_SALE, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 7, groups = {"Credit-EMV"})
    public void testEMVCCSaleVoidVoidVoidOfSale_07() {
        System.out.println("Test Case #7:");
        try{
        	Tests.testSaleVoidVoidOfSale(emvCC, CREDIT_SALE, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 8, groups = {"Credit-EMV"})
    public void testEMVCCSaleReversal_08() {
        System.out.println("Test Case #8:");
        try{
        	Tests.testSaleReversal(emvCC, CREDIT_SALE, CREDIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 9, groups = {"Credit-EMV"})
    public void testEMVCCSaleRefundReversalOfRefund_09() {
        System.out.println("Test Case #9:");
        try{
        	Tests.testSaleRefundReversalOfRefund(emvCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 10, groups = {"Credit-EMV"})
    public void testEMVCCSaleVoidReversalOfVoid_10() {
        System.out.println("Test Case #10:");
        try{
        	Tests.testSaleVoidReversalOfVoid(emvCC, CREDIT_SALE, CREDIT_VOID, CREDIT_REVERSAL);    
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 11, groups = {"Credit-EMV"})
    public void testEMVCCVoidOfRefundedSale_11() {
        System.out.println("Test Case #11:");
        try{
        	Tests.testVoidOfRefundedSale(emvCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }   

    @Test(priority = 12, groups = {"Credit-EMV"})
    public void testEMVCCMultiSale_12() {
        System.out.println("Test Case #12:");
        try{
        	Tests.testMultiSale(emvCC, CREDIT_SALE);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 13, groups = {"Credit-EMV"})
    public void testEMVCCRefundOfMultiSale_13() {
        System.out.println("Test Case #13:");
        try{
        	Tests.testRefundOfMultiSale(emvCC, CREDIT_SALE, CREDIT_REFUND);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 14, groups = {"Credit-EMV"})
    public void testEMVCCVoidOfMultiSale_14() {
        System.out.println("Test Case #14:");
        try{
        	Tests.testVoidOfMultiSale(emvCC, CREDIT_SALE, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 15, groups = {"Credit-EMV"})
    public void testEMVCCVoidOfMultiRefund_15() {
        System.out.println("Test Case #15:");
        try{
        	Tests.testVoidOfMultiRefund(emvCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 16, groups = {"Credit-EMV"})
    public void testEMVCCVoidOfMultiRefundedSale_16() {
        System.out.println("Test Case #16:");
        try{
        	Tests.testVoidOfMultiRefundedSale(emvCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);   
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    
    // ****************************EMV DEBIT************************
    // *************************************************************

    @Test(priority = 17, groups = {"Debit-EMV"})
    public void testEMVDBSale_17() {
        System.out.println("Test Case #17:");
        try{
            TestLogger.log("1 - SALE: ");
        	Tests.testSale(emvDB, DEBIT_SALE);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 18, groups = {"Debit-EMV"})
    public void testEMVDBSaleRefund_18() {
        System.out.println("Test Case #18:");
        try{
        	Tests.testSaleRefund(emvDB, DEBIT_SALE, DEBIT_REFUND);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 19, groups = {"Debit-EMV"})
    public void testEMVDBSaleVoid_19() {
        System.out.println("Test Case #19:");
        try{
        	Tests.testSaleVoid(emvDB, DEBIT_SALE, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 20, groups = {"Debit-EMV"})
    public void testEMVDBSaleRefundVoid_20() {
        System.out.println("Test Case #20:");
        try{
        	Tests.testSaleRefundVoid(emvDB, DEBIT_SALE, DEBIT_REFUND, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 21, groups = {"Debit-EMV"})
    public void testEMVDBSaleRefundVoidVoidOfSale_21() {
        System.out.println("Test Case #21:");
        try{
        	Tests.testSaleRefundVoidVoidOfSale(emvDB, DEBIT_SALE, DEBIT_REFUND, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }       
    }
    
    @Test(priority = 22, groups = {"Debit-EMV"})
    public void testEMVDBSaleVoidVoid_22() {
        System.out.println("Test Case #22:");
        try{
        	Tests.testSaleVoidVoid(emvDB, DEBIT_SALE, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 23, groups = {"Debit-EMV"})
    public void testEMVDBSaleVoidVoidOfSale_23() {
        System.out.println("Test Case #23:");
        try{
        	Tests.testSaleVoidVoidOfSale(emvDB, DEBIT_SALE, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 24, groups = {"Debit-EMV"})
    public void testEMVDBSaleReversal_24() {
        System.out.println("Test Case #24:");
        try{
        	Tests.testSaleReversal(emvDB, DEBIT_SALE, DEBIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 25, groups = {"Debit-EMV"})
    public void testEMVDBSaleRefundReversalOfRefund_25() {
        System.out.println("Test Case #25:");
        try{
        	Tests.testSaleRefundReversalOfRefund(emvDB, DEBIT_SALE, DEBIT_REFUND, CREDIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 26, groups = {"Debit-EMV"})
    public void testEMVDBSaleRefundVoidRefundOfVoid_26() {
        System.out.println("Test Case #26:");
        try{
        	Tests.testSaleVoidReversalOfVoid(emvDB, DEBIT_SALE, DEBIT_VOID, CREDIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 27, groups = {"Debit-EMV"})
    public void testEMVDBMultiSale_27() {
        System.out.println("Test Case #27:");
        try{
        	Tests.testMultiSale(emvDB, DEBIT_SALE);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 28, groups = {"Debit-EMV"})
    public void testEMVDBRefundOfMultiSale_28() {
        System.out.println("Test Case #28:");
        try{
        	Tests.testRefundOfMultiSale(emvDB, DEBIT_SALE, DEBIT_REFUND);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 29, groups = {"Debit-EMV"})
    public void testEMVDBVoidOfMultiSale_29() {
        System.out.println("Test Case #29:");
        try{
        	Tests.testVoidOfMultiSale(emvDB, DEBIT_SALE, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 30, groups = {"Debit-EMV"})
    public void testEMVDBVoidOfMultiRefund_30() {
        System.out.println("Test Case #30:");
        try{
        	Tests.testVoidOfMultiRefund(emvDB, DEBIT_SALE, DEBIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 31, groups = {"Debit-EMV"})
    public void testEMVDBVoidOfMultiRefundedSale_31() {
        System.out.println("Test Case #31:");
        try{
        	Tests.testVoidOfMultiRefundedSale(emvDB, DEBIT_SALE, DEBIT_REFUND, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    //*************************SWIPE CREDIT*************************
    // *************************************************************

    @Test(priority = 32, groups = {"Credit-Swipe"})
    public void testSWIPECCSale_32() {
        System.out.println("Test Case #32:");
        try{
            TestLogger.log("1 - SALE: ");
        	Tests.testSale(swipeCC, CREDIT_SALE);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 33, groups = {"Credit-Swipe"})
    public void testSWIPECCSaleRefund_33() {
        System.out.println("Test Case #33:");
        try{
        	Tests.testSaleRefund(swipeCC, CREDIT_SALE, CREDIT_REFUND);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 34, groups = {"Credit-Swipe"})
    public void testSWIPECCSaleVoid_34() {
        System.out.println("Test Case #34:");
        try{
        	Tests.testSaleVoid(swipeCC, CREDIT_SALE, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 35, groups = {"Credit-Swipe"})
    public void testSWIPECCSaleRefundVoid_35() {
        System.out.println("Test Case #35:");
        try{
        	Tests.testSaleRefundVoid(swipeCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 36, groups = {"Credit-Swipe"})
    public void testSWIPECCSaleRefundVoidVoidOfSale_36() {
        System.out.println("Test Case #36:");
        try{
        	Tests.testSaleRefundVoidVoidOfSale(swipeCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 37, groups = {"Credit-Swipe"})
    public void testSWIPECCSaleVoidVoid_37() {
        System.out.println("Test Case #37:");
        try{
        	Tests.testSaleVoidVoid(swipeCC, CREDIT_SALE, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 38, groups = {"Credit-Swipe"})
    public void testSWIPECCSaleVoidVoidVoidOfSale_38() {
        System.out.println("Test Case #38:");
        try{
        	Tests.testSaleVoidVoidOfSale(swipeCC, CREDIT_SALE, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 39, groups = {"Credit-Swipe"})
    public void testSWIPECCSaleReversal_39() {
        System.out.println("Test Case #39:");
        try{
        	Tests.testSaleReversal(swipeCC, CREDIT_SALE, CREDIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 40, groups = {"Credit-Swipe"})
    public void testSWIPECCSaleRefundReversalOfRefund_40() {
        System.out.println("Test Case #40:");
        try{
        	Tests.testSaleRefundReversalOfRefund(swipeCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 41, groups = {"Credit-Swipe"})
    public void testSWIPECCSaleVoidReversalOfVoid_41() {
        System.out.println("Test Case #41:");
        try{
        	Tests.testSaleVoidReversalOfVoid(swipeCC, CREDIT_SALE, CREDIT_VOID, CREDIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 42, groups = {"Credit-Swipe"})
    public void testSWIPECCVoidOfRefundedSale_42() {
        System.out.println("Test Case #42:");
        try{
        	Tests.testVoidOfRefundedSale(swipeCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }   

    @Test(priority = 43, groups = {"Credit-Swipe"})
    public void testSWIPECCMultiSale_43() {
        System.out.println("Test Case #43:");
        try{
        	Tests.testMultiSale(swipeCC, CREDIT_SALE);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 44, groups = {"Credit-Swipe"})
    public void testSWIPECCRefundOfMultiSale_44() {
        System.out.println("Test Case #44:");
        try{
        	Tests.testRefundOfMultiSale(swipeCC, CREDIT_SALE, CREDIT_REFUND);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 45, groups = {"Credit-Swipe"})
    public void testSWIPECCVoidOfMultiSale_45() {
        System.out.println("Test Case #45:");
        try{
        	Tests.testVoidOfMultiSale(swipeCC, CREDIT_SALE, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 46, groups = {"Credit-Swipe"})
    public void testSWIPECCVoidOfMultiRefund_46() {
        System.out.println("Test Case #46:");
        try{
        	Tests.testVoidOfMultiRefund(swipeCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 47, groups = {"Credit-Swipe"})
    public void testSWIPECCVoidOfMultiRefundedSale_47() {
        System.out.println("Test Case #47:");
        try{
        	Tests.testVoidOfMultiRefundedSale(swipeCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    // ***************************SWIPE DEBIT***********************
    // *************************************************************

    @Test(priority = 48, groups = {"Debit-Swipe"})
    public void testSWIPEDBSale_48() {
        System.out.println("Test Case #48:");
        try{
            TestLogger.log("1 - SALE: ");
        	Tests.testSale(swipeDB, DEBIT_SALE);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 49, groups = {"Debit-Swipe"})
    public void testSWIPEDBSaleRefund_49() {
        System.out.println("Test Case #49:");
        try{
        	Tests.testSaleRefund(swipeDB, DEBIT_SALE, DEBIT_REFUND);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 50, groups = {"Debit-Swipe"})
    public void testSWIPEDBSaleVoid_50() {
        System.out.println("Test Case #50:");
        try{
        	Tests.testSaleVoid(swipeDB, DEBIT_SALE, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 51, groups = {"Debit-Swipe"})
    public void testSWIPEDBSaleRefundVoid_51() {
        System.out.println("Test Case #51:");
        try{
        	Tests.testSaleRefundVoid(swipeDB, DEBIT_SALE, DEBIT_REFUND, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 52, groups = {"Debit-Swipe"})
    public void testSWIPEDBSaleRefundVoidVoidOfSale_52() {
        System.out.println("Test Case #52:");
        try{
        	Tests.testSaleRefundVoidVoidOfSale(swipeDB, DEBIT_SALE, DEBIT_REFUND, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 53, groups = {"Debit-Swipe"})
    public void testSWIPEDBSaleVoidVoid_53() {
        System.out.println("Test Case #53:");
        try{
        	Tests.testSaleVoidVoid(swipeDB, DEBIT_SALE, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 54, groups = {"Debit-Swipe"})
    public void testSWIPEDBSaleVoidVoidOfSale_54() {
        System.out.println("Test Case #54:");
        try{
        	Tests.testSaleVoidVoidOfSale(swipeDB, DEBIT_SALE, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 55, groups = {"Debit-Swipe"})
    public void testSWIPEDBSaleReversal_55() {
        System.out.println("Test Case #55:");
        try{
        	Tests.testSaleReversal(swipeDB, DEBIT_SALE, DEBIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 56, groups = {"Debit-Swipe"})
    public void testSWIPEDBSaleRefundReversalOfRefund_56() {
        System.out.println("Test Case #56:");
        try{
        	Tests.testSaleRefundReversalOfRefund(swipeDB, DEBIT_SALE, DEBIT_REFUND, CREDIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 57, groups = {"Debit-Swipe"})
    public void testSWIPEDBSaleRefundVoidRefundOfVoid_57() {
        System.out.println("Test Case #57:");
        try{
        	Tests.testSaleVoidReversalOfVoid(swipeDB, DEBIT_SALE, DEBIT_VOID, CREDIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 58, groups = {"Debit-Swipe"})
    public void testSWIPEDBMultiSale_58() {
        System.out.println("Test Case #58:");
        try{
        	Tests.testMultiSale(swipeDB, DEBIT_SALE);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 59, groups = {"Debit-Swipe"})
    public void testSWIPEDBRefundOfMultiSale_59() {
        System.out.println("Test Case #59:");
        try{
        	Tests.testRefundOfMultiSale(swipeDB, DEBIT_SALE, DEBIT_REFUND);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 60, groups = {"Debit-Swipe"})
    public void testSWIPEDBVoidOfMultiSale_60() {
        System.out.println("Test Case #60:");
        try{
        	Tests.testVoidOfMultiSale(swipeDB, DEBIT_SALE, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 61, groups = {"Debit-Swipe"})
    public void testSWIPEDBVoidOfMultiRefund_61() {
        System.out.println("Test Case #61:");
        try{
        	Tests.testVoidOfMultiRefund(swipeDB, DEBIT_SALE, DEBIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 62, groups = {"Debit-Swipe"})
    public void testSWIPEDBVoidOfMultiRefundedSale_62() {
        System.out.println("Test Case #62:");
        try{
        	Tests.testVoidOfMultiRefundedSale(swipeDB, DEBIT_SALE, DEBIT_REFUND, DEBIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 63, groups = {"Debit-Swipe"})
    public void testSWIPEDBSaleTrack1and2_63() {
        System.out.println("Test Case #63:");
        try{
        	Tests.testSaleTrack1and2(swipeDB, DEBIT_SALE);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    // ***************************KEYED CREDIT*******************

    @Test(priority = 64, groups = {"Credit-Keyed"})
    public void testKEYEDCCSale_64() {
        System.out.println("Test Case #64:");
        try{
            TestLogger.log("1 - SALE: ");
        	Tests.testSale(keyedCC, CREDIT_SALE);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 65, groups = {"Credit-Keyed"})
    public void testKEYEDCCSaleRefund_65() {
        System.out.println("Test Case #65:");
        try{
        	Tests.testSaleRefund(keyedCC, CREDIT_SALE, CREDIT_REFUND);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 66, groups = {"Credit-Keyed"})
    public void testKEYEDCCSaleVoid_66() {
        System.out.println("Test Case #66:");
        try{
        	Tests.testSaleVoid(keyedCC, CREDIT_SALE, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 67, groups = {"Credit-Keyed"})
    public void testKEYEDCCSaleRefundVoid_67() {
        System.out.println("Test Case #67:");
        try{
        	Tests.testSaleRefundVoid(keyedCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 68, groups = {"Credit-Keyed"})
    public void testKEYEDCCSaleRefundVoidVoidOfSale_68() {
        System.out.println("Test Case #68:");
        try{
        	Tests.testSaleRefundVoidVoidOfSale(keyedCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 69, groups = {"Credit-Keyed"})
    public void testKEYEDCCSaleVoidVoid_69() {
        System.out.println("Test Case #69:");
        try{
        	Tests.testSaleVoidVoid(keyedCC, CREDIT_SALE, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 70, groups = {"Credit-Keyed"})
    public void testKEYEDCCSaleVoidVoidVoidOfSale_70() {
        System.out.println("Test Case #70:");
        try{
        	Tests.testSaleVoidVoidOfSale(keyedCC, CREDIT_SALE, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(priority = 71, groups = {"Credit-Keyed"})
    public void testKEYEDCCSaleReversal_71() {
        System.out.println("Test Case #71:");
        try{
        	Tests.testSaleReversal(keyedCC, CREDIT_SALE, CREDIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 72, groups = {"Credit-Keyed"})
    public void testKEYEDCCSaleRefundReversalOfRefund_72() {
        System.out.println("Test Case #72:");
        try{
        	Tests.testSaleRefundReversalOfRefund(keyedCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 73, groups = {"Credit-Keyed"})
    public void testKEYEDCCSaleVoidReversalOfVoid_73() {
        System.out.println("Test Case #73:");
        try{
        	Tests.testSaleVoidReversalOfVoid(keyedCC, CREDIT_SALE, CREDIT_VOID, CREDIT_REVERSAL);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 74, groups = {"Credit-Keyed"})
    public void testKEYEDCCVoidOfRefundedSale_74() {
        System.out.println("Test Case #74:");
        try{
        	Tests.testVoidOfRefundedSale(keyedCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }   
    
    @Test(priority = 75, groups = {"Credit-Keyed"})
    public void testKEYEDCCMultiSale_75() {
        System.out.println("Test Case #75:");
        try{
        	Tests.testMultiSale(keyedCC, CREDIT_SALE);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 76, groups = {"Credit-Keyed"})
    public void testKEYEDCCRefundOfMultiSale_76() {
        System.out.println("Test Case #76:");
        try{
        	Tests.testRefundOfMultiSale(keyedCC, CREDIT_SALE, CREDIT_REFUND);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 77, groups = {"Credit-Keyed"})
    public void testKEYEDCCVoidOfMultiSale_77() {
        System.out.println("Test Case #77:");
        try{
        	Tests.testVoidOfMultiSale(keyedCC, CREDIT_SALE, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 78, groups = {"Credit-Keyed"})
    public void testKEYEDCCVoidOfMultiRefund_78() {
        System.out.println("Test Case #78:");
        try{
        	Tests.testVoidOfMultiRefund(keyedCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }
    
    @Test(priority = 79, groups = {"Credit-Keyed"})
    public void testKEYEDCCVoidOfMultiRefundedSale_79() {
        System.out.println("Test Case #79:");
        try{
        	Tests.testVoidOfMultiRefundedSale(keyedCC, CREDIT_SALE, CREDIT_REFUND, CREDIT_VOID);
        }catch(Exception e){
        	Assert.fail();
            e.printStackTrace();
        }
    }

    @AfterTest
    public void closeTesting() {
        emvCC = null;
        swipeCC = null;
        keyedCC = null;
        emvDB = null;
        swipeDB = null;
        System.out.println("Testcase Execution Completed For Elavon Credit and Debit Processor");
    }

}
