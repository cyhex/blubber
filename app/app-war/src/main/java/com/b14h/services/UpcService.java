package com.b14h.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UpcService {
    private static final String url = "http://www.searchupc.com/handlers/upcsearch" +
            ".ashx?request_type=3&access_token=21191A33-110E-45AF-8BF4-FCCCDCD39376&upc=__upc__";

    public static String search(String upc){

        try {
            return readUrl(url.replace("__upc__", upc));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String readUrl(String url) throws IOException {
        URL oracle = new URL(url);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String buffer = "";
        String inputLine;

        while ((inputLine = in.readLine()) != null){
            buffer += inputLine;
        }
        in.close();
        return buffer;
    }


}
