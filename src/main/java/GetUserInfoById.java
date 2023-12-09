import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUserInfoById {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users";

    public static void main(String[] args) {
        GetUserInfoById getUserInfoById = new GetUserInfoById();

        getUserInfoById.getUserInfo(3);
    }

    private void getUserInfo(int userId) {
        try {
            URL url = new URL(BASE_URL + USERS_ENDPOINT + "/" + userId);

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

                JSONObject userObject = new JSONObject(content.toString());

                System.out.println("User ID: " + userObject.getInt("id"));
                System.out.println("Name: " + userObject.getString("name"));
                System.out.println("Username: " + userObject.getString("username"));
                System.out.println("Email: " + userObject.getString("email"));

            } else {
                System.out.println("Failed to retrieve data. Response Code: " + responseCode);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

