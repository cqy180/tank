package game;

import java.util.Vector;

/**
 * @author 陈青云
 * 敌人坦克
 */
public class FoeTank extends Tank implements Runnable {
    Shot shot = null;
    //获取FoeTank
    Vector<FoeTank> foeTanks = new Vector<>();
    Vector<Shot> shots = new Vector<>();
//    boolean isLive = true;//是否存活

    public FoeTank(int x, int y) {
        super(x, y);
    }

    public void setFoeTanks(Vector<FoeTank> foeTanks) {
        this.foeTanks = foeTanks;
    }

    public boolean isTouchFoeTank() {
        switch (this.getDirect()) {
            case 0:
                for (int i = 0; i < foeTanks.size(); i++) {
                    //取出一个坦克
                    FoeTank foeTank = foeTanks.get(i);
                    if (foeTank != this) {
                        //重叠的情况
                        //方向：上下 x ==> x,x+50 y ==> y,y+60
                        if (foeTank.getDirect() == 0 || foeTank.getDirect() == 2) {
                            //当前坦克的左顶点 x y
                            if (getX() >= foeTank.getX()
                                    && getX() <= foeTank.getX() + 50
                                    && getY() >= foeTank.getY()
                                    && getY() <= foeTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克的右顶点 x+50 y
                            else if (getX() + 50 >= foeTank.getX() &&
                                    getX() + 50 <= foeTank.getX() + 50
                                    && getY() >= foeTank.getY()
                                    && getY() <= foeTank.getY() + 60) {
                                return true;
                            }
                        }
                        //重叠的情况
                        //方向：左右 x ==> x,x+60 y ==> y,y+50
                        if (foeTank.getDirect() == 1 || foeTank.getDirect() == 3) {
                            //当前坦克的左顶点
                            if (getX() >= foeTank.getX()
                                    && getX() <= foeTank.getX() + 60
                                    && getY() >= foeTank.getY()
                                    && getY() <= foeTank.getY() + 50) {
                                return true;
                            }
                            //当前坦克的右顶点
                            else if (getX() + 50 >= foeTank.getX()
                                    && getX() + 50 <= foeTank.getX() + 60
                                    && getY() >= foeTank.getY()
                                    && getY() <= foeTank.getY() + 50) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1://右
                for (int i = 0; i < foeTanks.size(); i++) {
                    FoeTank foeTank = foeTanks.get(i);
                    if (foeTank != this) {
                        //重叠的情况
                        //方向：上下 x ==> x,x+50 y ==> y,y+60
                        if (foeTank.getDirect() == 0 || foeTank.getDirect() == 2) {
                            //当前坦克的上顶点 x+60 y
                            if (getX() + 60 >= foeTank.getX()
                                    && getX() + 60 <= foeTank.getX() + 50
                                    && getY() >= foeTank.getY()
                                    && getY() <= foeTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克的下顶点  x+60 y+50
                            else if (getX() + 60 >= foeTank.getX()
                                    && getX() + 60 <= foeTank.getX() + 50
                                    && getY() + 50 >= foeTank.getY()
                                    && getY() + 50 <= foeTank.getY() + 60) {
                                return true;
                            }
                        }
                        //重叠的情况
                        //方向：左右 x ==> x,x+60 y ==> y,y+50
                        if (foeTank.getDirect() == 1 || foeTank.getDirect() == 3) {
                            //当前坦克的上顶点
                            if (getX() + 60 >= foeTank.getX()
                                    && getX() + 60 <= foeTank.getX() + 60
                                    && getY() >= foeTank.getY()
                                    && getY() <= foeTank.getY() + 50) {
                                return true;
                            }
                            //当前坦克的下顶点
                            else if (getX() + 60 >= foeTank.getX()
                                    && getX() + 60 <= foeTank.getX() + 60
                                    && getY() + 50 >= foeTank.getY()
                                    && getY() + 50 <= foeTank.getY() + 50) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2://下
                for (int i = 0; i < foeTanks.size(); i++) {
                    FoeTank foeTank = foeTanks.get(i);
                    if (foeTank != this) {
                        //重叠的情况
                        //方向：上下 x ==> x,x+50 y ==> y,y+60
                        if (foeTank.getDirect() == 0 || foeTank.getDirect() == 2) {
                            //当前坦克的左顶点 x y+60
                            if (getX() >= foeTank.getX()
                                    && getX() <= foeTank.getX() + 50 &&
                                    getY() + 60 >= foeTank.getY()
                                    && getY() + 60 <= foeTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克的右顶点  x+50 y+60
                            else if (getX() + 50 >= foeTank.getX()
                                    && getX() + 50 <= foeTank.getX() + 50
                                    && getY() + 60 >= foeTank.getY()
                                    && getY() + 60 <= foeTank.getY() + 60) {
                                return true;
                            }
                        }
                        //重叠的情况
                        //方向：左右 x ==> x,x+60 y ==> y,y+50
                        if (foeTank.getDirect() == 1 || foeTank.getDirect() == 3) {
                            //当前坦克的左顶点
                            if (getX() >= foeTank.getX()
                                    && getX() <= foeTank.getX() + 60
                                    && getY() + 60 >= foeTank.getY()
                                    && getY() + 60 <= foeTank.getY() + 50) {
                                return true;
                            }
                            //当前坦克的右顶点
                            else if (getX() + 50 >= foeTank.getX()
                                    && getX() + 50 <= foeTank.getX() + 60
                                    && getY() + 60 >= foeTank.getY()
                                    && getY() + 60 <= foeTank.getY() + 50) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3://左
                for (int i = 0; i < foeTanks.size(); i++) {
                    FoeTank foeTank = foeTanks.get(i);
                    if (foeTank != this) {
                        //重叠的情况
                        //方向：上下 x ==> x,x+50 y ==> y,y+60
                        if (foeTank.getDirect() == 0 || foeTank.getDirect() == 2) {
                            //当前坦克的上顶点 x y
                            if (getX() >= foeTank.getX()
                                    && getX() <= foeTank.getX() + 50
                                    && getY() >= foeTank.getY()
                                    && getY() <= foeTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克的下顶点 x y+50
                            else if (getX() >= foeTank.getX()
                                    && getX() <= foeTank.getX() + 50
                                    && getY() + 50 >= foeTank.getY()
                                    && getY() + 50 <= foeTank.getY() + 60) {
                                return true;
                            }
                        }
                        //重叠的情况
                        //方向：左右 x ==> x,x+60 y ==> y,y+50
                        if (foeTank.getDirect() == 1 || foeTank.getDirect() == 3) {
                            //当前坦克的上顶点
                            if (getX() >= foeTank.getX()
                                    && getX() <= foeTank.getX() + 60
                                    && getY() - 50 >= foeTank.getY()
                                    && getY() - 50 <= foeTank.getY() + 50) {
                                return true;
                            }
                            //当前坦克的下顶点
                            else if (getX() >= foeTank.getX()
                                    && getX() <= foeTank.getX() + 60
                                    && getY() >= foeTank.getY()
                                    && getY() <= foeTank.getY() + 50) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    //让敌人的坦克发射子弹
    public void shotFoeTank() {
        //若面板的子弹数不超过8课
        if (shots.size() > 0) {
            return;
        }
        switch (getDirect()) {
            case 0:
                shot = new Shot(getX() + 25, getY(), getDirect());
                break;
            case 1:
                shot = new Shot(getX() + 60, getY() + 30, getDirect());
                break;
            case 2:
                shot = new Shot(getX() + 25, getY() + 60, getDirect());
                break;
            case 3:
                shot = new Shot(getX(), getY() + 30, getDirect());
                break;
        }
        //将子弹添加到集合
        shots.add(shot);
        new Thread(shot).start();
    }

    //敌人坦克随机的更改方向，并保持运动
    public void move(){
        //让坦克按照当前方向继续移动
        switch (getDirect()) {
            case 0:
                for (int i = 0; i < 40; i++) {
                    if (getY() > -5 && !isTouchFoeTank()) {
                        moveUp();
                    } else {
                        break;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    shotFoeTank();
                }
                break;
            case 1:
                for (int i = 0; i < 40; i++) {
                    if (getX() <= 1000 - 60 -10 && !isTouchFoeTank()) {
                        moveRight();
                    } else {
                        break;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    shotFoeTank();
                }
                break;
            case 2:
                for (int i = 0; i < 40; i++) {
                    if (getY() < 750 -60 -45 && !isTouchFoeTank()) {
                        moveDown();
                    } else {
                        break;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    shotFoeTank();
                }
                break;
            case 3:
                for (int i = 0; i < 40; i++) {
                    if (getX() > 0 && !isTouchFoeTank()) {
                        moveLeft();
                    } else {
                        break;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    shotFoeTank();
                }
                break;
        }
        //然后随机改变坦克的方向
        this.setDirect((int) (Math.random() * 4));
    }

    @Override
    public void run() {
        while (true) {
            //让坦克按照当前方向继续移动
            //并随机的更改方向
            move();
            //发射子弹
            //结束线程
            if (!isLive) {
                break;
            }
        }
    }
}
