import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class GetOpenTasks {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String TODOS_ENDPOINT = "/todos";

    public static void main(String[] args) {
        GetOpenTasks getOpenTasks = new GetOpenTasks();

        getOpenTasks.printOpenTasksForUser(1);
    }

    public void printOpenTasksForUser(int userId) {
        try {
            List<JSONObject> userTasks = getTasksForUser(userId);
            List<JSONObject> openTasks = userTasks.stream()
                    .filter(task -> !task.optBoolean("completed", true))
                    .collect(Collectors.toList());

            System.out.println("Open tasks for user " + userId + ":");
            openTasks.forEach(task -> System.out.println(task.getString("title")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<JSONObject> getTasksForUser(int userId) throws IOException {
        URL url = new URL(BASE_URL + TODOS_ENDPOINT + "?userId=" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (connection.getResponseCode() == 200) {
            String jsonResponse = readResponse(connection);
            JSONArray tasksArray = new JSONArray(jsonResponse);

            return tasksArray.toList().stream()
                    .map(JSONObject::new)
                    .collect(Collectors.toList());
        } else {
            throw new IOException("Failed to retrieve tasks. Status code: " + connection.getResponseCode());
        }
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();
        }
    }
}