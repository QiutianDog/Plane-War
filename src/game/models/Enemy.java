package game.models;

import game.panels.Manage;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends FlyObject{

    private static ImageIcon[] imageIcons;

    static {
        imageIcons = new ImageIcon[10];
        for (int i = 0; i < imageIcons.length - 1; i++) {
            imageIcons[i] = new ImageIcon("src/game/images/e" + (i + 1) + ".png");
        }
        imageIcons[imageIcons.length - 1] = new ImageIcon("src/game/images/fbossbullet.png");
    }

    private Integer speed;

    private Integer type;

    // 用来处理爆炸特效的对象
    private Bomb bomb;

    public Enemy(Integer type) {
        super(imageIcons[type].getIconWidth()
                , imageIcons[type].getIconHeight()
                , new Random().nextInt(Manage.width - imageIcons[type].getIconWidth())
                , -imageIcons[type].getIconHeight());
        this.type = type;
        this.bomb = new Bomb();
        if (type >= 0 && type <= 2) {
            this.life = 3;
            speed = 2;
        } else if (type >= 3 && type <= 5) {
            this.life = 1;
            this.speed = 2;
        } else if (type >= 6 && type <= 9) {
            this.life = 5;
            this.speed = 1;
        }
    }

    @Override
    public void step() {
        setY(getY() + speed);
    }

    @Override
    public Image getImage() {
        if (isLife()) {
            return imageIcons[type].getImage();
        }

        if (isBomb()) {
            // 返回爆炸特效，特性结束后敌机死亡
            if (bomb.isEnd()) {
                state = DEAD;
                return null;
            }
            return bomb.getBombImage();
        }

        return null;
    }

    public Integer getReward() {
        if (type >= 0 && type <= 2) {
            return 3;
        } else if (type >= 3 && type <= 9) {
            return 1;
        }
        return 0;
    }
}
