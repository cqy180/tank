package game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author 陈青云
 */
public class Test extends JFrame {
    private MyPanel mp;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Test();
        System.out.println("测试SSH连接");
    }

    public Test() {
        System.out.println("1:新游戏\n2:继续上局游戏\n请输入你的选择");
        String key = scanner.next();
        mp = new MyPanel(key);
        new Thread(mp).start();
        this.add(mp);
        this.setSize(1300,750);
        this.setVisible(true);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.writer();
                System.out.println("监听到了");
                System.exit(0);
            }
        });
    }
}
