package it.polimi.ingsw.GC_23.Controller;

import it.polimi.ingsw.GC_23.FamilyMember;
import it.polimi.ingsw.GC_23.Spaces.MarketSpace;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jesss on 23/05/17.
 */
public class MarketController extends PlaceFamilyMember {

    private FamilyMember familyMember;
    private MarketSpace[] marketSpace = new MarketSpace[DIM];
    private static int DIM = 4;
    private MarketSpace chosenSpace;

    public MarketController(FamilyMember familyMember, MarketSpace[] marketSpace) throws IOException {
        this.familyMember = familyMember;
        this.marketSpace = marketSpace;
        familyMember.getPlayer().getUserHandler().messageToUser("Choose the market space");
        int i = 0;
        for(MarketSpace m : marketSpace){
            if (m.getFamilyMember() == null){
                familyMember.getPlayer().getUserHandler().messageToUser("Press " + i + " for getting: " + m.getEffect().toString());
            }
            i++;
        }
        familyMember.getPlayer().getUserHandler().messageToUser("write");
        String sw = familyMember.getPlayer().getUserHandler().messageFromUser();
        int j = -1;
        try {
            j = Integer.parseInt(sw);

        } catch (NumberFormatException e) {
            familyMember.getPlayer().getUserHandler().messageToUser("Invalid format");
            isLegal();
        }
        this.chosenSpace = this.marketSpace[j];
        if (isLegal()) {
            makeAction();
        } else {
            familyMember.getPlayer().getUserHandler().messageToUser("YOU ARE NOT ALLOW TO DO THIS MOVE, DO SOMETHING ELSE!");
            familyMember.getPlayer().chooseMove(familyMember.getPlayer().getView(),1);
        }
    }

    public boolean isLegal(){
        if(!(chosenSpace.checkBusy())&&(chosenSpace.checkValue(familyMember)) && !familyMember.getPlayer().isNotPlayInMarket()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void makeAction() throws IOException {
        chosenSpace.getEffect().activeEffect(familyMember.getPlayer());
        chosenSpace.setFamilyMember(familyMember);
    }
}
