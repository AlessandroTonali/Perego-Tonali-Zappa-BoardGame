package it.polimi.ingsw.GC_23.Effects;

import it.polimi.ingsw.GC_23.Cards.BuildingCard;
import it.polimi.ingsw.GC_23.Player;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Alessandro on 16/06/2017.
 */
public class EndGameEffect extends PermanentEffect {

    private int type;

    /**
     * permanent effect activated at the end of the game
     * @param type
     */
    public EndGameEffect(int type) {
        this.type = type;
    }

    /**
     *
     * @param player that want to active the effect
     * @throws IOException
     */
    @Override
    public void activeEffect(Player player) throws IOException {
        int playerVictoryPoint = player.getResources().getVictoryPoints();
        switch (type) {
            case 1:
                int victoryPointLost = playerVictoryPoint / 5;
                playerVictoryPoint = playerVictoryPoint - victoryPointLost;
                player.getResources().setVictoryPoints(playerVictoryPoint);
                break;
            case 2:
                int playerMilitaryPoint = player.getResources().getMilitaryPoints();
                player.getResources().setVictoryPoints(playerVictoryPoint - playerMilitaryPoint);
                break;
            case 3:
                int totStone = 0, totWood = 0;
                ArrayList<BuildingCard> buildingCards = player.getCardOfPlayer().getBuildingCards();
                for (int i = 0; i < buildingCards.size(); i++) {
                    int stone = buildingCards.get(i).getCostSelected().getResources().getStone();
                    int wood = buildingCards.get(i).getCostSelected().getResources().getWood();

                    totStone = totStone + stone;
                    totWood = totWood + wood;
                }
                player.getResources().setVictoryPoints(playerVictoryPoint - (totStone + totWood));
                break;
            case 4:
                int stone = player.getResources().getStone();
                int wood = player.getResources().getWood();
                int coin = player.getResources().getGold();
                int servant = player.getResources().getServants();
                int tot = stone + wood + coin + servant;
                player.getResources().setVictoryPoints(playerVictoryPoint - tot);
                break;
        }
    }
}
