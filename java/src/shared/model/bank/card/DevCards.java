package shared.model.bank.card;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.bank.PlayerBank;
import shared.model.player.Player;

import java.util.Stack;

/**
 * This class represents a list of Development Cards
 */
public class DevCards {

    private DevCard monopoly, roadBuilding, yearOfPlenty, soldier, monument;

    public DevCards(){
        initialize();
    }

    private void initialize(){
        monopoly = new DevCard(DevCardType.MONOPOLY) {
            @Override
            public void playAction(PlayerBank context) {
                //name 1 resource
                //ALL players give all of that resource
            }

            private void action(ResourceType type){

            }
        };

        roadBuilding = new DevCard(DevCardType.ROAD_BUILD) {
            @Override
            public void playAction(PlayerBank context) {
                try {
                    context.buyRoad();
                    context.buyRoad();
                } catch (InsufficientResourcesException e) {
                    e.printStackTrace();
                } catch (CatanException e) {
                    e.printStackTrace();
                }
                // Player can build 2 roads
            }
        };

        yearOfPlenty = new DevCard(DevCardType.YEAR_OF_PLENTY) {
            @Override
            public void playAction(PlayerBank context) {
                //take 2 resources from supply
            }

            private void action(PlayerBank context, ResourceType type1, ResourceType type2){
                context.getResources().getResource(type1).addResource(1);
                context.getResources().getResource(type2).addResource(1);
            }
        };

        soldier = new DevCard(DevCardType.SOLDIER) {
            @Override
            public void playAction(PlayerBank context) {
                //move robber
                context.addKnights(1);
            }
        };

        monument = new DevCard(DevCardType.MONUMENT) {
            @Override
            public void playAction(PlayerBank context) {
                context.addMonuments(1);
            }
        };
    }

    public DevCard getCard(DevCardType type) {
        switch (type){
            case MONOPOLY:
                return monopoly;
            case ROAD_BUILD:
                return roadBuilding;
            case YEAR_OF_PLENTY:
                return yearOfPlenty;
            case SOLDIER:
                return soldier;
            case MONUMENT:
                return monument;
        }

        return null;
    }

    public int totalCards(){
        return monopoly.total()
                + monument.total()
                + yearOfPlenty.total()
                + soldier.total()
                + roadBuilding.total();
    }
}
