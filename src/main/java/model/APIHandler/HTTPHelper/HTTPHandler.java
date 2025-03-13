package model.APIHandler.HTTPHelper;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * A class that makes get requests
 */
public class HTTPHandler {
    public static HttpResponse<String> getRequest(String requestURL) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(requestURL))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            Post post = gson.fromJson(response.body(), Post.class);

            return response;

        } catch (IOException | InterruptedException e) {
            ;
        } catch (URISyntaxException ignored) {
            ;
        }
        return null;
    }
}
