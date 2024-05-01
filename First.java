import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class First {
    private static JTextField textField;

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setTitle("Table Tennis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(600, 500);
        frame.getContentPane().setBackground(new Color(135, 206, 235));
        frame.setLayout(null); // Removed getContentPane() as it's not necessary when setting layout

        JLabel lblNewLabel = new JLabel("Name:");
        lblNewLabel.setFont(new Font("Footlight MT Light", Font.BOLD | Font.ITALIC, 29));
        lblNewLabel.setBounds(143, 142, 102, 49);
        frame.add(lblNewLabel);

        textField = new JTextField();
        textField.setBorder(new LineBorder(Color.BLACK, 3, true));
        textField.setBackground(new Color(255, 255, 255));
        textField.setFont(new Font("Footlight MT Light", Font.BOLD | Font.ITALIC, 29));
        textField.setBounds(281, 142, 206, 49);
        frame.add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("PLAY");
        btnNewButton.setBorder(new LineBorder(new Color(112, 128, 144), 3, true));
        btnNewButton.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 27));
        btnNewButton.setBounds(200, 340, 144, 58);
        frame.add(btnNewButton);

        JLabel lblNewLabel_2 = new JLabel("WELCOME");
        lblNewLabel_2.setForeground(new Color(0, 0, 205));
        lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 40));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(143, 29, 268, 58);
        frame.add(lblNewLabel_2);

        // ActionListener for the PLAY button
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playerName = textField.getText();
                // Close the current frame
                frame.dispose();
                // Open the game frame
                JFrame gameFrame = new JFrame("Ping Pong Game");
                PingPongGame game = new PingPongGame(playerName);
                gameFrame.add(game);
                gameFrame.setSize(800, 600);
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setVisible(true);
            }
        });

        frame.setVisible(true); // Set frame to visible after adding all components
    }
}
