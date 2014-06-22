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
        Store.loadUpc();
    },

    loadUpc : function(){
        var query = Helpers.parseQuery(window.location.search.substring(1));
        if(!query.upc){
            return;
        }

        $.ajax({
            dataType: "json",
            url: Store.endpointUpc,
            data: {upc:query.upc},
            success: function(data){
                var product = data[0];
                if(product && product.productname.trim()){
                    $("#name").val(decodeURIComponent(product.productname));
                    $("#img").attr('src',decodeURIComponent(product.imageurl));
                    $("#imgurl").val(decodeURIComponent(product.imageurl));
                    $("#img").show();
                }else{
                    Store.productNotFound();
                }
            },
            error: Store.productNotFound
        });


    },

    productNotFound: function(){
        $("#error").html("Product not found").show();
    },

    getScanUrl: function(){
        return  "http://zxing.appspot.com/scan?ret=" + encodeURIComponent(Store.callbackUrl);
    }
};

