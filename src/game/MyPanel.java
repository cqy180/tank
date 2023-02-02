package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @author 陈青云
 * 坦克大战的绘图区域
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    MyTank myTank;
    //定义敌人的坦克
    Vector<FoeTank> foeTanks = new Vector<>();
    //创建node的vector集合
    Vector<Node> nodes = new Vector<>();
    int foeTankSize = 5;//初始化敌人坦克数量
    //定义爆炸
    Vector<Bomb> bombs = new Vector<>();
    //定义三张爆炸图片
    Image image1;
    Image image2;
    Image image3;

    public MyPanel(String key) {
        //判断gameDate文件是否存在
        //若不存在，则将key赋值为1.强行开启新游戏
        File file = new File(Recorder.getFilePath());
        if (!file.exists()){
            key = "1";
            System.out.println("没有上一局记录，已开启新游戏");
        }
        //初始化我方坦克
        myTank = new MyTank(500, 370);
        myTank.setSpeed(5);

        switch (key) {
            case "1":
                //初始化敌人坦克
                for (int i = 0; i < foeTankSize; i++) {
//            FoeTank foeTank = new FoeTank((100 * (i + 1)), 0);
                    //在300,200区域内随机生成敌方坦克
                    FoeTank foeTank = new FoeTank((int) (Math.random() * 300), (int) (Math.random() * 200));
                    foeTank.setSpeed(3);
                    foeTank.setDirect(2);
                    //***将forTanks 设置给foeTank 对象
                    foeTank.setFoeTanks(foeTanks);
                    //将forTanks设置给Recorder对象
                    Recorder.setFoeTanks(foeTanks);
                    //启动FoeTank
                    new Thread(foeTank).start();
                    //给foeTank加入一颗子弹
                    for (int j = 0; j < foeTank.shots.size(); j++) {
                        Shot shot = new Shot(foeTank.getX() + 25, foeTank.getY() + 60, foeTank.getDirect());
                        foeTank.shots.add(shot);
                        new Thread(shot).start();
                    }
//            Shot shot = new Shot(foeTank.getX() + 25, foeTank.getY() + 60, foeTank.getDirect());
//            //加入到子弹集合
//            foeTank.shots.add(shot);
                    //启动short线程
//            new Thread(shot).start();
                    foeTanks.add(foeTank);
                }
                break;
            case "2"://继续上局游戏
                //向node集合传入上局敌人坦克的数据
                nodes = Recorder.getNodeAndFoeTankNum();
                //初始化敌人坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //在300,200区域内随机生成敌方坦克
                    FoeTank foeTank = new FoeTank(node.getX(), node.getY());
                    foeTank.setSpeed(3);
                    foeTank.setDirect(node.getDirect());
                    //***将forTanks 设置给foeTank 对象
                    foeTank.setFoeTanks(foeTanks);
                    //将forTanks设置给Recorder对象
                    Recorder.setFoeTanks(foeTanks);
                    //启动FoeTank
                    new Thread(foeTank).start();
                    //给foeTank加入一颗子弹
                    for (int j = 0; j < foeTank.shots.size(); j++) {
                        Shot shot = new Shot(foeTank.getX() + 25, foeTank.getY() + 60, foeTank.getDirect());
                        foeTank.shots.add(shot);
                        new Thread(shot).start();
                    }
                    foeTanks.add(foeTank);
                }
                break;
            default:
                System.out.println("输入有误");
                break;
        }
        //初始化爆炸图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/3.gif"));

        new AePlayWave("src\\bgm.wav").start();
    }

    //显示击毁敌方坦克数
    public void showInfo(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("您累计击毁坦克数:", 1020, 30);
        drawTank(1020, 60, g, 0, 0);
        g.setColor(Color.BLACK);
        font = new Font("宋体", Font.BOLD, 50);
        g.setFont(font);
        g.drawString(Recorder.getAllFoeTankNum() + "", 1110, 105);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);    //绘制游戏区域
        showInfo(g);
        //绘制自己的坦克
        drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 1);
        //绘制子弹
        for (int i = 0; i < myTank.shots.size(); i++) {
            if (myTank.shots.get(i) != null && myTank.shots.get(i).isLive) {
                g.fillRect(myTank.shots.get(i).x, myTank.shots.get(i).y, 2, 2);
//            this.repaint();
            } else {//若不存在，就删除
                myTank.shots.remove(i);
            }
        }
        //如果bombs集合中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }
        //画出敌人的坦克
        for (int i = 0; i < foeTanks.size(); i++) {
            FoeTank f = foeTanks.get(i);

            if (f.isLive) {
                drawTank(f.getX(), f.getY(), g, f.getDirect(), 0);
                //画出f的所有子弹
                for (int j = 0; j < f.shots.size(); j++) {
                    Shot shot = f.shots.get(j);
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 2, 2, false);
                    } else {
                        f.shots.remove(shot);
                    }
                }
            }
        }

    }

    /**
     * @param x,y    左上角坐标
     * @param g      画笔
     * @param direct 坦克方向
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        //判断坦克类型
        switch (type) {
            case 0://foe
                g.setColor(Color.cyan);
                break;
            case 1://my
                g.setColor(Color.yellow);
                break;
        }

        //判断方向绘制坦克
        //direct (0 上 1 右 2 下 3 左 )
        switch (direct) {
            case 0://向上
                //轮子
                g.fill3DRect(x, y, 10, 60, false);//左
                g.fill3DRect(x + 40, y, 10, 60, false);//右
                g.fill3DRect(x + 10, y + 10, 30, 40, false);//坦克体
                g.drawLine(x + 25, y, x + 25, y + 15);//弹管
                g.fillOval(x + 10, y + 15, 30, 30);//观察仓
                break;
            case 1://向右
                //轮子
                g.fill3DRect(x - 5, y + 5, 60, 10, false);//左
                g.fill3DRect(x + 40 - 45, y + 45, 60, 10, false);//右
                g.fill3DRect(x + 10 - 5, y + 10 + 5, 40, 30, false);//坦克体
                g.drawLine(x + 25 + 30, y + 30, x + 25 + 15, y + 15 + 15);//弹管
                g.fillOval(x + 10, y + 15, 30, 30);//观察仓
                break;
            case 2://向下
                //轮子
                g.fill3DRect(x, y, 10, 60, false);//左
                g.fill3DRect(x + 40, y, 10, 60, false);//右
                g.fill3DRect(x + 10, y + 10, 30, 40, false);//坦克体
                g.drawLine(x + 25, y + 60, x + 25, y + 45);//弹管
                g.fillOval(x + 10, y + 15, 30, 30);//观察仓
                break;
            case 3://向左
                //轮子
                g.fill3DRect(x - 5, y + 5, 60, 10, false);//左
                g.fill3DRect(x + 40 - 45, y + 45, 60, 10, false);//右
                g.fill3DRect(x + 10 - 5, y + 10 + 5, 40, 30, false);//坦克体
                g.drawLine(x + 25 + 30 - 60, y + 30, x + 25 + 15 - 30, y + 15 + 15);//弹管
                g.fillOval(x + 10, y + 15, 30, 30);//观察仓
                break;
        }

    }

    //判断是否击中了敌人坦克
    public void hitFoeTank() {
        for (int j = 0; j < myTank.shots.size(); j++) {
            Shot shot = myTank.shots.get(j);
            if (myTank.shot != null && myTank.shot.isLive) {
                //遍历敌人坦克
                for (int i = 0; i < foeTanks.size(); i++) {
                    FoeTank foeTank = foeTanks.get(i);
                    hitTank(shot, foeTank);
                }
            }
        }
    }

    //判断是否击中了自己的坦克
    public void hitMyTank() {
        for (int i = 0; i < foeTanks.size(); i++) {
            FoeTank foeTank = foeTanks.get(i);
            for (int j = 0; j < foeTank.shots.size(); j++) {
                Shot shot = foeTank.shots.get(j);
                if (foeTank.shot != null && foeTank.shot.isLive) {
                    hitTank(shot, myTank);
                }
            }
        }
    }

    //判断子弹是否击中坦克
    public void hitTank(Shot s, Tank tank) {
        switch (tank.getDirect()) {
            case 0:
            case 2:
                if (s.x > tank.getX() && s.x < tank.getX() + 50
                        && s.y > tank.getY() && s.y < tank.getY() + 60) {
                    s.isLive = false;
                    tank.isLive = false;
                    //击中敌人坦克后，在集合中删除这个坦克
                    if (tank instanceof FoeTank) {
                        foeTanks.remove(tank);
                        Recorder.addAllFoeTankNum();
//                        new Recorder().writer();
                    } else {
                        myTank.isLive = false;
                        System.out.println("你输了");
                        System.exit(0);
                    }
                    //创建Bomb对象，加入到集合
                    bombs.add(new Bomb(tank.getX(), tank.getY()));
//                    System.out.println("击中");
                }                break;
            case 1:
            case 3:
                if (s.x > tank.getX() && s.x < tank.getX() + 60
                        && s.y < tank.getY() + 50 && s.y > tank.getY()) {
                    s.isLive = false;
                    tank.isLive = false;
                    //击中敌人坦克后，在集合中删除这个坦克
                    if (tank instanceof FoeTank) {
                        foeTanks.remove(tank);
                        Recorder.addAllFoeTankNum();
//                        new Recorder().writer();
                    } else {
                        myTank.isLive = false;
                        System.out.println("你输了");
                        System.exit(0);
                    }
                    //创建Bomb对象，加入到集合
                    bombs.add(new Bomb(tank.getX(), tank.getY()));
//                    System.out.println("击中");
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //通过按键操作 我方坦克
        if (e.getKeyCode() == KeyEvent.VK_S) {
            myTank.setDirect(2);
            myTank.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            myTank.setDirect(0);
            myTank.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            myTank.setDirect(1);
            myTank.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            myTank.setDirect(3);
            myTank.moveLeft();
        }
        //按J发射子弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
            myTank.shotMyTank();//子弹射击
        }

        //让面板重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                //每50ms重绘面板
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断是否击中敌人坦克
            hitFoeTank();
            //判断是否击中自己的坦克
            hitMyTank();
            this.repaint();
        }
    }
}
