package it.polimi.ingsw.GC_23.Controller;

import it.polimi.ingsw.GC_23.Effects.AbsEffect;
import it.polimi.ingsw.GC_23.FamilyMember;
import it.polimi.ingsw.GC_23.Player;
import it.polimi.ingsw.GC_23.Spaces.CouncilSpace;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jesss on 23/05/17.
 */
public class CouncilController extends PlaceFamilyMember {
    CouncilSpace councilSpace;
    FamilyMember familyMember;

    /**
     * controller of the council space
     * @param councilSpace council space associated
     * @param familyMember family member that you want to put in
     * @throws IOException
     */
    public CouncilController(CouncilSpace councilSpace, FamilyMember familyMember) throws IOException {
        this.councilSpace = councilSpace;
        this.familyMember = familyMember;
        if(isLegal()){
            if(familyMember.getPlayer().getUserHandler().isGuiInterface()) {
                familyMember.getPlayer().getUserHandler().messageToUser("OK");
            }
            makeAction();
        }
        else {
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
    @Override
    public boolean isLegal(){
        if(familyMember.getValue()>=1 && !councilSpace.checkFamiliar(familyMember.getPlayer().getPlayerColor())){
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
        Player player = familyMember.getPlayer();
        councilSpace.setFamilyMember(familyMember);
        ArrayList<AbsEffect> effects = councilSpace.getEffects();
        for(AbsEffect i : effects) {
            if(player.getUserHandler().isGuiInterface()){
                player.getUserHandler().messageToUser("effect");
            }
            i.activeEffect(familyMember.getPlayer());
        }
        if(player.getUserHandler().isGuiInterface()){
            player.getUserHandler().messageToUser("effectended");
        }
        if(!player.getUserHandler().isGuiInterface()) {
            player.getUserHandler().messageToUser(familyMember.getPlayer().getResources().toString());
        }
    }
}


