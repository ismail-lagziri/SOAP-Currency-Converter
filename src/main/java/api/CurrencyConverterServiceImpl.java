package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import jakarta.jws.WebService;
import util.CurrencyConverterCache;
import util.Transaction;

@WebService(endpointInterface = "api.CurrencyConverterService")
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
    private CurrencyConverterCache cache;

    public CurrencyConverterServiceImpl() {
        this.cache = CurrencyConverterCache.getInstance();
    }

    public double convert(String fromCurrency, String toCurrency, double amount) {
        Double cachedRate = cache.getFromCache(fromCurrency + toCurrency);
        if (cachedRate != null) {
            System.out.println("Using cached exchange rate for " + fromCurrency + "/" + toCurrency);
            return amount * cachedRate;
        }

        String baseUrl = "https://api.exchangerate.host/latest";
        String urlParameters = "?base=" + fromCurrency + "&symbols=" + toCurrency;

        try {
            URL url = new URL(baseUrl + urlParameters);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            double rate = jsonObject.getJSONObject("rates").getDouble(toCurrency);

            cache.addToCache(fromCurrency + toCurrency, rate);

            Transaction transaction = new Transaction(LocalDateTime.now(), fromCurrency, toCurrency, amount, rate, amount * rate);
            System.out.println(transaction);

            return amount * rate;
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error connecting to API: " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return -1;
    }
}