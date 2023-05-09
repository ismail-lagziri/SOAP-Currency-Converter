package api;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface CurrencyConverterService {
    @WebMethod
    double convert(
	    @WebParam(name = "fromCurrency") String fromCurrency,
	    @WebParam(name = "toCurrency") String toCurrency, 
	    @WebParam(name = "amount") double amount);
}