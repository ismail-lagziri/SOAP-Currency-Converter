package server;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverterServerTest {


    @Test
    public void testWebServiceIsRunning() {
        try {

            URL url = new URL("http://localhost:8585/currencyConverter?wsdl");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode); 
        } catch (Exception e) {
 
            e.printStackTrace();
            org.junit.Assert.fail("Exception occurred while testing the web service.");
        }
    }
}
