package client.maritime;

import client.base.PanelView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Implementation for the maritime trade view, which displays the "Maritime Trade" button
 */
@SuppressWarnings("serial") public class MaritimeTradeView extends PanelView implements IMaritimeTradeView
{

    private JButton button;
    private ActionListener buttonListener = new ActionListener()
    {
        @Override public void actionPerformed(ActionEvent e)
        {

            getController().startTrade();
        }
    };

    public MaritimeTradeView()
    {

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        Font font = new JButton().getFont();
        Font newFont = font.deriveFont(font.getStyle(), 20);

        button = new JButton("Maritime Trade");
        button.setFont(newFont);
        button.addActionListener(buttonListener);

        this.add(button);
    }

    @Override public IMaritimeTradeController getController()
    {
        return (IMaritimeTradeController) super.getController();
    }

    @Override public void enableMaritimeTrade(boolean value)
    {

        button.setEnabled(value);
    }

}


