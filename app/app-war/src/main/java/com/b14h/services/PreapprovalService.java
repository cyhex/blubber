package com.b14h.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.PreapprovalRequest;
import com.paypal.svcs.types.ap.PreapprovalResponse;
import com.paypal.svcs.types.common.RequestEnvelope;

public class PreapprovalService {

	private static final String ERROR_LANGUAGE = "en_US";
	private static final String CURRENCY_CODE = "EUR";
	private static final String REDIRECT_URL_SUCCEEDED_PREAPPROVAL = "http://localhost:8080/html/parent/index.html?preapprovalSuccessful=true";
	private static final String REDIRECT_URL_CANCELED_PREAPPROVAL = "http://localhost:8080/html/parent/index.html";
	private static final DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public static String preapprove() {
		AdaptivePaymentsService adaptivePaymentsService = PayPalUtils
				.getAdaptivePaymentsService();

		PreapprovalRequest preapprovalRequest = createPreapprovalRequest();

		String preapprovalKey = execute(adaptivePaymentsService,
				preapprovalRequest);

		store(preapprovalKey);
		return preapprovalKey;
	}

	/**
	 * TODO: this is a workaround implementation to store/load the preapproval
	 * key in our datastore. a better store architecture is required.
	 */
	private static void store(String preapprovalKey) {
		// store the preapproval key
		// TODO: temp solution until data models are implemented:
		Entity keyEntity = new Entity("preapprovalKey");
		keyEntity.setProperty("key", preapprovalKey);
		datastore.put(keyEntity);
	}

	private static String execute(
			AdaptivePaymentsService adaptivePaymentsService,
			PreapprovalRequest preapprovalRequest) {
		PreapprovalResponse preapprovalResponse = null;
		try {
			preapprovalResponse = adaptivePaymentsService
					.preapproval(preapprovalRequest);
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

		return preapprovalResponse.getPreapprovalKey();
	}

	private static PreapprovalRequest createPreapprovalRequest() {
		RequestEnvelope requestEnvelope = new RequestEnvelope(ERROR_LANGUAGE);

		Date now = new Date();
		SimpleDateFormat sdfDestination = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");
		String startingDate = sdfDestination.format(now);

		PreapprovalRequest preapprovalRequest = new PreapprovalRequest(
				requestEnvelope, REDIRECT_URL_CANCELED_PREAPPROVAL,
				CURRENCY_CODE, REDIRECT_URL_SUCCEEDED_PREAPPROVAL, startingDate);
		return preapprovalRequest;
	}
}
