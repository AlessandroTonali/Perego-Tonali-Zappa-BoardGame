package it.polimi.ingsw.GC_23.Controller;

import it.polimi.ingsw.GC_23.FamilyMember;
import it.polimi.ingsw.GC_23.Spaces.MarketSpace;
import it.polimi.ingsw.GC_23.StringTyper;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jesss on 23/05/17.
 */
public class MarketController extends PlaceFamilyMember {

    private FamilyMember familyMember;
    private MarketSpace[] marketSpace = new MarketSpace[DIM];
    private static int DIM = 4;
    private MarketSpace chosenSpace;
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     *
     * @param familyMember that you want to put in market space
     * @param marketSpace associated at the move
     * @throws IOException
     */
    public MarketController(FamilyMember familyMember, MarketSpace[] marketSpace) throws IOException {
        this.familyMember = familyMember;
        this.marketSpace = marketSpace;
        if(!familyMember.getPlayer().getUserHandler().isGuiInterface()) {
            familyMember.getPlayer().getUserHandler().messageToUser("Choose the market space");
        }
        int i = 0;
        for(MarketSpace m : marketSpace){
            if (m.getFamilyMember() == null){
                if(!familyMember.getPlayer().getUserHandler().isGuiInterface()) {
                    familyMember.getPlayer().getUserHandler().messageToUser("Press " + i + " for getting: " + m.getEffect().toString());
                }
            }
            i++;
        }
        ExecutorService executorService = Executors.newCachedThreadPool();
        StringTyper stringTyper = new StringTyper(familyMember.getPlayer());
        executorService.submit(stringTyper);

        int j = -1;
        while (!familyMember.getPlayer().isTimeIsOver() && !familyMember.getPlayer().isTyped()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.setLevel(Level.SEVERE);
                logger.severe(String.valueOf(e));
                Thread.currentThread().interrupt();

            }
        }
        if(familyMember.getPlayer().isTimeIsOver()){
            familyMember.getPlayer().setTimeIsOver(false);
            if(!familyMember.getPlayer().getUserHandler().isGuiInterface()) {
                familyMember.getPlayer().getUserHandler().messageToUser("read");
            }
            return;
        }
        if(familyMember.getPlayer().isTyped()){
            familyMember.getPlayer().setTyped(false);
            j = familyMember.getPlayer().getTypedInt();
        }
        this.chosenSpace = this.marketSpace[j];
        if (isLegal()) {
            if(familyMember.getPlayer().getUserHandler().isGuiInterface()) {
                familyMember.getPlayer().getUserHandler().messageToUser("OK");
            }
            makeAction();
        } else {
            if(familyMember.getPlayer().getUserHandler().isGuiInterface()) {
                familyMember.getPlayer().getUserHandler().messageToUser("KO");
            }
            if(!familyMember.getPlayer().getUserHandler().isGuiInterface()) {
                familyMember.getPlayer().getUserHandler().messageToUser("YOU ARE NOT ALLOW TO DO THIS MOVE, DO SOMETHING ELSE!");
            }
            throw new IllegalArgumentException();
        }
    }

    /**
     * show on interface
     * @return
     */
    public boolean isLegal(){

        if(!(chosenSpace.checkBusy())&&(chosenSpace.checkValue(familyMember)) && !familyMember.getPlayer().isNotPlayInMarket()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * show on interface
     * @throws IOException
     */
    @Override
    public void makeAction() throws IOException {
        if(familyMember.getPlayer().getUserHandler().isGuiInterface()) {
            familyMember.getPlayer().getUserHandler().messageToUser("effect");
        }
        chosenSpace.getEffect().activeEffect(familyMember.getPlayer());
        if(familyMember.getPlayer().getUserHandler().isGuiInterface()){
            familyMember.getPlayer().getUserHandler().messageToUser("effectended");
        }
        chosenSpace.setFamilyMember(familyMember);
    }
}
