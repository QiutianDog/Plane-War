package game.models;

import javax.swing.*;
import java.awt.*;

public class Hero extends FlyObject{
    // 英雄机的图片
    private static ImageIcon[] imageIcons;

    static {
        imageIcons = new ImageIcon[4];
        for (int i = 0; i < imageIcons.length; i++) {
            imageIcons[i] = new ImageIcon("src/game/images/plane_" + (i + 1) + ".png");
        }
    }

    // 英雄机等级
    private Integer level;

    // 火力值
    private Integer fire;

    public Hero(Integer x, Integer y, Integer level) {
        super(imageIcons[level].getIconWidth()
                , imageIcons[level].getIconHeight()
                , x
                , y);
        this.life = 3;
        this.level = level;
        switch (level) {
            case 0:
                this.life = 3;
                this.fire = 0;
                break;
            case 1:
                this.life = 5;
                this.fire = 200;
                break;
            case 2:
                this.life = 10;
                this.fire = 500;
                break;
            case 3:
                this.life = 15;
                this.fire = 800;
        }
    }

    @Override
    public void step() {
        // 移动控制由鼠标获取，这里不做操作
    }

    @Override
    public Image getImage() {
        return imageIcons[level].getImage();
    }

    public Integer getFire() {
        return fire;
    }

    public void setFire(Integer fire) {
        this.fire = fire;
    }

    public Integer getLevel() {
        return level;
    }
}
