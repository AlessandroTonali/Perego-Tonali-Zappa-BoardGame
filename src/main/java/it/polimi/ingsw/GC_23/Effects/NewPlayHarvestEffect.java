package it.polimi.ingsw.GC_23.Effects;

import it.polimi.ingsw.GC_23.Controller.HarvestController;
import it.polimi.ingsw.GC_23.Enumerations.FamilyColor;
import it.polimi.ingsw.GC_23.FamilyMember;
import it.polimi.ingsw.GC_23.Player;

import java.io.IOException;


/**
 * Created by Alessandro on 06/06/2017.
 */
public class NewPlayHarvestEffect extends AbsEffect {

    private int diceValue;

    /**
     * new play on harvest space
     * @param diceValue value of the new play
     */
    public NewPlayHarvestEffect(int diceValue) {
        this.diceValue = diceValue;
    }

    /**
     *
     * @param player that want to active the effect
     * @throws IOException
     */
    @Override
    public void activeEffect(Player player) throws IOException {
        if(player.getUserHandler().isGuiInterface()){
            player.getUserHandler().messageToUser("newPlayHarvestEffect");
        }
        FamilyMember familyMember = new FamilyMember(player, FamilyColor.NEUTRAL, diceValue);
        new HarvestController(familyMember, player.getView().getHarvestSpace());
    }

    public int getDiceValue() {
        return diceValue;
    }
}
