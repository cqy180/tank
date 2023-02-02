package game;

import java.io.Serializable;

/**
 * @author 陈青云
 */
public class Tank implements Serializable {
    private int x;
    private int y;
    private int direct;//坦克方向
    private int speed = 1;//坦克的速度
    public boolean isLive = true;

    //上右下左的移动方法
    public void moveUp() {
        //判断坦克是否出边界
        if (!(y <= -5)) {
            y -= speed;
        }
    }

    public void moveRight() {
        if (!(x >= 1000 - 60 - 10)){
            x += speed;
        }
    }

    public void moveDown() {
        if (!(y >= 750 - 60 - 45)){
            y += speed;
        }
    }

    public void moveLeft() {
        if (!(x <= 0)){
            x -= speed;
        }
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
