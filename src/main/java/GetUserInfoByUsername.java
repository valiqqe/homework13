import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUserInfoByUsername {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users";

    public static void main(String[] args) {
        GetUserInfoByUsername getUserInfoByUsername = new GetUserInfoByUsername();

        getUserInfoByUsername.getUserInfo("Bret");
    }

    private void getUserInfo(String username) {
        try {

            URL url = new URL(BASE_URL + USERS_ENDPOINT + "?username=" + username);

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


                if (jsonArray.length() > 0) {
                    JSONObject userObject = jsonArray.getJSONObject(0);

                    System.out.println("User ID: " + userObject.getInt("id"));
                    System.out.println("Name: " + userObject.getString("name"));
                    System.out.println("Username: " + userObject.getString("username"));
                    System.out.println("Email: " + userObject.getString("email"));
                } else {
                    System.out.println("User with username '" + username + "' not found.");
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
