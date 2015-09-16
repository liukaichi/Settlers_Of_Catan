package shared.model.card;

import shared.definitions.DevCardType;

public abstract class DevCard
{
	private int amount;
	private DevCardType type;
	
	public int getAmount(){

		return 0;
	}
	public abstract void playAction(DevCardType type);
}