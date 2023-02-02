package game;

import java.util.Vector;

/**
 * @author 陈青云
 * 我方坦克
 */
public class MyTank extends Tank {
    Shot shot = null;
    Vector<Shot> shots = new Vector<>();
    public MyTank(int x, int y) {
        super(x, y);
    }

    //完成射击动作
    public void shotMyTank() {
        //若面板的子弹数不超过8课
        if (shots.size() > 8){
            return;
        }
        switch (getDirect()) {
            case 0:
                shot = new Shot(getX() + 25,getY(),getDirect());
                break;
            case 1:
                shot = new Shot(getX() + 60,getY() + 30,getDirect());
                break;
            case 2:
                shot = new Shot(getX() + 25, getY() + 60, getDirect());
                break;
            case 3:
                shot = new Shot(getX(), getY() + 30,getDirect());
                break;
        }
        //将子弹添加到集合
        shots.add(shot);
        new Thread(shot).start();
    }
}
