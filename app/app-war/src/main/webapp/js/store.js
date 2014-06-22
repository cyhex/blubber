var Store = {

	// cache and compile template
	errorTemplate : $("#error"),

	// REST end point
	endpointUpc : '/api/upc',

	callbackUrl : "http://" + window.location.host
			+ "/html/store/index.html?upc={CODE}",

	init : function() {

		$("#newPaymentOrderForm").submit(function(e) {
			e.preventDefault();
			$('#qrcode').html("");
			$('#qrcode').qrcode($(this).serialize());
			$('#qrCodePanel').slideDown();

		});

		$("#scan").attr('href', Store.getScanUrl());
		Store.loadUpc();
	},

	loadUpc : function() {
		var query = Helpers.parseQuery(window.location.search.substring(1));
		if (!query.upc) {
			return;
		}

		$.getJSON(Store.endpointUpc, {
			upc : query.upc
		}, function(data) {
			var product = data[0];
			if (product) {
				$("#name").val(decodeURIComponent(product.productname));
				$("#img").attr('src', decodeURIComponent(product.imageurl));
				$("#imgurl").val(decodeURIComponent(product.imageurl));
				$("#img").show();
			}

			Store.errorTemplate.children('span').html(
					'productname: "' + product.productname + '"<br />'
							+ 'imageurl: "' + product.imageurl + '"<br />'
							+ 'decodeURIComponent(product.productname): "'
							+ decodeURIComponent(product.productname)
							+ '"<br />'
							+ 'decodeURIComponent(product.imageurl): "'
							+ decodeURIComponent(product.imageurl) + '"<br />');
			Store.errorTemplate.show();
		});

	},

	getScanUrl : function() {
		return "http://zxing.appspot.com/scan?ret="
				+ encodeURIComponent(Store.callbackUrl);
	}
};
