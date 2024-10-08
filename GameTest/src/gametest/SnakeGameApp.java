package gametest;

import javax.swing.JFrame;

public class SnakeGameApp {

    public static void main(String[] args) {
        // Set the size of the game board (the area where the game will be played)
        int boardWidth = 600; // The board is 600 pixels wide
        int boardHeight = boardWidth; // The board is square, so height is the same as width

        // Create a window (frame) to display the game
        JFrame frame = new JFrame("Snake Game"); // The window is titled "Snake Game"
        frame.setSize(boardWidth, boardHeight); // Set the size of the window to match the board size
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setResizable(false); // Prevent the user from resizing the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the window is closed

        // Create the game itself and add it to the window
        Snake_Game snakeGame = new Snake_Game(boardWidth, boardHeight); // Create a new instance of the Snake_Game class
        frame.add(snakeGame); // Add the game to the window so it can be displayed

        // Make the window visible to the user
        frame.setVisible(true); // Show the window on the screen
    }
}
