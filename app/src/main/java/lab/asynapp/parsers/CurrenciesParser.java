package lab.asynapp.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

public class CurrenciesParser {
    public static String getCurrencies(String jsonResponse) throws IOException {
        StringBuilder result = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject rates = jsonObject.getJSONObject("rates");

            for (Iterator<String> it = rates.keys(); it.hasNext(); ) {
                String currency = it.next();
                double rate = rates.getDouble(currency);
                result.append(currency).append(": ").append(rate).append("\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
