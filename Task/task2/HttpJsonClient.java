import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class HttpJsonClient {

    // Method to send HTTP GET request and return response as String
    public static String sendGET(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("HTTP GET Request Failed with Error code : " + status);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        con.disconnect();

        return content.toString();
    }

    // Method to parse and display JSON data
    public static void parseJSON(String jsonResponse) {
        // Example assumes JSON array (e.g., from https://jsonplaceholder.typicode.com/posts)
        JSONArray jsonArray = new JSONArray(jsonResponse);

        System.out.println("📦 Parsed JSON Data:");
        for (int i = 0; i < Math.min(5, jsonArray.length()); i++) { // Limit to first 5
            JSONObject obj = jsonArray.getJSONObject(i);
            int id = obj.getInt("id");
            String title = obj.getString("title");
            String body = obj.getString("body");

            System.out.println("ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("Body: " + body);
            System.out.println("------------------------");
        }
    }

    public static void main(String[] args) {
        String apiUrl = "https://jsonplaceholder.typicode.com/posts";

        try {
            String response = sendGET(apiUrl);
            parseJSON(response);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
