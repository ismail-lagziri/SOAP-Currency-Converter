package server;

import api.CurrencyConverterServiceImpl;
import jakarta.xml.ws.Endpoint;

public class CurrencyConverterServer {
    public static void main(String[] args) {
        String url = "http://localhost:8585/currencyConverter";
        Endpoint.publish(url, new CurrencyConverterServiceImpl());
        System.out.println("Web service deployed on " + url);
    }
}