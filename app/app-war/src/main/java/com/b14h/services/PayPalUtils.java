package com.b14h.services;

import java.util.HashMap;
import java.util.Map;

import com.paypal.svcs.services.AdaptivePaymentsService;

class PayPalUtils {

	/**
	 * Creates and returns a fully configured service object for paypals
	 * adaptive payments.
	 * 
	 * @return a fully configured service object for paypals adaptive payments.
	 */
	static AdaptivePaymentsService getAdaptivePaymentsService() {
		Map<String, String> customConfigurationMap = new HashMap<String, String>();
		customConfigurationMap.put("mode", "sandbox"); // Load the map with all
														// mandatory parameters
		customConfigurationMap.put("acct1.UserName", "teambh_api1.gmx.de");
		customConfigurationMap.put("acct1.Password", "1402429072");
		customConfigurationMap.put("acct1.Signature",
				"AFcWxV21C7fd0v3bYYYRCpSSRl31AUsTqNigADQwIjFRV.1kjkOVHgdp");
		// The Sandbox uses a global test App ID value that remains constant
		customConfigurationMap.put("acct1.AppId", "APP-80W284485P519543T");

		AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(
				customConfigurationMap);

		return adaptivePaymentsService;
	}
}
