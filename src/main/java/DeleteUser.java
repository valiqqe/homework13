import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DeleteUser {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users";

    public static void main(String[] args) {
        DeleteUser deleteUser = new DeleteUser();

        deleteUser.deleteUserById(1);
    }

    private void deleteUserById(int userIdToDelete) {
        try {
            Connection.Response response = Jsoup.connect(BASE_URL + USERS_ENDPOINT + "/" + userIdToDelete)
                    .ignoreContentType(true)
                    .method(Connection.Method.DELETE)
                    .execute();

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                System.out.println("User with ID " + userIdToDelete + " deleted successfully!");
            } else {
                System.out.println("Error deleting user with ID " + userIdToDelete + ". Status code: " + response.statusCode());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
