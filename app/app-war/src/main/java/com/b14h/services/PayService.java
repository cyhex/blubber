package com.b14h.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.b14h.model.Parent;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.PayRequest;
import com.paypal.svcs.types.ap.Receiver;
import com.paypal.svcs.types.ap.ReceiverList;
import com.paypal.svcs.types.common.RequestEnvelope;

public class PayService {
	private static final String CURRENCY_CODE = "EUR";

	public static void pay(String receiverMail, double amountEur, String memo) {
		String preapprovalKey = Parent.getInstance().getPreapprovalKey();

		PayRequest payRequest = createPayRequest(preapprovalKey, receiverMail,
				amountEur, memo);

		AdaptivePaymentsService adaptivePaymentsService = PayPalUtils
				.getAdaptivePaymentsService();

		execute(payRequest, adaptivePaymentsService);
	}

	private static PayRequest createPayRequest(String preapprovalKey,
			String receiverMail, double amountEur, String memo) {
		RequestEnvelope env = new RequestEnvelope();
		env.setErrorLanguage("en_US");

		List<Receiver> receiver = new ArrayList<Receiver>();
		Receiver rec = new Receiver();
		rec.setAmount(amountEur);
		rec.setEmail(receiverMail);
		receiver.add(rec);
		ReceiverList receiverlst = new ReceiverList(receiver);

		PayRequest payRequest = new PayRequest();
		payRequest.setActionType("PAY");
		payRequest.setMemo(memo);
		payRequest.setReceiverList(receiverlst);
		payRequest.setCurrencyCode(CURRENCY_CODE);
		payRequest.setCancelUrl("http://www.notUsedButRequiredUrl.de/");
		payRequest.setReturnUrl("http://www.notUsedButRequiredUrl.de/");
		payRequest.setRequestEnvelope(env);
		payRequest.setPreapprovalKey(preapprovalKey);
		return payRequest;
	}

	private static void execute(PayRequest payRequest,
			AdaptivePaymentsService adaptivePaymentsService) {
		try {
			adaptivePaymentsService.pay(payRequest);
		} catch (SSLConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCredentialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidResponseDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientActionRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MissingCredentialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
