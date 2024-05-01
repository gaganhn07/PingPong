import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class PingPongGame extends JPanel implements MouseMotionListener, ActionListener, MouseListener {
    private int paddle1Y = 200;
    private int paddle2Y = 200;
    private int ballX = 300;
    private int ballY = 200;
    private int ballXSpeed = -6; // Increased ball speed
    private int ballYSpeed = 6; // Increased ball speed
    private float paddleSpeed = (float) 4.0; // Reduced paddle speed for Player 2
    private Timer timer;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameOver = false;
    private String playerName; // Declaring playerName here

    // Default constructor
    public PingPongGame() {
        timer = new Timer(10, this);
        timer.start();
        addMouseMotionListener(this);
        addMouseListener(this); // Registering the panel as a MouseListener
    }

    // Constructor to accept player's name
    public PingPongGame(String playerName) {
        this.playerName = playerName;
        timer = new Timer(10, this);
        timer.start();
        addMouseMotionListener(this);
        addMouseListener(this); // Registering the panel as a MouseListener
    }
 

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.fillRect(20, paddle1Y, 10, 80); // Paddle 1
        g.fillRect(getWidth() - 30, paddle2Y, 10, 80); // Paddle 2

        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, 20, 20); // Ball

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString(playerName + ": " + scoreP1, 50, 50);
        g.drawString("Player 2: " + scoreP2, getWidth() - 220, 50);

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", getWidth() / 2 - 120, getHeight() / 2 - 20);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Click to play again", getWidth() / 2 - 100, getHeight() / 2 + 20);
            g.drawString("Go Back", 300, 450); // Add "Go Back" button text
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            ballX += ballXSpeed;
            ballY += ballYSpeed;

            if (ballY <= 0 || ballY >= getHeight() - 20) {
                ballYSpeed = -ballYSpeed;
            }

            // Paddle and Ball collision
            if (new Rectangle(20, paddle1Y, 10, 80).intersects(new Rectangle(ballX, ballY, 20, 20)) ||
                    new Rectangle(getWidth() - 30, paddle2Y, 10, 80).intersects(new Rectangle(ballX, ballY, 20, 20))) {
                ballXSpeed = -ballXSpeed;
            }

            // Ball goes past player 2's paddle
            if (ballX <= 0) {
                scoreP2++;
                if (scoreP2 >= 5) { // Check if scoreP2 is 5 or greater
                    gameOver = true;
                } else {
                    resetBall();
                }
            } else if (ballX >= getWidth() - 20) { // Ball goes past player 1's paddle
                scoreP1++;
                if (scoreP1 >= 5) { // Check if scoreP1 is 5 or greater
                    gameOver = true;
                } else {
                    resetBall();
                }
            }

            // AI for Player 2
            int paddleCenter = paddle2Y + 40;
            if (paddleCenter < ballY && paddle2Y < getHeight() - 80) {
                paddle2Y += paddleSpeed;
            } else if (paddleCenter > ballY && paddle2Y > 0) {
                paddle2Y -= paddleSpeed;
            }

            repaint();
        }
    }

    private void resetBall() {
        ballX = getWidth() / 2;
        ballY = getHeight() / 2;
        ballXSpeed = -ballXSpeed;
    }

    public void mouseMoved(MouseEvent e) {
        int mouseY = e.getY();
        if (mouseY >= 0 && mouseY <= getHeight() - 80) {
            paddle1Y = mouseY;
        }
    }

    public void mouseDragged(MouseEvent e) {}

    // Implement mouseClicked method
    public void mouseClicked(MouseEvent e) {
        if (gameOver) {
            // Check if the click is within the "Go Back" button area
            if (e.getX() >= 200 && e.getX() <= 500 && e.getY() >= 400 && e.getY() <= 450) {
                // Close the current frame
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.dispose();
                // Open the first frame again
                JFrame firstFrame = new JFrame();
                firstFrame.getContentPane().setFont(new Font("Franklin Gothic Demi", Font.BOLD | Font.ITALIC, 29));
                firstFrame.setTitle("Table Tennis");
                firstFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                firstFrame.setSize(600, 500);
                firstFrame.setVisible(true);

                ImageIcon image = new ImageIcon("ping.jpg");
                firstFrame.setIconImage(image.getImage());
                firstFrame.getContentPane().setBackground(new Color(135, 206, 235));
                firstFrame.getContentPane().setLayout(null);

                JLabel lblNewLabel = new JLabel("Name :");
                lblNewLabel.setFont(new Font("Footlight MT Light", Font.BOLD | Font.ITALIC, 29));
                lblNewLabel.setBounds(143, 142, 102, 49);
                firstFrame.getContentPane().add(lblNewLabel);

                JTextField textField = new JTextField();
                textField.setBorder(new LineBorder(Color.BLACK, 3, true));
                textField.setBackground(new Color(255, 255, 255));
                textField.setFont(new Font("Footlight MT Light", Font.BOLD | Font.ITALIC, 29));
                textField.setBounds(281, 142, 206, 49);
                firstFrame.getContentPane().add(textField);
                textField.setColumns(10);

                JButton btnNewButton = new JButton("PLAY");
                btnNewButton.setBorder(new LineBorder(new Color(112, 128, 144), 3, true));
                btnNewButton.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 27));
                btnNewButton.setBounds(200, 340, 144, 58);
                firstFrame.getContentPane().add(btnNewButton);

                JLabel lblNewLabel_2 = new JLabel("WELCOME");
                lblNewLabel_2.setForeground(new Color(0, 0, 205));
                lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 40));
                lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
                lblNewLabel_2.setBounds(143, 29, 268, 58);
                firstFrame.getContentPane().add(lblNewLabel_2);

                // ActionListener for the PLAY button
                btnNewButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String playerName = textField.getText();
                        // Close the current frame
                        firstFrame.dispose();
                        // Open the game frame
                        JFrame gameFrame = new JFrame("Ping Pong Game");
                        PingPongGame game = new PingPongGame(playerName);
                        gameFrame.add(game);
                        gameFrame.setResizable(false);
                        gameFrame.setSize(800, 600);
                        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        gameFrame.setVisible(true);
                    }
                });
            } else {
                scoreP1 = 0;
                scoreP2 = 0;
                resetBall();
                gameOver = false;
                repaint();
            }
        }
    }

    // Unused mouse listener methods
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ping Pong Game");
        PingPongGame game = new PingPongGame();
        frame.add(game);
        frame.setResizable(false);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
} 