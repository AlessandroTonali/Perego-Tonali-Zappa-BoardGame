package it.polimi.ingsw.GC_23.Resources;

import it.polimi.ingsw.GC_23.Effects.MalusOnBenefitEffect;
import it.polimi.ingsw.GC_23.Effects.PermanentEffect;
import it.polimi.ingsw.GC_23.Player;

import java.util.ArrayList;

/**
 * Created by Alessandro Tonali on 20/05/2017.
 */
public class ResourcesSet {
    private FaithPoints faithPoints;
    private Gold gold;
    private MilitaryPoints militaryPoints;
    private Servants servants;
    private Stone stone;
    private VictoryPoints victoryPoints;
    private Wood wood;
    private static int resourceNumber = 7;


    /**
     * check if two resourceSet are equals
     * @param o object to compare
     * @return true if the two objects are equal, false if not
     */
    @Override
    public boolean equals(Object o) {

        if (!(o instanceof ResourcesSet))
            return false;

        ResourcesSet resset = (ResourcesSet) o;

        if (!(this.getFaithPoints() == resset.getFaithPoints())) {return false;}
        if (! (this.getGold() == resset.getGold())) return false;
        if (!(this.getServants() == resset.getServants())) return false;
        if (!(this.getMilitaryPoints() == resset.getMilitaryPoints())) return false;
        if (!(this.getStone() == resset.getStone())) return false;
        if (!(this.getVictoryPoints() == resset.getVictoryPoints())) return false;
        return this.getWood() == resset.getWood();
    }

    @Override
    public int hashCode() {
        int result = faithPoints.hashCode();
        result = 31 * result + gold.hashCode();
        result = 31 * result + militaryPoints.hashCode();
        result = 31 * result + servants.hashCode();
        result = 31 * result + stone.hashCode();
        result = 31 * result + victoryPoints.hashCode();
        result = 31 * result + wood.hashCode();
        return result;
    }

    public ResourcesSet(int faithPoints, int gold, int militaryPoints, int servants,
                        int stone, int victoryPoints, int wood) {
        this.faithPoints = new FaithPoints(faithPoints);
        this.gold = new Gold(gold);
        this.militaryPoints = new MilitaryPoints(militaryPoints);
        this.servants = new Servants(servants);
        this.stone = new Stone(stone);
        this.victoryPoints = new VictoryPoints(victoryPoints);
        this.wood = new Wood(wood);
    }

    public ResourcesSet() {
        this.faithPoints = new FaithPoints(0);
        this.gold = new Gold(0);
        this.militaryPoints = new MilitaryPoints(0);
        this.servants = new Servants(0);
        this.stone = new Stone(0);
        this.victoryPoints = new VictoryPoints(0);
        this.wood = new Wood(0);
    }

    public void setFaithPoints(int faithPoints) {
        this.faithPoints.setQuantity(faithPoints);
    }

    public void setGold(int gold) {
        this.gold.setQuantity(gold);
    }

    public void setMilitaryPoints(int militaryPoints) {
        this.militaryPoints.setQuantity(militaryPoints);
    }

    public void setServants(int servants) {
        this.servants.setQuantity(servants);
    }

    public void setStone(int stone) {
        this.stone.setQuantity(stone);
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints.setQuantity(victoryPoints);
    }

    public void setWood(int wood) {
        this.wood.setQuantity(wood);
    }

    public int getFaithPoints() {
        return faithPoints.getQuantity();
    }

    public int getGold() {
        return gold.getQuantity();
    }

    public int getMilitaryPoints() {
        return militaryPoints.getQuantity();
    }

    public int getServants() {
        return servants.getQuantity();
    }

    public int getStone() {
        return stone.getQuantity();
    }

    public int getVictoryPoints() {return victoryPoints.getQuantity();
    }

    public Wood getWoodObj() {
        return wood;
    }

    public FaithPoints getFaithPointsObj() {
        return faithPoints;
    }

    public Gold getGoldObj() {
        return gold;
    }

    public MilitaryPoints getMilitaryPointsObj() {
        return militaryPoints;
    }

    public Servants getServantsObj() {
        return servants;
    }

    public Stone getStoneobj() {
        return stone;
    }

    public VictoryPoints getVictoryPointsObj() {
        return victoryPoints;
    }

    public int getWood() {
        return wood.getQuantity();
    }

    public String toString(){
        return "faith points " + faithPoints.toString() + " military points " + militaryPoints.toString() + " gold " +
                gold.toString() + " servants " + servants.toString() + " wood " + wood.toString() +
                " stone " + stone.toString() + " victory points " + victoryPoints.toString();

    }

    public int[] getArray(){
        int[] resarray = new int[resourceNumber ];
        resarray[0] = this.getFaithPoints();
        resarray[1] = this.getGold();
        resarray[2] = this.getMilitaryPoints();
        resarray[3] = this.getServants();
        resarray[4] = this.getStone();
        resarray[5] = this.getVictoryPoints();
        resarray[6] = this.getWood();

        return  resarray;

    }

    /**
     * check this object if bigger than the param in input
     * @param checked resources to be checked
     * @return true if the object is bigger, false if not
     */
    public boolean checkAffordable( ResourcesSet checked) {
        int[] playerSet = this.getArray();
        int[] checkSet = checked.getArray();
        for(int i = 0; i < resourceNumber; i++) {
            if(playerSet[i] < checkSet[i]){
                return false;
            }
        }
        return true;


    }

    public void setArray(int[] setarray ) {
        this.faithPoints.setQuantity(setarray[0]);
        this.gold.setQuantity(setarray[1]);
        this.militaryPoints.setQuantity(setarray[2]);
        this.servants.setQuantity(setarray[3]);
        this.stone.setQuantity(setarray[4]);
        this.victoryPoints.setQuantity(setarray[5]);
        this.wood.setQuantity(setarray[6]);

    }

    /**
     * add at this object the resources at first parameter
     * @param prize resources to add
     * @param player to add resources
     */
    public void sum(ResourcesSet prize, Player player) {
        checkPermanentEffect(player, prize);
        int[] playerset = this.getArray();
        int[] prizeset = prize.getArray();
        for(int i = 0; i < resourceNumber; i++){
            int result = playerset[i] + prizeset[i];
            if (result < 0) {
                playerset[i] = 0;
            } else {
                playerset[i] = result;
            }
        }
        this.setArray(playerset);
    }

    /**
     * subtract the resource
     * @param prize resources to subtract
     */
    public void pay(ResourcesSet prize) {
        int[] playerset = this.getArray();
        int[] prizeset = prize.getArray();
        for(int i = 0; i < resourceNumber; i++){
            playerset[i] = playerset[i] - prizeset[i];
        }
        this.setArray(playerset);
    }

    /**
     * check and active the permanent effect on the resources
     * @param player that you want to check the permanent effect
     * @param prize
     */
    public void checkPermanentEffect(Player player, ResourcesSet prize){
        ArrayList<PermanentEffect> permanentEffectArrayList = player.getPermanentEffects();
        for (int i = 0; i < permanentEffectArrayList.size(); i++) {
            PermanentEffect permanentEffect = permanentEffectArrayList.get(i);
            if (permanentEffect instanceof MalusOnBenefitEffect) {
                String malusType = ((MalusOnBenefitEffect) permanentEffect).getType();
                switch (malusType){
                    case "coin":
                        prize.setGold(prize.getGold() + ((MalusOnBenefitEffect) permanentEffect).getMalusValue());
                        break;
                    case "militaryPoint":
                        prize.setMilitaryPoints(prize.getMilitaryPoints() + ((MalusOnBenefitEffect) permanentEffect).getMalusValue());
                        break;
                    case "wood":
                        prize.setWood(prize.getWood() + ((MalusOnBenefitEffect) permanentEffect).getMalusValue());
                        break;
                    case "victoryPoint":
                        prize.setVictoryPoints(prize.getVictoryPoints() + ((MalusOnBenefitEffect) permanentEffect).getMalusValue());
                        break;
                    case "stone":
                        prize.setStone(prize.getStone() + ((MalusOnBenefitEffect) permanentEffect).getMalusValue());
                        break;
                    case "servant":
                        prize.setServants(prize.getServants() + ((MalusOnBenefitEffect) permanentEffect).getMalusValue());
                        break;
                    case "faithPoint":
                        prize.setFaithPoints(prize.getFaithPoints() + ((MalusOnBenefitEffect) permanentEffect).getMalusValue());
                        break;

                }
            }
        }
    }






}
