/*
 * Sebastian Scholl
 */

package at.htl.bastibot.gui;

import at.htl.bastibot.model.Direction;
import at.htl.bastibot.model.Robot;
import processing.core.PApplet;

public class Main extends PApplet {

    //region Member-Variables
    private final boolean fullScreen = true;
    private final int windowWidth = 800;
    private final int windowHeight = 600;

    private final int frameRate = 60;

    private final int gridWidth = 10;
    private final int gridHeight = 10;
    private final int gridColor = color(255);

    private final int backgroundColor = color(209);
    private final int textColor = color(0);

    private final int gameOverBackgoundColor = color(0);
    private final int gameOverTextColor = color(255);

    private final int startScreenBackgroundColor = color(0);
    private final int startScreenTextcolor = color(255);

    private int marginTopBottom = 70;
    private int marginLeftRight = 50;

    private int fieldWith;
    private int fieldHeight;
    private final boolean fieldsAreSqares = true;

    private final char keyUp = 'w';
    private final char keyRight = 'd';
    private final char keyDown = 's';
    private final char keyLeft = 'a';

    private int score;
    private int highscore = 0;
    private int time;

    private int boxX;
    private int boxY;
    private final int boxColor = color(229, 0, 255);

    private Robot robot = new Robot(gridWidth, gridHeight);
    private final int robotColor = color(0, 0, 255);

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

            drawGrid();

            drawBox();

            drawRobot();

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

        String text = "Move a robot on a grid. The goal is to collect as many boxes as possible in 60 seconds.";

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

        textAlign(CENTER, BOTTOM);
        text("Game Over!", width / 2, height / 2);

        textAlign(CENTER, TOP);
        text("Press any key to play again", width / 2, height / 2);

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
        int y = marginTopBottom / 2;

        fill(textColor);
        textSize(Math.min(marginLeftRight, marginTopBottom) / 4);

        textAlign(LEFT, BOTTOM);
        text("Exit [ESC]", marginLeftRight / 10, y);

        textSize(Math.min(marginLeftRight, marginTopBottom) / 2);
        textAlign(CENTER, CENTER);
        text(time + "s", width / 2, y);


        textSize(Math.min(marginLeftRight, marginTopBottom) / 4);
        textAlign(RIGHT, BOTTOM);
        text("Score: " + score, width - marginLeftRight / 10, y);

        textAlign(RIGHT, TOP);
        text("Highscore: " + highscore, width - marginLeftRight / 10, y);

    }

    private void drawGrid() {

        for (int i = 0; i < gridWidth; i++) {

            for (int j = 0; j < gridHeight; j++) {

                int x = marginLeftRight + fieldWith * i;
                int y = marginTopBottom + fieldHeight * j;

                fill(gridColor);
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

        if (keyPressed) {

            char currentKey = (key + "").toLowerCase().charAt(0);

            switch (keyCode) {

                case UP:

                    robot.setDirection(Direction.NORTH);
                    robot.stepForward();
                    delay(70);
                    break;

                case RIGHT:

                    robot.setDirection(Direction.EAST);
                    robot.stepForward();
                    delay(70);
                    break;

                case DOWN:

                    robot.setDirection(Direction.SOUTH);
                    robot.stepForward();
                    delay(70);
                    break;

                case LEFT:

                    robot.setDirection(Direction.WEST);
                    robot.stepForward();
                    delay(70);
                    break;

            }

            switch (currentKey) {

                case keyUp:

                    robot.setDirection(Direction.NORTH);
                    robot.stepForward();
                    delay(70);
                    break;

                case keyRight:

                    robot.setDirection(Direction.EAST);
                    robot.stepForward();
                    delay(70);
                    break;

                case keyDown:

                    robot.setDirection(Direction.SOUTH);
                    robot.stepForward();
                    delay(70);
                    break;

                case keyLeft:

                    robot.setDirection(Direction.WEST);
                    robot.stepForward();
                    delay(70);
                    break;

            }

        }

        keyCode = SHIFT;
        key = '§';

    }
}