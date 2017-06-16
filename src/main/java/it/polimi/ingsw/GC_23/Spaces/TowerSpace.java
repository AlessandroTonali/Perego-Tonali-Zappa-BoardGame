package it.polimi.ingsw.GC_23.Spaces;

import it.polimi.ingsw.GC_23.Cards.Card;
import it.polimi.ingsw.GC_23.Effects.AbsEffect;
import it.polimi.ingsw.GC_23.Effects.BenefitsEffect;
import it.polimi.ingsw.GC_23.Effects.PlusTowerEffect;
import it.polimi.ingsw.GC_23.Enumerations.NewPlayColor;

import java.util.ArrayList;

/**
 * Created by Alessandro Tonali on 20/05/2017.
 */
public class TowerSpace extends ActionSpace {
    private Card card;
    private final BenefitsEffect benefitsEffect; //bonus immediato

    public TowerSpace(Card card, BenefitsEffect effect, int value) {
        super(value);
        this.card = card;
        this.benefitsEffect = effect;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }


    public BenefitsEffect getBenefitsEffect() {
        return benefitsEffect;
    }

    public void resetCard(){
        this.card= null;
    }

    @Override
    public String toString() {
        if(super.getFamilyMember() == null){
            return "value: "+ super.getValue() + "\neffect: " +
                    this.benefitsEffect.toString() + "\n" + this.card.toString()
                    + "\n no player inside";
        }
        return "value: "+ super.getValue() + "\neffect: " +
                this.benefitsEffect.toString() + "\n" + this.card.toString() + "\n family member "
                + super.getFamilyMember().toString();

    }
}
