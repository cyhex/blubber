var Helpers = {

    parseQuery: function (query) {
        var pairs = {};
        var data = query.split('&');
        for (var i = 0; i < data.length; i++) {
            var pair = data[i].split('=');
            pairs[pair[0]] = pair[1];
        }
        return pairs;
    }
}