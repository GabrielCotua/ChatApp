/**
 * 
 */
package client;

import java.io.*;
import java.net.*;

public class Client {
    public  String username;
    public  String ipAddr;
    public  int serverPort;
    public Client(String usr, String ip, int port) {
        this.username = usr;
        this.ipAddr = ip;
        this.serverPort = port;
    }
    public void connect() {
        try {
            Socket socket = null;
            try {
                socket = new Socket(this.ipAddr, this.serverPort);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                out.println(this.username);

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String message;
                while ((message = reader.readLine()) != null) {
                    out.println(message);
                }
            } finally {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return this.username;
    }

    public static void main(String[] args) {
        Client client = new Client("michael", "localhost", 5000);
        client.connect();
    }

}