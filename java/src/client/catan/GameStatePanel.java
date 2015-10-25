package client.catan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.*;
import shared.definitions.CatanColor;
import client.base.IAction;
import client.data.PlayerInfo;
import client.facade.ClientFacade;


@SuppressWarnings("serial")
public class GameStatePanel extends JPanel
{
	private JButton button;
	
	public GameStatePanel()
	{
		this.setLayout(new FlowLayout());
		this.setBackground(Color.white);
		this.setOpaque(true);
		
		button = new JButton();
		
		Font font = button.getFont();
		Font newFont = font.deriveFont(font.getStyle(), 20);
		button.setFont(newFont);
		
		button.setPreferredSize(new Dimension(400, 50));
		
		this.add(button);
		
		updateGameState("Waiting for other Players", false);
	}
	
	public void updateGameState(String stateMessage, boolean enable)
	{
		button.setText(stateMessage);
		button.setEnabled(enable);
		
		PlayerInfo playerInfo = ClientFacade.getInstance().getClientPlayer();
		
		if (playerInfo != null)
		{
			CatanColor color = playerInfo.getColor();
			//Color playerColor = Color.FromName(color.toString());
			Color playerColor = new Color(0); 
			try {
			
			    Field field = Class.forName("java.awt.Color").getField(color.toString());
			    playerColor = (Color)field.get(null);
			} 
			catch (Exception e) 
			{
			    color = null; // Not defined
			}
			
			
			button.setBorder(BorderFactory.createLineBorder(playerColor, 3));
		
		}
		
	}
	
	public void setButtonAction(final IAction action)
	{
		ActionListener[] listeners = button.getActionListeners();
		for(ActionListener listener : listeners) {
			button.removeActionListener(listener);
		}
		
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				action.execute();
			}
		};
		button.addActionListener(actionListener);
	}
}

