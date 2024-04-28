package lab.asynapp.utilities;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

import lab.asynapp.parsers.CurrenciesParser;

public class CurrenciesDataLoader extends AsyncTask<String, Void, String> {
    private static String downloadUrlContent(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();

        try {
            InputStream inputStream = conn.getInputStream();
            StringBuilder result = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } finally {
            conn.disconnect();
        }
    }

    protected String doInBackground(String... params) {
        try {
            String apiContentStream = null;
            String result = "";

            apiContentStream = downloadUrlContent("https://api.currencybeacon.com/v1/latest?api_key=5T9lTa23KVhUXYgmsT5BbIUkZ767wKvg&base=EUR");
            result = CurrenciesParser.getCurrencies(apiContentStream);

            return result;
        } catch (IOException ex) {
            return String.format("Some error occured => %s", ex.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
