/*
 * Sebastian Scholl
 */

package at.htl.bastibot.gui;

import at.htl.bastibot.model.Robot;
import processing.core.PApplet;

public class Main extends PApplet {

    //region Member-Variables
    private boolean fullScreen = true;
    private int windowWidth = 800;
    private int windowHeight = 600;

    private int frameRate = 60;

    private int gridWidth = 10;
    private int gridHeight = 10;
    private int[][] grid = new int[gridHeight][gridWidth];
    private int gridColor = color(255);

    private int backgroundColor = color(209);
    private int selectedFieldColor = color(255, 0, 0);
    private int textColor = color(0);

    private int gameOverBackgoundColor = color(0);
    private int gameOverTextColor = color(255);

    private int startScreenBackgroundColor = color(0);
    private int startScreenTextcolor = color(255);

    private int marginTopBottom = 70;
    private int marginLeftRight = 50;

    private int fieldWith;
    private int fieldHeight;
    private boolean fieldsAreSqares = true;

    private char keyRotateLeft = 'f';
    private char keyStepForward = 'l';

    private int score;
    private int highscore = 0;
    private int time;

    private int boxX;
    private int boxY;
    private int boxColor = color(229, 0, 255);

    private Robot robot = new Robot(gridWidth, gridHeight);
    private int robotColor = color(0, 0, 255);

    private boolean gameOver = false;
    private boolean wait = false;
    private boolean startScreen = true;
    //endregion

    public static void main(String[] args) {
        PApplet.main("at.htl.bastibot.gui.Main", args);
    }

    public void settings() {

        if (fullScreen) {
            fullScreen();
        } else {
            size(windowWidth, windowHeight);
        }

    }

    public void setup() {

        noCursor();
        background(backgroundColor);
        frameRate(frameRate);
        declareMemberVariables();

        if (width > height && fieldsAreSqares) {

            fieldWith = (height - marginTopBottom * 2) / gridHeight;
            fieldHeight = fieldWith;
            marginLeftRight = (width - fieldWith * gridWidth) / 2;

        } else if (width < height && fieldsAreSqares) {

            fieldWith = (width - marginLeftRight * 2) / gridWidth;
            fieldHeight = fieldWith;
            marginTopBottom = (height - fieldHeight * gridHeight) / 2;

        } else {

            fieldWith = (width - marginLeftRight * 2) / gridWidth;
            fieldHeight = (height - marginTopBottom * 2) / gridHeight;

        }

    }

    public void draw() {

        if (startScreen) {

            startScreen();

        } else if (gameOver) {

            gameOver();

            if (wait) {

                delay(1000);
                wait = false;

            }

        } else {

            interpretPressedKey();

            markSelectedField();

            drawGrid();

            drawRobot();

            drawBox();

            drawText();

            if (frameCount % frameRate == 1) {
                time--;
            }

            if (time == -1) {

                gameOver = true;
                wait = true;

            }

        }

    }

    private void startScreen() {

        String text = "Move a robot on a grid by pressing [" + (keyStepForward + "").toUpperCase() + "] to make a step in the selected direction and [" + (keyRotateLeft + "").toUpperCase() + "] to rotate left. The goal is to collect as many boxes as possible in 60 seconds.";

        background(startScreenBackgroundColor);

        fill(startScreenTextcolor);

        textSize(Math.min(width, height) / 10);
        textAlign(CENTER, BOTTOM);
        text("BastiBot", width / 2, height / 3);

        textSize(Math.min(width, height) / 70);
        textAlign(RIGHT, TOP);
        text("Sebastian Scholl", width - 10, 10);

        textSize(Math.min(width, height) / 50);
        textAlign(CENTER, TOP);
        text(text, width / 3, height / 2, width / 3, height);

        textSize(Math.min(width, height) / 40);
        textAlign(CENTER, BOTTOM);
        text("Press any key to start", width / 2, height - height / 10);

        if (keyPressed || mousePressed) {

            startScreen = false;
            background(backgroundColor);

        }

    }

    private void gameOver() {

        background(gameOverBackgoundColor);

        fill(gameOverTextColor);

        textSize(Math.min(marginLeftRight, marginTopBottom) / 2);
        textAlign(CENTER, CENTER);
        text("Press any key to PLAY AGAIN", width / 2, height / 2);

        textSize(Math.min(marginLeftRight, marginTopBottom) / 4);
        textAlign(LEFT, BOTTOM);
        text("Score: " + score, marginLeftRight / 10, marginTopBottom / 2);

        textAlign(RIGHT, BOTTOM);
        text("Highscore: " + highscore, width - marginLeftRight / 10, marginTopBottom / 2);

        if ((keyPressed || mousePressed) && !wait) {

            gameOver = false;
            background(backgroundColor);
            declareMemberVariables();

        }

    }

    private void declareMemberVariables() {

        time = 60;
        score = 0;
        robot = new Robot(gridWidth, gridHeight);

        do {

            boxX = (int) (Math.random() * gridWidth - 1);
            boxY = (int) (Math.random() * gridHeight - 1);

        } while ((robot.getX() == boxX && robot.getY() == boxY));

    }

    private void drawBox() {

        int boxWidth = fieldWith - Math.min(fieldWith, fieldHeight) / 5;
        int boxHeigth = fieldHeight - Math.min(fieldWith, fieldHeight) / 5;
        int x = marginLeftRight + boxX * fieldWith + Math.min(fieldWith, fieldHeight) / 10;
        int y = marginTopBottom + boxY * fieldHeight + Math.min(fieldWith, fieldHeight) / 10;

        fill(boxColor);
        rect(x, y, boxWidth, boxHeigth);

        if (robot.getX() == boxX && robot.getY() == boxY) {

            score++;

            if (score > highscore) {
                highscore = score;
            }

            while (robot.getX() == boxX && robot.getY() == boxY) {

                boxX = (int) (Math.random() * gridWidth - 1);
                boxY = (int) (Math.random() * gridHeight - 1);

            }

        }

    }

    private void drawText() {

        noStroke();
        fill(backgroundColor);
        rect(0, 0, width, marginTopBottom);

        stroke(0);

        String textStepForward = "Step forward [" + (keyStepForward + "").toUpperCase() + "]";
        String textRotateLeft = "Rotate Left [" + (keyRotateLeft + "").toUpperCase() + "]";
        int y = marginTopBottom / 2;

        fill(textColor);
        textSize(Math.min(marginLeftRight, marginTopBottom) / 4);

        textAlign(LEFT, BOTTOM);
        text(textStepForward, marginLeftRight / 10, y);

        textAlign(LEFT, TOP);
        text(textRotateLeft, marginLeftRight / 10, y);

        textAlign(CENTER, BOTTOM);
        text("Exit [ESC]", width / 2, y);

        textAlign(CENTER, TOP);
        text(time + "s", width / 2, y);

        textAlign(RIGHT, BOTTOM);
        text("Score: " + score, width - marginLeftRight / 10, y);

        textAlign(RIGHT, TOP);
        text("Highscore: " + highscore, width - marginLeftRight / 10, y);

    }

    private void markSelectedField() {

        int x = robot.getX();
        int y = robot.getY();

        switch (robot.getDirection()) {

            case NORTH:
                y -= 1;
                break;

            case EAST:
                x += 1;
                break;

            case SOUTH:
                y += 1;
                break;

            case WEST:
                x -= 1;
                break;

        }

        if (x < 0) {
            x = gridWidth - 1;
        } else if (x >= gridWidth) {
            x = 0;
        }

        if (y < 0) {
            y = gridHeight - 1;
        } else if (y >= gridHeight) {
            y = 0;
        }

        declareGrid();
        grid[x][y] = selectedFieldColor;

    }

    private void declareGrid() {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = gridColor;
            }
        }

    }

    private void drawGrid() {

        for (int i = 0; i < gridWidth; i++) {

            for (int j = 0; j < gridHeight; j++) {

                int x = marginLeftRight + fieldWith * i;
                int y = marginTopBottom + fieldHeight * j;

                fill(grid[i][j]);
                rect(x, y, fieldWith, fieldHeight);

            }

        }

    }

    private void drawRobot() {

        int radius = Math.min(fieldWith, fieldHeight) - Math.min(fieldWith, fieldHeight) / 10;
        int x = marginLeftRight + robot.getX() * fieldWith + fieldWith / 2;
        int y = marginTopBottom + robot.getY() * fieldHeight + fieldHeight / 2;

        fill(robotColor);
        ellipse(x, y, radius, radius);

    }

    private void interpretPressedKey() {

        if (keyRotateLeft == (key + "").toLowerCase().charAt(0) && keyPressed) {
            robot.rotateLeft();
        } else if (keyStepForward == (key + "").toLowerCase().charAt(0) && keyPressed) {
            robot.stepForward();
        }

        key = '§';

    }
}