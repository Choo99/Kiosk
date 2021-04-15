package tcpKioskClient;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JavaApplication116 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new JavaApplication116().createAndShowUI();
            }
        });
    }

    private void createAndShowUI() {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents(frame);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private void addLabel(JPanel panel) {
    	JLabel _lbl = new JLabel("Label");//make label and assign text in 1 line
        
        JPanel pane = new JPanel();
        JButton button2 = new JButton("Add label");
        pane.add(_lbl);//add label we made
        pane.add(button2);
        panel.add(pane);

    }
    private void initComponents(final JFrame frame) {
        JPanel panel = new JPanel(new FlowLayout());
        JButton button = new JButton("Add label");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
               // panel.revalidate();
                //panel.repaint();
            	addLabel(panel);
                frame.pack();//so our frame resizes to compensate for new components
            }
        });
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(button, BorderLayout.SOUTH);
    }
}
