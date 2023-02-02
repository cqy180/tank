package game;

import javax.sound.sampled.*;
import java.io.*;

/**
 * @author 陈青云
 */
public class AePlayWave extends Thread {

    private String filename ;
    public AePlayWave(String src){
        filename = src;
    }

    @Override
    public void run() {
        // 从项目资源目录下加载背景音乐
        AudioInputStream audioInputStream = null;
        File file = new File(filename);
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
        } catch (Exception e1) {
            e1.printStackTrace();
            return;
        }

        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        auline.start();
        int nBytesRead = 0;
        //缓存
        byte[] abData = new byte[512];

        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    auline.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
        }
    }
}
