package it.polimi.ingsw.GC_23.Connection;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alessandro Tonali on 13/06/2017.
 */
public class SocketAccepter implements Runnable {
    private ServerSocket serverSocket;
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * SocketAccepter thread: accept the connection through serverSocket.accept and then create a new
     * userhandler and add it to a match. Also get its properties: cli/gui interface and username
     */
    @Override
    public void run() {
        try {
            this.serverSocket = ServerImpl.getServer().getServerSocket();
            Socket socket = serverSocket.accept();
            SocketAccepter socketAccepter = new SocketAccepter();
            ServerImpl.getExecutor().submit(socketAccepter);
            SocketHandler userHandler = new SocketHandler(socket);
            ServerImpl.getServer().addToMatch(userHandler);
            ServerImpl.getExecutor().submit(userHandler);
            userHandler.setGuiInterface(Boolean.parseBoolean(userHandler.messageFromUser()));
            userHandler.setCurrentUser(userHandler.messageFromUser());
            System.out.println("Client accepted: " + socket);

        } catch (RemoteException e) {
            logger.setLevel(Level.SEVERE);
            logger.severe(String.valueOf(e));
        } catch (IOException e) {
            logger.setLevel(Level.SEVERE);
            logger.severe(String.valueOf(e));
        }
    }
}
