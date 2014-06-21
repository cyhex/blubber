var Store = {

    // REST end point
    endpointUpc: '/api/upc',

    callbackUrl : "http://" + window.location.host +  "/html/store/index.html?upc={CODE}",

    init: function () {

        $("#newPaymentOrderForm").submit(function (e) {
            e.preventDefault();
            $('#qrcode').html("");
            $('#qrcode').qrcode($(this).serialize());
            $('#qrCodePanel').slideDown();

        });

        $("#scan").attr('href', Store.getScanUrl());
    },

    loadUpc : function(){
        Helpers.parseQuery(window.location.q)
    },

    getScanUrl: function(){
        return  "http://zxing.appspot.com/scan?ret=" + encodeURIComponent(Store.callbackUrl);
    }
};

