package it.polimi.ingsw.GC_23.Spaces;

import it.polimi.ingsw.GC_23.Enumerations.PlayerColor;
import it.polimi.ingsw.GC_23.FamilyMember;

/**
 * Created by Alessandro Tonali on 20/05/2017.
 */
public class CouncilSpace extends ActionSpace {
    private static int orderCounter;
    private FamilyMember[] playerOrder;
    public CouncilSpace(){
        super(1);
        orderCounter = 0;
    }

    public boolean isPresent(PlayerColor playerColor){
        for(int i = 0; i<playerOrder.length; i++) {
            if (playerOrder[i].getPlayer().getPlayerColor() == playerColor) {
                return true;
            }
        }
        //TODO bisogna capire dove va messo questo metodo
            return false;
        }

    public void setPlayerOrder(FamilyMember[] playerOrder) {
        this.playerOrder = playerOrder;
    }

    public static int getOrderCounter() {
        return orderCounter;
    }

    public FamilyMember[] getPlayerOrder() {
        return playerOrder;
    }
}
