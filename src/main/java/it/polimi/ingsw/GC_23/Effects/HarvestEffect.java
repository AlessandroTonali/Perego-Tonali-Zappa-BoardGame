package it.polimi.ingsw.GC_23.Effects;

import it.polimi.ingsw.GC_23.FamilyMember;
import it.polimi.ingsw.GC_23.Player;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Alessandro on 16/06/2017.
 */
public class HarvestEffect extends PermanentEffect  {

    private int harvestValue;
    private ArrayList<AbsEffect> effects;
    private boolean isActivable;

    public HarvestEffect(int harvestValue, ArrayList<AbsEffect> effects) {
        this.harvestValue = harvestValue;
        this.effects = effects;
    }

    public int getHarvestValue() {
        return harvestValue;
    }

    public void setHarvestValue(int harvestValue) {
        this.harvestValue = harvestValue;
    }

    public ArrayList<AbsEffect> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<AbsEffect> effects) {
        this.effects = effects;
    }

    public boolean checkActivable(int familyValue) {

        if (harvestValue <= familyValue) {
            isActivable = true;
        } else {
            isActivable = false;
        }

        return isActivable;
    }

    @Override
    public void activeEffect(Player player) throws IOException {
        if(isActivable) {
            for (int i = 0; i < effects.size(); i++) {
                effects.get(i).activeEffect(player);
            }
        } else {
            player.getUserHandler().messageToUser("Permanent effect on harvest isn't activable");
        }
    }
}
