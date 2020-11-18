package game.models;

import javax.swing.*;
import java.awt.*;

// 这是一个专门处理爆炸动画的对象
public class Bomb {

    private static ImageIcon[] imageIcons;

    static {
        imageIcons = new ImageIcon[6];
        for (int i = 0; i < imageIcons.length; i++) {
            imageIcons[i] = new ImageIcon("src/game/images/boom" + i + ".png");
        }
    }

    // 计数器
    private Integer index;

    // 爆炸计数器
    private Integer bombIndex;

    public Bomb() {
        this.flush();
    }

    // 返回爆炸图片
    public Image getBombImage() {
        if (index++ % 4 == 0 && bombIndex < 5) {
            bombIndex++;
        }
        return imageIcons[bombIndex].getImage();
    }

    // 重置计数器
    public void flush() {
        index = 1;
        bombIndex = 0;
    }

    // 判断爆炸是否结束
    public boolean isEnd() {
        return bombIndex == 5 && index >= 24;
    }
}
