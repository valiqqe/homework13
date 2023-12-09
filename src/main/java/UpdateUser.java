import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.json.JSONObject;

import java.io.IOException;

public class UpdateUser {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users";

    public static void main(String[] args) {
        UpdateUser updateUser = new UpdateUser();

        updateUser.updateAndPrintUser();
    }

    private void updateAndPrintUser() {
        try {
            Document document = Jsoup.connect(BASE_URL + USERS_ENDPOINT).get();

            String userId = document.body().select("body > :first-child > :first-child").attr("id");

            if (!userId.isEmpty()) {
                JSONObject updatedUser = new JSONObject();
                updatedUser.put("id", Integer.parseInt(userId));
                updatedUser.put("name", "Updated User");
                updatedUser.put("username", "updateduser");
                updatedUser.put("email", "updateduser@example.com");

                Connection.Response response = Jsoup.connect(BASE_URL + USERS_ENDPOINT + "/" + userId)
                        .ignoreContentType(true)
                        .requestBody(updatedUser.toString())
                        .header("Content-Type", "application/json")
                        .method(Connection.Method.PUT)
                        .execute();

                if (response.statusCode() == 200) {
                    System.out.println("User updated successfully!");
                    System.out.println("Updated User JSON: " + updatedUser.toString());
                } else {
                    System.out.println("Error updating user. Status code: " + response.statusCode());
                }
            } else {
                System.out.println("No user found with the specified ID.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

