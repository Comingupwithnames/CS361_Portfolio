import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class QOD_Microservice {

    /*
     *
     *
     *
     *
     *
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br;

        String key ="YOUR KEY HERE"; //Insert your own API key for this to function
        String lineToRead;
        StringBuffer responseContent = new StringBuffer();
        String quote = null;

        JSONObject outputJSON = null;

        Boolean debugFlag = true;

        try
        {
            URL quoteURL = new URL("https://api.api-ninjas.com/v1/quotes?category=happiness");
            /* Initialize the connection and set it to a get request */
            HttpURLConnection connection = (HttpURLConnection) quoteURL.openConnection();
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("X-Api-Key", key);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println("Status: " + status);

            if(status > 299)
            {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            else
            {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            while((lineToRead = br.readLine()) != null)
            {
                responseContent.append(lineToRead);
            }
            br.close();
            if(debugFlag) { System.out.println(responseContent.toString()); }
            quote = parseJSON(responseContent.toString());
            System.out.println(quote);
            connection.disconnect();

            outputJSON = new JSONObject();
            outputJSON.put("QuoteOfTheDay", quote);
            String currDir = System.getProperty("user.dir");

            FileWriter file = new FileWriter(currDir + "/quote.json", false);
            file.write(outputJSON.toString());
            file.close();
        }
        catch (Exception e)
        {
            System.out.println("Some error occurred");
            System.out.println(e.toString());
        }

    }

    public static String parseJSON(String responseBody)
    {
        JSONArray quoteArr = new JSONArray(responseBody);
        JSONObject quoteObj = quoteArr.getJSONObject(0);
        String QOD = quoteObj.getString("quote");
        return QOD;
    }
}
