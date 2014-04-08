package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by geh on 8-4-14.
 */
public abstract class KwetterGoFrame
{
    private JButton Send;
    private JPanel panel1;
    private JTextField userName;
    private JTextArea kwetBody;

    public KwetterGoFrame()
    {
        JFrame frame = new JFrame("KwetterGoFrame");
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setContentPane(this.panel1);
        frame.setVisible(true);
        Send.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sendMessage(userName.getText(), kwetBody.getText());
            }
        });
    }

    public abstract void sendMessage(String name, String text);
}
