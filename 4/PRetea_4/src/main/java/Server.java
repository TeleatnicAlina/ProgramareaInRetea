import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static int i = 0;
    static int numberOfThreads = 4;
    public static ClientHandler clientThread = null;
    private static String[] names = {"Wily", "Felix", "Bob","Margaret", "Stay", "Hannah" };

    private static final int PORT = 9000;

    private static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    private static ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(PORT);

        while (i < numberOfThreads) {
            System.out.println("{SERVER} waiting for client connection ...");
            Socket client = listener.accept();
            System.out.println("{SERVER} connected to the client");
            clientThread = new ClientHandler(client, clients);
            clients.add(clientThread);
            pool.execute(clientThread);
            i++;
        }
    }

    public static String getRandomName() {
        String name = names[(int) (Math.random() * names.length)];
        return name ;
    }
}
