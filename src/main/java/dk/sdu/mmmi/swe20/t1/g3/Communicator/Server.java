package dk.sdu.mmmi.swe20.t1.g3.Communicator;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.ItemController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    // Singleton Pattern Declaration
    public static Server instance = null;
    public static Server getInstance() {
        if (instance == null) instance = new Server();
        return instance;
    }
    // End Singleton Pattern Declaration

    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream in = null;
    private PrintWriter out = null;

    private final int PORT = 4444;

    private Server() { }
    /*
    Communication Prefixes:

    # - Utils Request - mostly used by the Communicator.
    ! - Command Request - used for navigating.
    / - Data Request - used for transmitting data.
    &[No]: - Response - Used for declaring response
     */

    public void connect() {
        try {
            serverSocket = new ServerSocket(PORT);
            socket = serverSocket.accept();

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            socket.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataInputStream getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }
}
