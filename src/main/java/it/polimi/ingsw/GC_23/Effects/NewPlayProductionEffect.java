package it.polimi.ingsw.GC_23.Effects;

import it.polimi.ingsw.GC_23.Controller.ProductionController;
import it.polimi.ingsw.GC_23.Enumerations.FamilyColor;
import it.polimi.ingsw.GC_23.FamilyMember;
import it.polimi.ingsw.GC_23.Player;

import java.io.IOException;


/**
 * Created by Alessandro on 06/06/2017.
 */
public class NewPlayProductionEffect extends AbsEffect {
    private int diceValue;

    /**
     * new play on production space
     * @param diceValue value of the new play
     */
    public NewPlayProductionEffect(int diceValue) {
        this.diceValue = diceValue;
    }

    /**
     *
     * @param player that want to active the effect
     * @throws IOException
     */
    @Override
    public void activeEffect(Player player) throws IOException{
        if(player.getUserHandler().isGuiInterface()){
            player.getUserHandler().messageToUser("newPlayProductionEffect");
        }
        FamilyMember familyMember = new FamilyMember(player, FamilyColor.NEUTRAL, diceValue);
        new ProductionController(familyMember, player.getView().getProductionSpace());
    }

    public int getDiceValue() {
        return diceValue;
    }
}
