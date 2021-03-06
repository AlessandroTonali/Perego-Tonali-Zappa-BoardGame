package it.polimi.ingsw.GC_23.FX;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jesss on 19/06/17.
 */
public class Gameboard implements Serializable {
    private Stage primaryStage;
    private UserFX userFX;
    private String color;
    private transient final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private transient MessageListener messageListener;
    private transient ExecutorService executorService;

    public Gameboard(Stage primaryStage, UserFX userFX, String color) {
        this.primaryStage = primaryStage;
        this.userFX = userFX;
        this.color = color;

    }

    /**
     * When the server sends "update" the gameboard will be updated
     */
    public void running(GameboardController gameboardController) throws RemoteException {
        String actualString = userFX.receive();
        while (true){
            if("update".equals(actualString)){
                updateController(gameboardController);

            }
        }


    }

    public UserFX getUserFX() {
        return userFX;
    }

    public MessageListener getMessageListener() {
        return messageListener;
    }


    /**
     * Starts the page "Gameboard" through which the user will play
     */
    public void startGameBoard(Stage primaryStage) throws RemoteException{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Lorenzo Il Magnifico");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("gameboard.fxml"));
        GameboardController gameboardController =new GameboardController(userFX, color, this);
        loader.setController(gameboardController);

        Parent content = null;
        try {
            content = loader.load();
        } catch (IOException e) {
            logger.setLevel(Level.SEVERE);
            logger.severe(String.valueOf(e));
        }

        this.primaryStage.setScene(new Scene(content));
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        this.primaryStage.setWidth(bounds.getWidth());
        this.primaryStage.setHeight(bounds.getHeight());
        this.primaryStage.show();
        String astring;
        astring = userFX.receive();
        while (!"starttoupdate".equals(astring)){
            astring = userFX.receive();

        }
        MessageListener messageListener = new MessageListener(gameboardController,this,true);
        this.executorService = Executors.newCachedThreadPool();
        executorService.submit(messageListener);
        this.messageListener = messageListener;



    }

    /**
     * Starts the handle in GameboardController, where will be listened the actions performed by the user
     */
    public void handleStarter(GameboardController gameboardController) throws RemoteException {
        gameboardController.handle();
        messageListener.setRead(true);
    }

    /**
     * Update the gameboard with data from the server
     */
    public void updateController(GameboardController gameboardController) throws RemoteException {

        gameboardController.boardTranslator();
        gameboardController.dataTranslator();
        gameboardController.cardsTranslator();
        gameboardController.whoseTurnTranslator();
        this.messageListener.setRead(true);
    }

    public void excommunicationchoose(GameboardController gameboardController) throws RemoteException {
        userFX.send(gameboardController.excomAlert());
    }

    /**
     * Starts the last page "End"
     */
    public void startEnd() throws RemoteException{
        End end = new End(primaryStage, userFX);
        end.startEnd(primaryStage);
    }
}