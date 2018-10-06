package at.htl.bastibot.model;

public class Robot {

    private int x;
    private int y;
    private Direction direction;

    private int gridWidth;
    private int gridHeight;

    //region Constructors
    public Robot(int x, int y, Direction direction, int gridWidth) {
        setX(x);
        setY(y);
        setDirection(direction);
        this.gridWidth = gridWidth;
        gridHeight = 0;
    }

    public Robot(int x, int y, Direction direction, int gridWidth, int gridHeight) {
        setX(x);
        setY(y);
        setDirection(direction);
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    public Robot(int x, int y, int gridWidth) {
        setX(x);
        setY(y);
        setDirection(Direction.SOUTH);
        this.gridWidth = gridWidth;
        gridHeight = gridWidth;
    }

    public Robot(int x, int y, int gridWidth, int gridHeight) {
        setX(x);
        setY(y);
        setDirection(Direction.SOUTH);
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    public Robot(Direction direction, int gridWidth) {
        setDirection(Direction.SOUTH);
        this.gridWidth = gridWidth;
        gridHeight = gridWidth;
    }

    public Robot(Direction direction, int gridWidth, int gridHeight) {
        setDirection(Direction.SOUTH);
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    public Robot(int gridWidth) {
        setDirection(Direction.SOUTH);
        this.gridWidth = gridWidth;
        gridHeight = gridWidth;
    }

    public Robot(int gridWidth, int gridHeight) {
        setDirection(Direction.SOUTH);
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }
    //endregion

    //region Getter and Setter
    public int getX() {
        return x;
    }

    public void setX(int x) {

        if (x >= 0 && x <= gridWidth - 1) {
            this.x = x;
        } else if (x < 0) {
            this.x = gridWidth - 1;
        } else {
            this.x = 0;
        }

    }

    public int getY() {
        return y;
    }

    public void setY(int y) {

        if (y >= 0 && y <= gridHeight - 1) {
            this.y = y;
        } else if (y < 0) {
            this.y = gridHeight - 1;
        } else {
            this.y = 0;
        }

    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    //endregion

    public void stepForward() {

        switch (getDirection()) {

            case NORTH:
                setY(getY() - 1);
                break;

            case EAST:
                setX(getX() + 1);
                break;

            case SOUTH:
                setY(getY() + 1);
                break;

            case WEST:
                setX(getX() - 1);
                break;

        }

    }

    public void rotateLeft() {

        switch (getDirection()) {

            case NORTH:
                setDirection(Direction.WEST);
                break;

            case EAST:
                setDirection(Direction.NORTH);
                break;

            case SOUTH:
                setDirection(Direction.EAST);
                break;

            case WEST:
                setDirection(Direction.SOUTH);
                break;

        }

    }

}
