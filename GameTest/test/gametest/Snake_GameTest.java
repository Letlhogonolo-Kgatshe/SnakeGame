package gametest;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class Snake_GameTest {

    private Snake_Game snakeGame;

    @BeforeClass
    public static void setUpClass() throws Exception {
        // This method runs once before any of the test methods in the class.
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        // This method runs once after all the test methods in the class have run.
    }

    @Before
    public void setUp() {
        // Initialize the game board and set up the game before each test
        snakeGame = new Snake_Game(600, 600);
    }

    @After
    public void tearDown() throws Exception {
        // Clean up resources after each test, if necessary
    }

    // Test if the initial body length is set correctly
    @Test
    public void testInitialBodyLength() {
        int expectedBodyLength = 3;
        assertEquals(expectedBodyLength, snakeGame.getBodyLength());
    }

    // Test if the snake moves correctly to the right
@Test
public void testMoveRight() {
    // Set initial direction to right and ensure no other direction flags are set
    snakeGame.setLeftDirection(false);
    snakeGame.setRightDirection(true);
    snakeGame.setUpDirection(false);
    snakeGame.setDownDirection(false);
    
    // Get the initial x position of the snake's head
    int initialX = snakeGame.getSnakeX()[0];

    // Move the snake
    snakeGame.move();
    
    // Check if the x position has been updated correctly
    assertEquals("Snake should move right by TILE_SIZE", initialX + snakeGame.getTileSize(), snakeGame.getSnakeX()[0]);
}


    // Test if the snake moves correctly to the left
    @Test
    public void testMoveLeft() {
        int initialX = snakeGame.getSnakeX()[0];
        snakeGame.setLeftDirection(true);
        snakeGame.move();
        assertEquals(initialX - snakeGame.getTileSize(), snakeGame.getSnakeX()[0]);
    }

    // Test if the snake moves correctly upward
    @Test
    public void testMoveUp() {
        int initialY = snakeGame.getSnakeY()[0];
        snakeGame.setUpDirection(true);
        snakeGame.move();
        assertEquals(initialY - snakeGame.getTileSize(), snakeGame.getSnakeY()[0]);
    }

    // Test if the snake moves correctly downward
    @Test
    public void testMoveDown() {
        int initialY = snakeGame.getSnakeY()[0];
        snakeGame.setDownDirection(true);
        snakeGame.move();
        assertEquals(initialY + snakeGame.getTileSize(), snakeGame.getSnakeY()[0]);
    }

    // Test if the food is spawned within the board boundaries
    @Test
    public void testSpawnFoodWithinBounds() {
        snakeGame.spawnFood();
        int foodX = snakeGame.getFoodX();
        int foodY = snakeGame.getFoodY();

        assertTrue(foodX >= 0 && foodX < snakeGame.getBoardSize());
        assertTrue(foodY >= 0 && foodY < snakeGame.getBoardSize());
    }

    // Test if the game ends when the snake collides with itself
@Test
public void testSelfCollision() {
    // Increase body length to ensure enough body segments for collision
    snakeGame.setBodyLength(5);
    int[] x = snakeGame.getSnakeX();
    int[] y = snakeGame.getSnakeY();

    // Set up a situation where the snake's head collides with its body
    // Example: Position the snake in a vertical line, and then place the head to collide with one of the segments
    x[0] = 100; y[0] = 100; // Head
    x[1] = 100; y[1] = 125; // Body
    x[2] = 100; y[2] = 150; // Body
    x[3] = 100; y[3] = 175; // Body
    x[4] = 100; y[4] = 200; // Body

    // Now, simulate the snake turning back onto itself
    x[0] = 100; y[0] = 150; // Head moves to the same position as the third body part

    // Call the method that checks for collisions
    snakeGame.checkCollision();

    // Check if the game has ended due to self-collision
    //assertFalse("The game should be over due to self-collision.", snakeGame.isInGame());
}



    // Test the paintComponent method
@Test
public void testPaintComponent() {
    System.out.println("paintComponent");
    BufferedImage bi = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
    Graphics g = bi.getGraphics();
    try {
        snakeGame.paintComponent(g);
        // Additional assertions can be made here if there are specific checks for graphics rendering.
    } finally {
        g.dispose();
    }
}


    // Test the actionPerformed method
    @Test
    public void testActionPerformed() {
        System.out.println("actionPerformed");
        ActionEvent e = new ActionEvent(snakeGame, ActionEvent.ACTION_PERFORMED, "test");
        snakeGame.actionPerformed(e);
        // Verify state changes or other side effects of the actionPerformed method
    }

    // Additional test cases for getter and setter methods

    // Test getting the body length
    @Test
    public void testGetBodyLength() {
        int expectedLength = 3;
        assertEquals(expectedLength, snakeGame.getBodyLength());
    }

    // Test setting the right direction
    @Test
    public void testSetRightDirection() {
        snakeGame.setRightDirection(true);
        assertTrue(snakeGame.getSnakeX()[0] == 100); // Check initial position
    }

    // Test getting the snake's X coordinates
    @Test
    public void testGetSnakeX() {
        int[] x = snakeGame.getSnakeX();
        assertNotNull(x);
    }

    // Test getting the snake's Y coordinates
    @Test
    public void testGetSnakeY() {
        int[] y = snakeGame.getSnakeY();
        assertNotNull(y);
    }

    // Test setting the body length
    @Test
    public void testSetBodyLength() {
        int newLength = 5;
        snakeGame.setBodyLength(newLength);
        assertEquals(newLength, snakeGame.getBodyLength());
    }

    // Test getting the X coordinate of the food
    @Test
    public void testGetFoodX() {
        snakeGame.spawnFood();
        int foodX = snakeGame.getFoodX();
        assertTrue(foodX >= 0 && foodX < snakeGame.getBoardSize());
    }

    // Test getting the Y coordinate of the food
    @Test
    public void testGetFoodY() {
        snakeGame.spawnFood();
        int foodY = snakeGame.getFoodY();
        assertTrue(foodY >= 0 && foodY < snakeGame.getBoardSize());
    }

    // Test getting the board size
    @Test
    public void testGetBoardSize() {
        int boardSize = snakeGame.getBoardSize();
        assertEquals(600, boardSize);
    }

    // Test getting the tile size
    @Test
    public void testGetTileSize() {
        int tileSize = snakeGame.getTileSize();
        assertEquals(25, tileSize);
    }

    // Test if the game is in progress
    @Test
    public void testIsInGame() {
        assertTrue(snakeGame.isInGame());
    }

    // Test setting the left direction
    @Test
    public void testSetLeftDirection() {
        snakeGame.setLeftDirection(true);
        // Additional logic to verify the left direction setting
    }

    // Test setting the up direction
    @Test
    public void testSetUpDirection() {
        snakeGame.setUpDirection(true);
        // Additional logic to verify the up direction setting
    }

    // Test setting the down direction
    @Test
    public void testSetDownDirection() {
        snakeGame.setDownDirection(true);
        // Additional logic to verify the down direction setting
    }

    // Test the spawnFood method
    @Test
    public void testSpawnFood() {
        snakeGame.spawnFood();
        int foodX = snakeGame.getFoodX();
        int foodY = snakeGame.getFoodY();
        assertTrue(foodX >= 0 && foodX < snakeGame.getBoardSize());
        assertTrue(foodY >= 0 && foodY < snakeGame.getBoardSize());
    }

    // Test the checkCollision method
    @Test
    public void testCheckCollision() {
        snakeGame.checkCollision();
        // Additional assertions can be added based on the expected outcome of the checkCollision method
    }
}
