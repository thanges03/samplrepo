import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 12345;

        try (
            Socket socket = new Socket(hostname, port);
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            System.out.println("🟢 Connected to chat server at " + hostname + ":" + port);
            System.out.print("Enter your name: ");
            String name = inputReader.readLine();
            out.println("👤 " + name + " joined the chat.");

            // Thread to read incoming messages
            new Thread(() -> {
                String msgFromServer;
                try {
                    while ((msgFromServer = in.readLine()) != null) {
                        System.out.println(msgFromServer);
                    }
                } catch (IOException e) {
                    System.out.println("❌ Disconnected from server.");
                }
            }).start();

            // Main thread to send messages
            String msgToSend;
            while ((msgToSend = inputReader.readLine()) != null) {
                out.println("💬 " + name + ": " + msgToSend);
            }

        } catch (IOException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
