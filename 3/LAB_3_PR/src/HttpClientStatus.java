import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientStatus {

    public static void main(String[] args) throws IOException, InterruptedException {
        String ipAdddress = "51.158.172.165";
        int port = 8811;//proxy port
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://unite.md/"))
                .GET() // GET is default
                .build();
        HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
        System.out.println(response);
        if (response.statusCode() == 200) {
            System.out.println("\n\n GET request:");
            Get.sendGet(ipAdddress, port);

            System.out.println("\n\n HEAD request:");
            Head.sendHead(ipAdddress, port);

            System.out.println("\n\n OPTIONS request:");
            Options.sendOptions(ipAdddress, port);

            System.out.println("\n\n POST request:");
            Post.sendPost(ipAdddress, port);
        }

    }
}