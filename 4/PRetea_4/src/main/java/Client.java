import javax.management.MBeanServerConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9000;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        System.out.println("Type: tell me name to get a random name");
        ServerConnection serverConnect = new ServerConnection(socket);
        BufferedReader kyeboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        new Thread(serverConnect).start();
        while (true) {
            System.out.println("-> "); //cursor place
            String command = kyeboard.readLine();
            if (command.equals("quit")) {
                System.exit(0);
                break;
            }
            out.println(command);

        }
    }
}
