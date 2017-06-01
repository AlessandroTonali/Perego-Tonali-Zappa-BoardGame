package it.polimi.ingsw.GC_23;

import it.polimi.ingsw.GC_23.Effects.BenefitsEffect;
import it.polimi.ingsw.GC_23.Effects.CouncilPrivilegeEffect;
import it.polimi.ingsw.GC_23.Effects.Effect;
import it.polimi.ingsw.GC_23.Effects.ImplicationEffect;
import it.polimi.ingsw.GC_23.Resources.ResourcesSet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Alessandro on 01/06/2017.
 */
public class ParseJson {

    private HashMap<Integer, Effect> effectMap;
    private HashMap<Integer,BenefitsEffect> benefitsEffectMap;

    public ParseJson() {
        parseCard();
        parseEffect();

    }

    private void parseCard() {
        String jsonContent = null;
        try {
            Scanner scanner = new Scanner(new File("Cards.txt"));
            jsonContent = scanner.useDelimiter("\\Z").next();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONObject rootObject = new JSONObject(jsonContent);

        JSONArray ventureCard = rootObject.getJSONArray("VentureCard");
        for (int x = 0; x < ventureCard.length(); x++) {
            System.out.println(ventureCard.getJSONObject(x).getString("name"));
            System.out.println(ventureCard.getJSONObject(x).getString("cost"));
            String name = ventureCard.getJSONObject(x).getString("name");


        }

        System.out.println();

        JSONArray buildingCard = rootObject.getJSONArray("BuildingCard");
        for (int x = 0; x < buildingCard.length(); x++) {
            System.out.println(buildingCard.getJSONObject(x).getString("name"));
            JSONArray costs = buildingCard.getJSONObject(x).getJSONArray("cost");

            JSONArray immediateEffects = buildingCard.getJSONObject(x).getJSONArray("immediateEffect");
            for (int y = 0; y < immediateEffects.length() ; y++) {

            }


            JSONArray permanentEffects = buildingCard.getJSONObject(x).getJSONArray("permanentEffect");
            for (int y = 0; y < permanentEffects.length() ; y++) {

            }
        }

    }

    public void parseEffect() {
        String jsonContent = null;
        effectMap = new HashMap<Integer,Effect>();
        try {
            Scanner scanner = new Scanner(new File("Effect.txt"));
            jsonContent = scanner.useDelimiter("\\Z").next();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        benefitsEffectMap = new HashMap<Integer,BenefitsEffect>();


        JSONObject rootObject = new JSONObject(jsonContent);

        JSONArray benefitEffects = rootObject.getJSONArray("BenefitEffect");
        parseBenefitEffect(benefitEffects);



        JSONArray councilPrivilegeEffects = rootObject.getJSONArray("CouncilPrivilegeEffect");
        parseCouncilPrivilegeEffect(councilPrivilegeEffects);


        JSONArray implicationEffects = rootObject.getJSONArray("ImplicationEffect");
        parseImplicationEffect(implicationEffects);

        JSONArray discountEffects = rootObject.getJSONArray("DiscountEffect");

        JSONArray newPlayEffects = rootObject.getJSONArray("NewPlayEffect");
        parseNewPlayEffect(newPlayEffects);
    }

    private void parseNewPlayEffect(JSONArray newPlayEffects) {

    }

    public void parseBenefitEffect(JSONArray benefitEffects) {
        for (int i = 0; i < benefitEffects.length() ; i++) {
            JSONObject jsonObject = benefitEffects.getJSONObject(i);
            BenefitsEffect benefitsEffect = parseBenefit(jsonObject);
            benefitsEffectMap.put(jsonObject.getInt("id"),benefitsEffect);
            Effect effect = new Effect(null, benefitsEffect, null, null, null);
            effectMap.put(jsonObject.getInt("id"),effect);

            //System.out.println(effect.getResources().toString());
        }
    }

    private void parseCouncilPrivilegeEffect(JSONArray councilPrivilegeEffects) {
        BenefitsEffect[] councilPrivilege = new BenefitsEffect[5];
        councilPrivilege[0] = benefitsEffectMap.get("10");
        councilPrivilege[1] = benefitsEffectMap.get("11");
        councilPrivilege[2] = benefitsEffectMap.get("12");
        councilPrivilege[3] = benefitsEffectMap.get("13");
        councilPrivilege[4] = benefitsEffectMap.get("14");
        for (int i = 0; i < councilPrivilegeEffects.length(); i++) {
            JSONObject jsonObject = councilPrivilegeEffects.getJSONObject(i);
            CouncilPrivilegeEffect councilPrivilegeEffect = new CouncilPrivilegeEffect(councilPrivilege,jsonObject.getInt("number_privilege"), jsonObject.getBoolean("is_different"));
            Effect effect = new Effect(councilPrivilegeEffect, null, null, null, null);
            effectMap.put(jsonObject.getInt("id"), effect);

        }
    }

    private void parseImplicationEffect(JSONArray implicationEffects) {
        for (int i = 0; i < implicationEffects.length() ; i++) {
            ArrayList<SingleCost> requirments = new ArrayList<>();
            ArrayList<BenefitsEffect> givings = new ArrayList<>();
            JSONObject jsonObject = implicationEffects.getJSONObject(i);
            JSONArray jsonArrayRequirment = jsonObject.getJSONArray("requirment");
            for (int j = 0; j < jsonArrayRequirment.length(); j++) {
                BenefitsEffect benefitsEffect = benefitsEffectMap.get(jsonArrayRequirment.getJSONObject(j).getInt("requirment_id"));
                requirments.add(new SingleCost(benefitsEffect.getResources()));
            }

            JSONArray jsonArrayGiving = jsonObject.getJSONArray("giving");
            for (int j = 0; j < jsonArrayGiving.length(); j++) {
                BenefitsEffect benefitsEffect = benefitsEffectMap.get(jsonArrayGiving.getJSONObject(j).getInt("giving_id"));
                givings.add(benefitsEffect);
            }

            ImplicationEffect implicationEffect =  new ImplicationEffect(requirments,givings);
            Effect effect = new Effect(null, null, implicationEffect, null, null);
            effectMap.put(jsonObject.getInt("id"),effect);

        }
    }



    public BenefitsEffect parseBenefit(JSONObject jsonObject) {
        int faithPoint = jsonObject.getInt("faithPoint");
        int gold = jsonObject.getInt("gold");
        int militaryPoint = jsonObject.getInt("militaryPoint");
        int servant = jsonObject.getInt("servant");
        int stone = jsonObject.getInt("stone");
        int victoryPoint = jsonObject.getInt("victoryPoint");
        int wood = jsonObject.getInt("wood");
        ResourcesSet resources = new ResourcesSet(faithPoint, gold, militaryPoint, servant, stone, victoryPoint, wood);
        BenefitsEffect benefitsEffect = new BenefitsEffect(resources);

        return benefitsEffect;


    }

    public ArrayList<SingleCost> parseCost(JSONArray costs) {
        ArrayList<SingleCost> singleCosts = null;

        for (int y = 0; y < costs.length(); y++) {
            if (costs.getJSONObject(y).has("stone")) {
                System.out.println(costs.getJSONObject(y).getString("stone"));
            }
            if (costs.getJSONObject(y).has("wood")) {
                System.out.println(costs.getJSONObject(y).getString("wood"));
            }
            if (costs.getJSONObject(y).has("coin")) {
                System.out.println(costs.getJSONObject(y).getString("coin"));
            }
            if (costs.getJSONObject(y).has("servant")) {
                System.out.println(costs.getJSONObject(y).getString("servant"));
            }
        }

        SingleCost singleCost = new SingleCost(new ResourcesSet(0,0,0,0,0,0,0));
        singleCosts.add(singleCost);

        return singleCosts;
    }
}