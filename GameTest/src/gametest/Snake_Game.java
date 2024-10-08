package gametest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Snake_Game extends JPanel implements ActionListener {
    // ---- Start: Constants and Variables Definition ----//
    private final int TILE_SIZE = 25;
    private final int BOARD_SIZE = 600;
    private final int ALL_TILES = (BOARD_SIZE / TILE_SIZE) * (BOARD_SIZE / TILE_SIZE);
    private final int DELAY = 140;

    private final int[] x = new int[ALL_TILES];
    private final int[] y = new int[ALL_TILES];

    private int bodyLength;
    private int foodX, foodY;
    private int score;

    private boolean leftDirection = true;
    private boolean rightDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Random random;

    private JButton startOverButton;
    // ---- End: Constants and Variables Definition ----//

    // ---- Start: Constructor and Initial Setup ----//
    public Snake_Game(int boardWidth, int boardHeight) {
        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
        initBoard();
        initGame();
    }

    private void initBoard() {
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new TAdapter());

        startOverButton = new JButton("Start Over");
        startOverButton.setVisible(false);
        startOverButton.addActionListener(e -> restartGame());
        this.setLayout(null);
        startOverButton.setBounds((BOARD_SIZE / 2) - 50, (BOARD_SIZE / 2) + 30, 100, 30);
        this.add(startOverButton);
    }

    private void initGame() {
        bodyLength = 3;
        score = 0;
        for (int z = 0; z < bodyLength; z++) {
            x[z] = 100 + z * TILE_SIZE; // Start from the right and move left
            y[z] = 100;
        }
        spawnFood();
        timer = new Timer(DELAY, this);
        timer.start();
    }
    // ---- End: Constructor and Initial Setup ----//

    // ---- Start: Getter and Setter Methods ----//
    public int[] getSnakeX() {
        return x;
    }

    public int[] getSnakeY() {
        return y;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    public int getTileSize() {
        return TILE_SIZE;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setLeftDirection(boolean leftDirection) {
        this.leftDirection = leftDirection;
    }

    public void setRightDirection(boolean rightDirection) {
        this.rightDirection = rightDirection;
    }

    public void setUpDirection(boolean upDirection) {
        this.upDirection = upDirection;
    }

    public void setDownDirection(boolean downDirection) {
        this.downDirection = downDirection;
    }
    // ---- End: Getter and Setter Methods ----//

    // ---- Start: Game Loop Methods ----//
    void spawnFood() {
        random = new Random();
        int maxPosition = (BOARD_SIZE / TILE_SIZE);
        foodX = random.nextInt(maxPosition) * TILE_SIZE;
        foodY = random.nextInt(maxPosition) * TILE_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            drawGrid(g);
            drawFood(g);
            drawSnake(g);
            drawScore(g);

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i <= BOARD_SIZE / TILE_SIZE; i++) {
            g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, BOARD_SIZE);
            g.drawLine(0, i * TILE_SIZE, BOARD_SIZE, i * TILE_SIZE);
        }
    }

    private void drawFood(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(foodX, foodY, TILE_SIZE, TILE_SIZE);
    }

    private void drawSnake(Graphics g) {
        for (int z = 0; z < bodyLength; z++) {
            if (z == 0) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.yellow);
            }
            g.fillRect(x[z], y[z], TILE_SIZE, TILE_SIZE);
        }
    }

    private void drawScore(Graphics g) {
        String scoreText = "Score: " + score;
        Font small = new Font("Helvetica", Font.BOLD, 20);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(scoreText, 10, BOARD_SIZE - 10);
    }
    // ---- End: Game Loop Methods ----//

    // ---- Start: Collision and Movement Logic ----//
    private void checkFoodCollision() {
        if ((x[0] == foodX) && (y[0] == foodY)) {
            bodyLength++;
            score++;
            spawnFood();
        }
    }

    public void checkCollision() {
    for (int z = bodyLength; z > 0; z--) {
        // Check if head collides with body segment
        if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
            inGame = false;
        }
    }

    // Check for collisions with the boundaries of the game board
    if (y[0] >= BOARD_SIZE || y[0] < 0 || x[0] >= BOARD_SIZE || x[0] < 0) {
        inGame = false;
    }

    if (!inGame) {
        timer.stop();
    }
}


    public void move() {
        for (int z = bodyLength; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= TILE_SIZE;
        }

        if (rightDirection) {
            x[0] += TILE_SIZE;
        }

        if (upDirection) {
            y[0] -= TILE_SIZE;
        }

        if (downDirection) {
            y[0] += TILE_SIZE;
        }
    }
    // ---- End: Collision and Movement Logic ----//

    // ---- Start: Game Over and Restart Methods ----//
    private void gameOver(Graphics g) {
        String msg = "Game Over";
        String report = "Final Score: " + score;
        Font small = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (BOARD_SIZE - metr.stringWidth(msg)) / 2, BOARD_SIZE / 2);
        g.drawString(report, (BOARD_SIZE - metr.stringWidth(report)) / 2, BOARD_SIZE / 2 + 40);

        startOverButton.setVisible(true);
    }

    private void restartGame() {
        inGame = true;
        startOverButton.setVisible(false);
        initGame();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkFoodCollision();
            checkCollision();
            move();
        }

        repaint();
    }
    // ---- End: Game Over and Restart Methods ----//

    

    // ---- Start: Key Adapter for Controls ----//
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
    // ---- End: Key Adapter for Controls ----//
}

    // ---- End: Key Adapter for Controls ----
