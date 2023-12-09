import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUsersInfo {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users";

    public static void main(String[] args) {
        GetUsersInfo getUsersInfo = new GetUsersInfo();


        getUsersInfo.getAllUsersInfo();
    }


    private void getAllUsersInfo() {
        try {

            URL url = new URL(BASE_URL + USERS_ENDPOINT);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

                reader.close();

                JSONArray jsonArray = new JSONArray(content.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject userObject = jsonArray.getJSONObject(i);

                    System.out.println("User ID: " + userObject.getInt("id"));
                    System.out.println("Name: " + userObject.getString("name"));
                    System.out.println("Username: " + userObject.getString("username"));
                    System.out.println("Email: " + userObject.getString("email"));
                    System.out.println("--------------------------------------");
                }
            } else {
                System.out.println("Failed to retrieve data. Response Code: " + responseCode);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

