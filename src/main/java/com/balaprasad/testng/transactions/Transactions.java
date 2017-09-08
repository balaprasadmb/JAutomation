package com.balaprasad.testng.transactions;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.balaprasad.constants.AurusISOFields;
import com.balaprasad.testng.ResponseValidator;
import com.balaprasad.testng.TestLogger;
import com.balaprasad.testng.TestNGConstant;
import com.balaprasad.testng.TestNGUtility;
import com.balaprasad.testng.ResponseFactory;

public class Transactions extends TestNGConstant{

	private static JSONObject requestPayload;
	private static boolean isIVR;
	private static boolean isTicketNull = false;

	public static JSONObject transaction(JSONObject request, String transactionType, int logLevel) throws JSONException, Exception {
		JSONObject payload = new JSONObject(request.toString());
		payload.put("4.1", transactionType);
		if(transactionType != CREDIT_REVERSAL && transactionType != DEBIT_REVERSAL && transactionType != GET_CARD_BIN){
			payload.put("4.2", TestNGUtility.getSequenceNumber());
			requestPayload = payload;
		}
        JSONObject response = TestNGUtility.sendRequest(TestNGUtility.buildJSONRequest(payload));
        Assert.assertTrue(response.has(AurusISOFields.AURUSPAY_TRANSACTION_ID), "Expected TRANSACTION ID Field in Response ");
        TestLogger.log("Transaction id: " + response.getString(AurusISOFields.AURUSPAY_TRANSACTION_ID), logLevel + 1);
        Assert.assertTrue(response.has(AurusISOFields.UNIQUE_REQUEST_ID), "Expected UNIQUE REQUEST ID Field in Response ");
        TestLogger.log("Request id: " + response.getString(AurusISOFields.UNIQUE_REQUEST_ID), logLevel + 1);
        Assert.assertTrue(response.has(AurusISOFields.RESPONSE_CODE), "Expected RESPONSE CODE Field in Response ");
        TestLogger.log("Response Code: " + response.getString(AurusISOFields.RESPONSE_CODE), logLevel + 1);
        String processor_response = null;
        if(response.has(AurusISOFields.PROCESSOR_RESPONSE_CODE) && !response.getString(AurusISOFields.PROCESSOR_RESPONSE_CODE).isEmpty())
        	processor_response = response.getString(AurusISOFields.PROCESSOR_RESPONSE_CODE);
        TestLogger.log("Processor Response Code: " + processor_response, logLevel + 1);
        Assert.assertTrue(response.has(AurusISOFields.AUTHORIZATION_RESPONSE_TEXT), "Expected AUTHORIZATION RESPONSE TEXT Field in Response ");
        TestLogger.log("Response Text: " + response.getString(AurusISOFields.AUTHORIZATION_RESPONSE_TEXT), logLevel + 1);

        
        return response;
	}

	

	private static void doAssert(JSONObject response, String requestedAmount) throws JSONException, Exception {
		hasKey(response);
		if(response.getString(AurusISOFields.RESPONSE_CODE).equalsIgnoreCase("006")){
			Assert.assertTrue(response.has(AurusISOFields.PROCESSOR_RESPONSE_CODE), "Processor Response Code ");
			Assert.assertTrue(ResponseFactory.validateResponse(response.getString(AurusISOFields.PROCESSOR_RESPONSE_CODE)), "Processor Approval ");
			if(response.getString(AurusISOFields.APPROVED_AMOUNT).equalsIgnoreCase("0.00"))
				Assert.assertEquals(response.getString(AurusISOFields.APPROVED_AMOUNT), "0.00", "Approved Amount ");
			else
				Assert.assertEquals(response.getString(AurusISOFields.APPROVED_AMOUNT), requestedAmount, "Approved Amount ");
		}
		else{
		Assert.assertEquals(response.getString(AurusISOFields.RESPONSE_CODE), "000", "Response Code ");
		Assert.assertEquals(response.getString(AurusISOFields.AUTHORIZATION_RESPONSE_TEXT).trim(), "APPROVAL", "Authorization Response Text ");
		Assert.assertEquals(response.getString(AurusISOFields.APPROVED_AMOUNT), requestedAmount, "Approved Amount ");
        Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.TICKET_NUMBER)), "Ticket Number is null ");
		}
        assertBlank(response);
	}

	private static void assertBlank(JSONObject response) throws JSONException, Exception {
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.UNIQUE_REQUEST_ID)), "Unique Request Id is null ");
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.AURUSPAY_TRANSACTION_ID)), "Transaction Id is null ");
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.TERMINAL_ID)), "Terminal Id is null ");
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.CONFIG_SIGNUP_FLAG)), "Config Signup Flag is null ");
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.AUTO_DOWNLOAD_FLAG)), "Auto Download Flag is null ");
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.BATCH_NUMBER)), "Batch Number is null ");
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.TRANSACTION_SEQUENCE_NUMBER)), "Transaction Sequence Number is null ");
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.CARD_TYPE)), "Card Type is null ");
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.TRANSACTION_DATE)), "Transaction Date is null ");
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.TRANSACTION_TIME)), "Transaction Time is null ");
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.PARTIAL_TENDER_FLAG)), "Partial Tender Flag is null ");
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.PARTIAL_CARD)), "Partial Card is null ");
		Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.HSM_FLAG)), "HSM Flag is null ");
		if(!isIVR)
			Assert.assertTrue(StringUtils.isNotBlank(response.getString(AurusISOFields.PROCESSOR_ID)), "Processor Id is null ");
		ResponseValidator.validateResponse(response, isTicketNull);
	}

	private static void hasKey(JSONObject response) throws Exception {
        Assert.assertTrue(response.has(AurusISOFields.TERMINAL_ID), "Expected TERMINAL ID Field in Response ");
        Assert.assertTrue(response.has(AurusISOFields.CONFIG_SIGNUP_FLAG), "Expected CONFIG SIGNUP FLAG Field in Response ");
        Assert.assertTrue(response.has(AurusISOFields.AUTO_DOWNLOAD_FLAG), "Expected AUTO DOWNLOAD FLAG Field in Response ");
        Assert.assertTrue(response.has(AurusISOFields.BATCH_NUMBER), "Expected BATCH NUMBER Field in Response ");
        Assert.assertTrue(response.has(AurusISOFields.TRANSACTION_SEQUENCE_NUMBER), "Expected TRANSACTION SEQUENCE NUMBER Field in Response ");
        Assert.assertTrue(response.has(AurusISOFields.CARD_TYPE), "Expected CARD TYPE Field in Response ");
        Assert.assertTrue(response.has(AurusISOFields.TRANSACTION_DATE), "Expected TRANSACTION DATE Field in Response ");
        Assert.assertTrue(response.has(AurusISOFields.TRANSACTION_TIME), "Expected TRANSACTION TIME Field in Response ");
        Assert.assertTrue(response.has(AurusISOFields.PARTIAL_TENDER_FLAG), "Expected PARTIAL TENDER FLAG Field in Response ");
        Assert.assertTrue(response.has(AurusISOFields.TICKET_NUMBER), "Expected TICKET NUMBER Field in Response ");
        Assert.assertTrue(response.has(AurusISOFields.PARTIAL_CARD), "Expected PARTIAL CARD Field in Response ");
        Assert.assertTrue(response.has(AurusISOFields.HSM_FLAG), "Expected HSM FLAG Field in Response ");
        if(!isIVR)
        	Assert.assertTrue(response.has(AurusISOFields.PROCESSOR_ID), "Expected PROCESSOR ID Field in Response ");
	}

	public static JSONObject applePaySale(JSONObject request, String saleType, int logLevel) throws JSONException, Exception {
		JSONObject saleResponse = transaction(request, saleType, logLevel+1);
        Assert.assertEquals(saleResponse.getString(AurusISOFields.RESPONSE_CODE), "230", "Response Code ");
		Assert.assertEquals(saleResponse.getString(AurusISOFields.AUTHORIZATION_RESPONSE_TEXT).trim(), "TRANS NOT SUPPORTED", "Authorization Response Text ");
        return saleResponse;
	}

	

}
