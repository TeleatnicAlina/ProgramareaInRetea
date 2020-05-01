import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;
    public String clientName;

    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        this.client = clientSocket;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }

    public void run() {
        try {
            while (true) {
                String request = "say " + in.readLine();

                if (request.contains("quit")) {
                    System.out.println("{SERVER} Closing......");
                    in.close();
                    out.close();
                    break;
                } else if (request.contains("name")) {
                    this.clientName = Server.getRandomName();
                    out.println(this.clientName);
                } else if (request.startsWith("say")) {
                    int findSpace = request.indexOf(" ");
                    if (findSpace != -1) {
                        System.out.println(this.clientName + ":" + request.substring(findSpace + 1));
                        outToAll(request.substring(findSpace + 1), this.clientName);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IO exception in client handler");
        } finally {
            System.out.println("{SERVER} Closing.....");
            System.out.println(this.clientName+" leaved the chat");
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void outToAll(String stringMessage, String name) throws IOException {
        for (ClientHandler aClient : clients) {
            aClient.out.println(name + ": " + stringMessage);
        }
    }
}
