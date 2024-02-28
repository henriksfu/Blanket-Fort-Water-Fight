package Model;

public class Fort {
    int startX;
    int startY;
    char[][] shape;
    
    public Fort(int x, int y, char[][] shape) {
        this.startX = x;
        this.startY = y;
        this.shape = shape;
    }  

    public int getX() {
        return startX;
    }

    public int getY() {
        return startY;
    }

    public char[][] getShape() {
        return shape;
    }

    public void setX(int startX) {
        this.startX = startX;
    }

    public void setY(int startY) {
        this.startY = startY;
    }

    public void setShape(char[][] shape) {
        this.shape = shape;
    }


}
