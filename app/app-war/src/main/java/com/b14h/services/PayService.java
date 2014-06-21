package com.b14h.services;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
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
	private static final String STATIC_STORE_PAYPAL_ID = "teambh.store@gmx.de";
	private static final String CURRENCY_CODE = "EUR";
	private static final DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public static void pay() {
		String preapprovalKey = fetchPreapprovalKey();

		PayRequest payRequest = createPayRequest(preapprovalKey);

		AdaptivePaymentsService adaptivePaymentsService = PayPalUtils
				.getAdaptivePaymentsService();

		execute(payRequest, adaptivePaymentsService);
	}

	private static PayRequest createPayRequest(String preapprovalKey) {
		RequestEnvelope env = new RequestEnvelope();
		env.setErrorLanguage("en_US");

		List<Receiver> receiver = new ArrayList<Receiver>();
		Receiver rec = new Receiver();
		rec.setAmount(2.0);
		rec.setEmail(STATIC_STORE_PAYPAL_ID);
		receiver.add(rec);
		ReceiverList receiverlst = new ReceiverList(receiver);
		
		PayRequest payRequest = new PayRequest();
		payRequest.setActionType("PAY");
		payRequest.setReceiverList(receiverlst);
		payRequest.setCurrencyCode(CURRENCY_CODE);
		payRequest.setCancelUrl("http://www.notUsedButRequiredUrl.de/");
		payRequest.setReturnUrl("http://www.notUsedButRequiredUrl.de/");
		payRequest.setRequestEnvelope(env);
		payRequest.setPreapprovalKey(preapprovalKey);
		return payRequest;
	}

	/**
	 * TODO: this is a workaround implementation to store/load the preapproval
	 * key in our datastore. a better store architecture is required.
	 * 
	 * @return the preapproval key fetched from our datastore.
	 */
	private static String fetchPreapprovalKey() {
		Query q = new Query("preapprovalKey");
		List<Entity> preapprovalKeys = datastore.prepare(q).asList(
				withLimit(50));
		Entity entity = preapprovalKeys.get(0);
		String preapprovalKey = (String) entity.getProperty("key");
		return preapprovalKey;
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
