package it.polimi.ingsw.GC_23.Connection;

import it.polimi.ingsw.GC_23.Board;
import it.polimi.ingsw.GC_23.Creator;
import it.polimi.ingsw.GC_23.Player;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jesss on 06/06/17.
 */
public class Match implements Runnable{
    private ArrayList<UserHandler> userHandlers;
    private PlayerController playerController;
    private int playerCounter;
    private Creator creator;
    private Board board;
    private boolean startMatch = false;
    private int votation;
    private boolean isAdvanced;
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public Match() {
        this.userHandlers = new ArrayList<>();
        this.playerController = new PlayerController();
        this.playerCounter = 0;
    }

    /**
     * start match thread: create the board and start setting
     */
    @Override
    public void run(){

        System.out.println("Match runned");
        while(!startMatch) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.setLevel(Level.SEVERE);
                logger.severe(String.valueOf(e));
                Thread.currentThread().interrupt();
            }
        }
        try {
            System.out.println("Match started");
            creator = new Creator(playerCounter);
            board = creator.getBoard();
            setting();
        } catch (Exception e) {
            logger.setLevel(Level.SEVERE);
            logger.severe(String.valueOf(e));
        }
    }

    /**
     * Life cycle of the match:
     * starts the setup of the user (username, type of connection and type of the interface)
     * starts the cli or the gui according to the choice made
     * at the end of the match close the socket or the rmi connection of all users
     * @throws RemoteException
     * @throws IOException
     */
    private void setting() throws IOException, RemoteException{
        for(UserHandler u: userHandlers){
            if(!u.isGuiInterface()) {
                u.messageToUser("MATCH STARTED");
                u.messageToUser("Wait for your turn");
            }
        }
        for(UserHandler u : userHandlers){
            if(!u.isGuiInterface()) {
                setup(playerController, u);
            }
            else{
                guiSetup(u);
            }
            playerController.getAssociation().putIfAbsent(u.getCurrentPlayer(), u.getCurrentUser());
            System.out.println("Setup di " + u.getCurrentUser() + " eseguito");
            creator.createPlayer(u.getCurrentPlayer().getPlayerColor(), u);
        }
        setAdvanced();
        creator.startGame(userHandlers.size(), this.isAdvanced);
        for(UserHandler u: userHandlers){

            u.messageToUser("quit");
            u.setEndMatch(true);
        }
        System.out.println("Match ended");
    }

    /**
     * Add the userhandler in the list of userhandlers and update the counter
     * @param userHandler to add
     * @throws RemoteException
     */
    public void setUserHandler(UserHandler userHandler) throws RemoteException{
        this.userHandlers.add(userHandler);
        playerCounter++;
    }

    public void setStartMatch(boolean value) throws RemoteException{
        this.startMatch = value;
    }


    public boolean isStartMatch() {
        return startMatch;
    }

    public int getPlayerCounter() throws RemoteException{
        return this.playerCounter;
    }

    /**
     * Cli setup of the user
     * It is possibile to choose to play with basic or advanced rules (in the match the majority of votes will be considered)
     * and the player color
     * @param playerController: the map of the current association of players and colors
     * @param userHandler: the userhandler of the user to set
     * @throws RemoteException
     * @throws IOException
     */
    public void setup(PlayerController playerController, UserHandler userHandler) throws IOException, RemoteException {
        StringBuilder stringBuilder = new StringBuilder();
        Map<Player, String> association = playerController.getAssociation();
        while(true) {
            int z = -1;
            if(!userHandler.isGuiInterface()) {
                userHandler.messageToUser("IF YOU WOULD LIKE TO PLAY WITH ADVANCED RULES PRESS 0\n" +
                        "IF YOU WOULD LIKE TO PLAY WITH BASIC RULES PRESS 1\n" +
                        "MAJORITY WILL DECIDE");
                userHandler.messageToUser("write");
            }
                String in = userHandler.messageFromUser();

            try {
                z = Integer.parseInt(in);
            } catch (NumberFormatException e) {
                userHandler.messageToUser("invalid input");
                continue;

            }
            switch (z){
                case 0:
                    votation++;
                    break;
                case 1:
                    votation--;
                    break;

                default:
                    continue;
            }
            break;
        }
        userHandler.messageToUser(String.valueOf(stringBuilder.append(association.size())));
        //mostra le associazioni presenti
        for (Map.Entry<Player, String> entry : association.entrySet()) {
            userHandler.messageToUser(entry.getKey().getPlayerColor().toString() + "\t\t\t" + entry.getValue());
        }
        boolean logged = false;
        userHandler.messageToUser("Select your color");
        //controlla che la stringa data corrisponda ad un player e che questo non sia già associato
        try{
            while (!logged) {
                userHandler.messageToUser("write");
                String choice = userHandler.messageFromUser();
                userHandler.messageToUser("You have chosen: "+choice);
                Player selectedPlayer = new Player(null,null);
                for (Map.Entry<Player, String> entry : association.entrySet()) {
                    if (entry.getKey().getPlayerColor().toString().equalsIgnoreCase((choice))) {
                        selectedPlayer = entry.getKey();
                    }
                }
                if ((selectedPlayer.getPlayerColor() == null) || (association.putIfAbsent(selectedPlayer, userHandler.getCurrentUser()) != null)) {
                    userHandler.messageToUser("Player già associato o nullo");
                    userHandler.messageToUser("Retry");
                    continue;
                }
                if (association.get(selectedPlayer) == userHandler.getCurrentUser()) {
                    userHandler.setCurrentPlayer(selectedPlayer);
                    userHandler.messageToUser("Player chosen correctly");
                    userHandler.messageToUser("Wait for your turn");
                    userHandler.messageToUser("wait");
                    logged = true;
                }
            }
        } catch (Exception e) {
            logger.setLevel(Level.SEVERE);
            logger.severe(String.valueOf(e));
        }
    }

    /**
     * Gui setup of the player (player color + basic/advanced rules)
     * @param userHandler userhandler of the user to set
     * @throws RemoteException
     */
    public void guiSetup(UserHandler userHandler) throws RemoteException{
        userHandler.messageToUser("start");
        for (Map.Entry<Player, String> entry : playerController.getAssociation().entrySet()) {
            try {
                userHandler.messageToUser(entry.getKey().getPlayerColor().toString());
                userHandler.messageToUser(entry.getValue());
            }catch(NullPointerException e){
                userHandler.messageToUser("null");
            }
        }
        String choice = userHandler.messageFromUser();
        Player selectedPlayer = new Player(null, null);
        for (Map.Entry<Player, String> entry : playerController.getAssociation().entrySet()) {
            if (entry.getKey().getPlayerColor().toString().equalsIgnoreCase((choice))) {
                selectedPlayer = entry.getKey();
            }
        }
        userHandler.setCurrentPlayer(selectedPlayer);
        this.votation = this.votation + Integer.parseInt(userHandler.messageFromUser());
    }

    public boolean isAdvanced() {
        return isAdvanced;
    }

    public void setAdvanced() {
        isAdvanced = votation>0;
    }
}



