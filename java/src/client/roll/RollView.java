package client.roll;

import client.base.OverlayView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Implementation for the roll view, which allows the user to roll the dice
 */
@SuppressWarnings("serial") public class RollView extends OverlayView implements IRollView
{

    private final int LABEL_TEXT_SIZE = 20;
    private final int BUTTON_TEXT_SIZE = 28;
    private final int BORDER_WIDTH = 10;
    java.util.Timer timer;
    private JLabel label;
    private JLabel imageLabel;
    private JButton rollButton;
    private JPanel buttonPanel;
    private Logger LOGGER = Logger.getLogger(RollView.class.getName());
    private ActionListener actionListener = new ActionListener()
    {
        @Override public void actionPerformed(ActionEvent e)
        {

            if (e.getSource() == rollButton)
            {

                closeModal();
                timer.cancel();
                getController().rollDice();
            }
        }
    };

    public RollView()
    {

        this.setOpaque(true);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));

        label = new JLabel("Roll View");
        Font labelFont = label.getFont();
        labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
        label.setFont(labelFont);
        this.add(label, BorderLayout.NORTH);

        try
        {
            BufferedImage diceImg = ImageIO.read(new File("images/misc/dice.jpg"));
            Image smallDiceImg = diceImg.getScaledInstance(300, 224, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(smallDiceImg));
            this.add(imageLabel, BorderLayout.CENTER);
        } catch (IOException ex)
        {
            //handle exception
        }

        rollButton = new JButton("Roll!");
        rollButton.addActionListener(actionListener);
        Font buttonFont = rollButton.getFont();
        buttonFont = buttonFont.deriveFont(buttonFont.getStyle(), BUTTON_TEXT_SIZE);
        rollButton.setFont(buttonFont);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(rollButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override public IRollController getController()
    {

        return (IRollController) super.getController();
    }

    @Override public void setMessage(String message)
    {
        label.setText(message);
    }

    @Override public void showModal()
    {
        LOGGER.info("Showing Roll Dice Modal");
        super.showModal();
        RollTask rollTask = new RollTask(this);

        timer = new java.util.Timer();
        timer.schedule(rollTask, 3000);
    }
}


