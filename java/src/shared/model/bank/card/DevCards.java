package shared.model.bank.card;

import shared.definitions.DevCardType;
import shared.definitions.exceptions.CatanException;

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
            public void playAction() {
                //name 1 resource
                //ALL players give all of that resource
            }
        };

        roadBuilding = new DevCard(DevCardType.ROAD_BUILD) {
            @Override
            public void playAction() {
                // Player can build 2 roads
            }
        };

        yearOfPlenty = new DevCard(DevCardType.YEAR_OF_PLENTY) {
            @Override
            public void playAction() {
                //take 2 resources from supply
            }
        };

        soldier = new DevCard(DevCardType.SOLDIER) {
            @Override
            public void playAction() {
                //move robber
                //knight + 1
            }
        };

        monument = new DevCard(DevCardType.MONUMENT) {
            @Override
            public void playAction() {
                //victorypoint + 1
                // but only at the end
            }
        };
    }

    public DevCard getCard(DevCardType type) throws CatanException {
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

        throw new CatanException();
    }
}
