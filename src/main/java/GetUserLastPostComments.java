import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetUserLastPostComments {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users";
    private static final String POSTS_ENDPOINT = "/posts";
    private static final String COMMENTS_ENDPOINT = "/comments";

    public static void main(String[] args) {
        GetUserLastPostComments userLastPostComments = new GetUserLastPostComments();

        userLastPostComments.getAndSaveLastPostComments(1); // Замініть 1 на id користувача, для якого ви хочете отримати коментарі
    }

    private void getAndSaveLastPostComments(int userId) {
        try {

            JSONObject lastPost = getLatestPost(userId);

            if (lastPost != null) {

                JSONArray comments = getPostComments(lastPost.getInt("id"));

                saveCommentsToFile(userId, lastPost.getInt("id"), comments);
            } else {
                System.out.println("Користувач не має постів.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getLatestPost(int userId) throws IOException {
        URL url = new URL(BASE_URL + USERS_ENDPOINT + "/" + userId + POSTS_ENDPOINT);
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
            JSONArray posts = new JSONArray(content.toString());

            if (posts.length() > 0) {
                return posts.getJSONObject(posts.length() - 1);
            }
        }

        return null;
    }

    private JSONArray getPostComments(int postId) throws IOException {
        URL url = new URL(BASE_URL + POSTS_ENDPOINT + "/" + postId + COMMENTS_ENDPOINT);
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
            return new JSONArray(content.toString());
        }

        return null;
    }

    private void saveCommentsToFile(int userId, int postId, JSONArray comments) {
        String fileName = "user-" + userId + "-post-" + postId + "-comments.json";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(comments.toString(2));
            System.out.println("Сomments were saved to a file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
