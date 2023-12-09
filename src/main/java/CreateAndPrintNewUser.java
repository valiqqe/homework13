import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.json.JSONObject;

import java.io.IOException;

public class CreateAndPrintNewUser {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users";

    public static void main(String[] args) {
        CreateAndPrintNewUser createUser = new CreateAndPrintNewUser();

        createUser.createAndPrintNewUser();
    }

    private void createAndPrintNewUser() {
        try {
            JSONObject newUser = new JSONObject();
            newUser.put("name", "New User");
            newUser.put("username", "newuser");
            newUser.put("email", "newuser@example.com");

            Connection.Response response = Jsoup.connect(BASE_URL + USERS_ENDPOINT)
                    .ignoreContentType(true)
                    .requestBody(newUser.toString())
                    .header("Content-Type", "application/json")
                    .method(Connection.Method.POST)
                    .execute();

            if (response.statusCode() == 201) {
                System.out.println("New user created successfully!");
                JSONObject createdUser = new JSONObject(response.body());
                System.out.println("New User ID: " + createdUser.getInt("id"));
            } else {
                System.out.println("Error creating new user. Status code: " + response.statusCode());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
