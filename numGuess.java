import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class numGuess extends JFrame{
   
    private int targetNumber;
    private int attemptsLeft;
    private int maxAttempts = 7;
    private int score = 0;

   
    private JLabel instructionLabel;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JButton restartButton;
    private JProgressBar attemptBar;
    private JPanel mainPanel;

    public numGuess(){
        
        setTitle("The Number Guessing Game");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setResizable(false);

      
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 244, 248));


        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.setOpaque(false);

        instructionLabel = new JLabel("Guess a secret number between 1 and 100!", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        instructionLabel.setForeground(new Color(44, 62, 80));

        scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        scoreLabel.setForeground(new Color(41, 128, 185));

        topPanel.add(instructionLabel);
        topPanel.add(scoreLabel);

     
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setOpaque(false);

        guessField = new JTextField(8);
        guessField.setFont(new Font("SansSerif", Font.BOLD, 16));
        guessField.setHorizontalAlignment(JTextField.CENTER);

        guessButton = new JButton("Submit Guess");
        guessButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        guessButton.setBackground(new Color(46, 204, 113));
        guessButton.setForeground(Color.WHITE);
        guessButton.setFocusPainted(false);

        restartButton = new JButton("Play Again");
        restartButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        restartButton.setBackground(new Color(52, 152, 219));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.setEnabled(false);

        centerPanel.add(guessField);
        centerPanel.add(guessButton);
        centerPanel.add(restartButton);

    
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        bottomPanel.setOpaque(false);

        statusLabel = new JLabel("Take your first guess!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        statusLabel.setForeground(new Color(127, 140, 141));

        attemptBar = new JProgressBar(0, maxAttempts);
        attemptBar.setValue(maxAttempts);
        attemptBar.setStringPainted(true);
        attemptBar.setForeground(new Color(46, 204, 113));

        bottomPanel.add(statusLabel);
        bottomPanel.add(attemptBar);

     
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

   
        resetGame();

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
    }

    private void handleGuess() {
        try {
            int userGuess = Integer.parseInt(guessField.getText().trim());
            attemptsLeft--;

           
            attemptBar.setValue(attemptsLeft);
            attemptBar.setString("Attempts Left: " + attemptsLeft + "/" + maxAttempts);

           
            if (attemptsLeft <= 2) {
                attemptBar.setForeground(new Color(231, 76, 60)); // Red when low
            } 
            else if (attemptsLeft <= 4) {
                attemptBar.setForeground(new Color(241, 196, 15)); // Yellow when mid
            }

          
            if (userGuess == targetNumber) {
                score += 10;
                scoreLabel.setText("Score: " + score);
                statusLabel.setText("🎉 Correct! You guessed it!");
                statusLabel.setForeground(new Color(39, 174, 96));
                endGame();
            } 
            else if (attemptsLeft == 0){
                statusLabel.setText("❌ Game Over! The number was " + targetNumber);
                statusLabel.setForeground(new Color(192, 57, 43));
                endGame();
            } 
            else if (userGuess < targetNumber){
                statusLabel.setText("📉 Too Low! Try a higher number.");
                statusLabel.setForeground(new Color(211, 84, 0));
            } 
            else {
                statusLabel.setText("📈 Too High! Try a lower number.");
                statusLabel.setForeground(new Color(211, 84, 0));
            }

            guessField.setText("");
            guessField.requestFocus();

        } catch (NumberFormatException ex) {
            statusLabel.setText("⚠️ Please enter a valid number!");
            statusLabel.setForeground(new Color(192, 57, 43));
        }
    }

    private void endGame(){
        guessField.setEnabled(false);
        guessButton.setEnabled(false);
        restartButton.setEnabled(true);
    }

    private void resetGame(){
        Random rand = new Random();
        targetNumber = rand.nextInt(100) + 1;
        attemptsLeft = maxAttempts;

        
        attemptBar.setValue(maxAttempts);
        attemptBar.setForeground(new Color(46, 204, 113));
        attemptBar.setString("Attempts Left: " + maxAttempts + "/" + maxAttempts);

        statusLabel.setText("Take your first guess!");
        statusLabel.setForeground(new Color(127, 140, 141));

        guessField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
        restartButton.setEnabled(false);
        guessField.requestFocus();
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new numGuess().setVisible(true);
            }
        });
    }
}