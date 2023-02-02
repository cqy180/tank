package game;

import java.io.*;
import java.util.Vector;

/**
 * @author 陈青云
 * 游戏数据记录
 */
public class Recorder {
    private static int allFoeTankNum = 0;//击毁敌人坦克数量
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    static String filePath = "src\\gameDate.txt";
    static Vector<FoeTank> foeTanks = new Vector<>();
    static Vector<Node> nodes = new Vector<>();

    public static void setFoeTanks(Vector<FoeTank> foeTanks) {
        Recorder.foeTanks = foeTanks;
    }

    //从文件读取敌人坦克信息，并恢复数据
    //该方法在选择继续上局游戏时，进行调用
    public static Vector<Node> getNodeAndFoeTankNum(){
        try {
            br = new BufferedReader(new FileReader(filePath));
            allFoeTankNum = Integer.parseInt(br.readLine());//读取击毁坦克的数量
            String line = null;
            while ((line = br.readLine()) != null){
                //将读取到的数据用" "符分隔成字符串数组
                String[] xyd = line.split(" ");
                //还原xyd并创建node对象
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);//将获取到的node对象，放入nodes集合
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nodes;
    }

    //向文件写入敌人坦克信息
    public static void writer(){
        try {
            bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(allFoeTankNum + "");
            bw.newLine();
            for (int i = 0; i < foeTanks.size(); i++) {
                FoeTank foeTank = foeTanks.get(i);
                if (foeTank.isLive) {
                    String str = foeTank.getX() + " " + foeTank.getY() + " " + foeTank.getDirect();
                    bw.write(str);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getAllFoeTankNum() {
        return allFoeTankNum;
    }

    public static void setAllFoeTankNum(int allFoeTankNum) {
        Recorder.allFoeTankNum = allFoeTankNum;
    }
    //
    public static void addAllFoeTankNum(){
        Recorder.allFoeTankNum++;
    }

    public static String getFilePath() {
        return filePath;
    }
}
