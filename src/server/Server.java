package server;

import java.io.*;
import java.net.*;

public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                // Get client information
                InetAddress clientAddress = socket.getInetAddress();
                int clientPort = socket.getPort();
                System.out.println("Client IP: " + clientAddress.getHostAddress());
                System.out.println("Client Port: " + clientPort);

                // Read username as the first message
                this.username = in.readLine();
                System.out.println("Client Username: " + this.username);

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println( "["+ this.username + "] " + ": " + message);
                    out.println("Echo: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
        server.start();
    }
}