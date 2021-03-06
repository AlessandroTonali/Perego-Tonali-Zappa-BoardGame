package it.polimi.ingsw.GC_23.Controller;

import it.polimi.ingsw.GC_23.FamilyMember;
import it.polimi.ingsw.GC_23.Resources.ResourcesSet;

import java.io.IOException;

/**
* Created by jesss on 23/05/17.
*/
public class IncreaseFamilyValue implements Controller {

    int quantity;
    FamilyMember familyMember;

    /**
     *
     * @param quantity number of servants that you want to use
     * @param familyMember that you want to increase the value
     * @throws IOException
     */
    public IncreaseFamilyValue(int quantity, FamilyMember familyMember) throws IOException {
        this.quantity = quantity;
        this.familyMember = familyMember;
        if (!this.isLegal()) {
            if(familyMember.getPlayer().getUserHandler().isGuiInterface()){
                familyMember.getPlayer().getUserHandler().messageToUser("KO");
            }
            if(!familyMember.getPlayer().getUserHandler().isGuiInterface()) {
                familyMember.getPlayer().getUserHandler().messageToUser("YOU ARE NOT ALLOW TO DO THIS MOVE, DO SOMETHING ELSE!");
            }
            throw new IllegalArgumentException();
        } else {
            if(familyMember.getPlayer().getUserHandler().isGuiInterface()){
                familyMember.getPlayer().getUserHandler().messageToUser("OK");
            }
            this.makeAction();

        }
    }

    /**
     * show on interface
     * @return
     */
    public boolean isLegal() {
        ResourcesSet playerRes = this.familyMember.getPlayer().getResources();
        if (playerRes.checkAffordable(new ResourcesSet(0, 0, 0, quantity, 0
                , 0, 0))) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * show on interface
     */
    @Override
    public void makeAction() {
        if (familyMember.getPlayer().isDoubleServantToIncrease()) {
            if  (quantity % 2 == 1) {
                quantity = (quantity - 1) / 2;
            } else {
                quantity = quantity / 2;
            }
        }
        familyMember.increaseFamilyValue(quantity);
    }

}