package it.polimi.ingsw.GC_23.Controller;

import it.polimi.ingsw.GC_23.Effects.AbsEffect;
import it.polimi.ingsw.GC_23.FamilyMember;
import it.polimi.ingsw.GC_23.Resources.ResourcesSet;
import it.polimi.ingsw.GC_23.SingleCost;
import it.polimi.ingsw.GC_23.Spaces.Tower;
import it.polimi.ingsw.GC_23.Spaces.TowerSpace;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by jesss on 23/05/17.
 */
public class OtherCardsController extends TowerController {
    private FamilyMember familyMember;
    private TowerSpace towerSpace;
    private  Tower tower;

    public void makeAction() {

    }

    public boolean isLegal(){
        return false;
    }

    public OtherCardsController(FamilyMember familyMember, Tower tower) throws IOException {
        super(familyMember, tower);
        SingleCost cost = super.getTowerSpace().getCard().getCost(familyMember.getPlayer());
        if (isLegal(cost)) {
            makeAction(cost);
            System.out.println("success");
        } else {
            System.out.println("error");
            familyMember.getPlayer().chooseMove(familyMember.getPlayer().getView(),1);
        }
    }

    public OtherCardsController(FamilyMember familyMember, Tower tower, TowerSpace towerSpace) throws IOException {
        super(familyMember, tower, towerSpace);

        super.getTowerSpace().getCard().getCost(familyMember.getPlayer());
        SingleCost cost = super.getTowerSpace().getCard().getCost(familyMember.getPlayer());
        if (isLegal(cost)) {
            makeAction(cost);
            System.out.println("success");
        } else {
            System.out.println("error");
        }
    }

    public boolean isLegal(SingleCost cost) throws RemoteException {

        if(super.getTower().checkOtherFamiliar()) {
            cost.getResources().sum(new ResourcesSet(0,3,0,0,0,0,0));
        }

        boolean legal = true;

        legal = legal && !super.getTowerSpace().checkBusy();

        legal = legal && super.getFamilyMember().getPlayer().getResources().checkAffordable(cost.getResources());

        legal = legal && (super.getFamilyMember().getValue() >= super.getTowerSpace().getValue());

        legal = legal && super.getTowerSpace().getCard().checkTakeable(super.getFamilyMember().getPlayer());

      legal = legal && !super.getTower().checkFamiliarTower(super.getFamilyMember());

        return legal;
    }

    public void makeAction(SingleCost cost) throws IOException {
        super.getFamilyMember().getPlayer().getResources().pay(cost.getResources());
        ArrayList<AbsEffect> effects = super.getTowerSpace().getCard().getImmediateEffect();
        for(AbsEffect i : effects){
            i.activeEffect(super.getFamilyMember().getPlayer());
        }
        super.getTowerSpace().setFamilyMember(super.getFamilyMember());
        super.getTowerSpace().getCard().addCardOfPlayer(super.getFamilyMember().getPlayer());
    }


}


