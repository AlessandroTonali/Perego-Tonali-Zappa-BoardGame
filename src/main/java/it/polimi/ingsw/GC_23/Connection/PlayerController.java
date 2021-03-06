package it.polimi.ingsw.GC_23.Connection;

import it.polimi.ingsw.GC_23.Enumerations.PlayerColor;
import it.polimi.ingsw.GC_23.ParseJson;
import it.polimi.ingsw.GC_23.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jesss on 04/06/17.
 */
public class PlayerController {
    private Map<Player, String> userPlayerAssociation;

    public PlayerController() {
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(new Player(PlayerColor.GREEN, ParseJson.getParseJson().getBonusTile1()));
        players.add(new Player(PlayerColor.RED, ParseJson.getParseJson().getBonusTile1()));
        players.add(new Player(PlayerColor.BLUE, ParseJson.getParseJson().getBonusTile1()));
        players.add(new Player(PlayerColor.YELLOW, ParseJson.getParseJson().getBonusTile1()));
        userPlayerAssociation = new HashMap<>();
        for(Player p : players){
            this.userPlayerAssociation.put(p, null);
        }
    }

    /**
     * @return the map of color and username associations
     */
    public Map<Player, String> getAssociation() {
        return userPlayerAssociation;
    }
}
