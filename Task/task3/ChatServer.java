import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        int port = 12345;
        System.out.println("🚀 Chat Server started on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("✅ New client connected: " + clientSocket);
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("❌ Server error: " + e.getMessage());
        }
    }

    // Thread class to handle each client
    static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) {
                out = new PrintWriter(socket.getOutputStream(), true);
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("📩 Message received: " + message);
                    broadcast(message);
                }
            } catch (IOException e) {
                System.out.println("⚠️ Client disconnected: " + socket);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {}
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
            }
        }

        // Send message to all clients
        private void broadcast(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}
