import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class Get {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String URL = "http://unite.md/";

    public static void sendGet(String hostname, int port) throws IOException {
        URL obj = new URL(URL);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostname, port));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection(proxy);
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        }
    }
}