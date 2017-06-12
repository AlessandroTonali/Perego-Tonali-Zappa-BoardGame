package it.polimi.ingsw.GC_23.Spaces;

import it.polimi.ingsw.GC_23.Cards.Card;
import it.polimi.ingsw.GC_23.Cards.TerritoryCard;
import it.polimi.ingsw.GC_23.Enumerations.FamilyColor;
import it.polimi.ingsw.GC_23.FamilyMember;
import it.polimi.ingsw.GC_23.Player;
import it.polimi.ingsw.GC_23.Spaces.TowerSpace;

/**
 * Created by Alessandro Tonali on 20/05/2017.
 */
public class Tower {
    private int DIM=3;
    private TowerSpace[] spaces = new TowerSpace[DIM];

    public TowerSpace[] getSpaces() {
        return spaces;
    }

    public int getDIM() {
        return DIM;
    }

    public void setDIM(int DIM) {
        this.DIM = DIM;
    }

    public void setSpaces(TowerSpace[] spaces) {
        this.spaces = spaces;
    }

    public Tower(TowerSpace[] spaces) {
        this.spaces = spaces;
    }


    // controlla se è presente un familiare del tuo colore nella torre
    public boolean checkFamiliarTower(FamilyMember familyMember) {
        boolean myFamiliarPresence = false;
        TowerSpace[] towerSpaces = this.getSpaces();
        if (familyMember.getFamilyColor() != FamilyColor.NEUTRAL) {
            for (int i = 0; i < towerSpaces.length; i++) {
                if (towerSpaces[i].getFamilyMember()!= null && towerSpaces[i].getFamilyMember().getPlayer().isEquals(familyMember.getPlayer())) {
                    if (towerSpaces[i].getFamilyMember().getFamilyColor() != FamilyColor.NEUTRAL) {
                        myFamiliarPresence = true;
                    }
                }
            }
        }
        return myFamiliarPresence;
    }

    // controlla se è presente un familiare nella torre
    public boolean checkOtherFamiliar() {
        boolean otherFamiliarPresence  = false;
        TowerSpace[] towerSpaces = this.getSpaces();
        for (int i = 0; i < towerSpaces.length ; i++) {
            if (towerSpaces[i].getFamilyMember() != null) {
                otherFamiliarPresence = true;
            }
        }
        return otherFamiliarPresence;
    }

    public TowerSpace chooseTowerSpace(Player player) {
        System.out.println("choose the tower space");
        String input = player.getNextLine();
        int i;
        try {
            i = Integer.parseInt(input);
            System.out.println("ciao");

        } catch (NumberFormatException e) {
            System.out.println("unvalid format");
            return chooseTowerSpace(player);
        }

        try {
            System.out.println("u choosed " + i);
            return this.spaces[i];

        } catch (NullPointerException e) {
            System.out.println("number out of bound, try again");
            return chooseTowerSpace(player);
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(TowerSpace t : spaces){
            stringBuilder.append(t.toString() + "\n" + "_______________________________________________________" +
                    "____________________________________________________________" + "\n");

        }
        return String.valueOf(stringBuilder);
    }

}

