package it.polimi.ingsw.GC_23;

import it.polimi.ingsw.GC_23.Enumerations.FamilyColor;
import it.polimi.ingsw.GC_23.Enumerations.PlayerColor;
import it.polimi.ingsw.GC_23.Resources.ResourcesSet;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jesss on 02/06/17.
 */
public class Creator {
    private static Creator creator;
    private static Board board;
    private static ArrayList<Player> players;
    private static Gameplay gameplay;

    //creo tutti i player che poi verranno assegnati a seconda dei giocatori presenti
    //todo:disporre tessere scomunica
    private Creator(){
        board= board.getBoard();
        //todo: settare le carte nella tower
        players = new ArrayList<Player>();
        //todo: assegno bonus tile
        players.add(new Player(PlayerColor.RED, null));
        players.add(new Player(PlayerColor.GREEN, null));
        players.add(new Player(PlayerColor.BLUE, null));
        players.add(new Player(PlayerColor.YELLOW, null));
        for(Player p: players){
            FamilyMember[] familyMembers = new FamilyMember[4];
            familyMembers[0]= new FamilyMember(p, FamilyColor.ORANGE, board.getDiceOValue());
            familyMembers[1]= new FamilyMember(p, FamilyColor.WHITE, board.getDiceWValue());
            familyMembers[2]= new FamilyMember(p, FamilyColor.BLACK, board.getDiceBValue());
            familyMembers[3]= new FamilyMember(p, FamilyColor.NEUTRAL, 0);
            p.setFamilyMembers(familyMembers);
            p.setResources(new ResourcesSet(0,0,0,3,2,0,2));
        }
        Collections.shuffle(players);
        players.get(0).getResources().getGoldObj().add(5);
        players.get(1).getResources().getGoldObj().add(6);
        players.get(2).getResources().getGoldObj().add(7);
        players.get(3).getResources().getGoldObj().add(8);
        gameplay= new Gameplay(players);
        gameplay.scheduling();
    }

    synchronized static Creator getCreator(){
        if(creator==null){
            creator = new Creator();
        }
        return creator;
    }
}
