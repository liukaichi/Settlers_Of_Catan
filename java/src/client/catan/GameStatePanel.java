package client.catan;

import client.base.IAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

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
		//this.setBackground(Color.white);
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
			CatanColor catanColor = playerInfo.getColor();
			String playerColor = catanColor.toString();
			Color color;
			
			switch (playerColor)
            {
            case "YELLOW":
            	color = new Color(253, 228, 123);
            	break;
            	
            case "BLUE":
            	color = new Color(111, 183, 246);
            	break;
            	
            case "PURPLE":
            	color = new Color(164, 148, 215);
            	break;
            	
            case "PUCE":
            	color = new Color(215, 163, 176);
            	break;
            	
            case "BROWN":
            	color = new Color(165, 148, 118);
            	break;

            case "ORANGE":
            	color = new Color(255, 165, 0);
            	break;
            	
            case "RED":
            	color = new Color(227, 66, 52);
            	break;
            	
            case "GREEN":
            	color = new Color(109, 192, 102);
            	break;
            	
            case "WHITE":
            	color = new Color(255, 255, 255);
            	break;
            	
            default:
            	color = new Color(0, 0, 0);	
            	break;
            }
			//button.setBorder(BorderFactory.createLineBorder(color, 3));
			button.setUI((ButtonUI) BasicButtonUI.createUI(button));
			button.setBackground(color);
			button.setContentAreaFilled(true);
			button.setOpaque(true);
			this.repaint();
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

