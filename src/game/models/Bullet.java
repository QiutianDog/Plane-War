package game.models;

import javax.swing.*;
import java.awt.*;

public class Bullet extends FlyObject {
    // 子弹的图片
    private static ImageIcon[] imageIcons;

    static {
        imageIcons = new ImageIcon[6];
        // 普通子弹
        imageIcons[0] = new ImageIcon("src/game/images/bullet.png");
        // 原力弹
        imageIcons[1] = new ImageIcon("src/game/images/bullet2.png");
        // 激光炮
        imageIcons[2] = new ImageIcon("src/game/images/bullet_1.png");
        // 技能波
        imageIcons[3] = new ImageIcon("src/game/images/bullet_2.png");

        // 左斜射的子弹
        imageIcons[4] = new ImageIcon("src/game/images/bullet2.png");
        // 右斜射的子弹
        imageIcons[5] = new ImageIcon("src/game/images/bullet2.png");
    }

    private Integer speedX;
    private Integer speedY;

    private Integer damage;

    private Integer type;

    public Bullet(Integer x, Integer y, Integer type) {
        super(imageIcons[type].getIconWidth()
                , imageIcons[type].getIconHeight()
                , x
                , y);
        this.type = type;
        this.speedX = 0;
        this.speedY = 3;
        switch (type) {
            case 0:
            case 1:
                this.damage = 1;
                break;
            case 2:
                this.damage = 2;
                break;
            case 3:
                this.damage = 10;
                this.speedY = 5;
                break;
            case 4:
                this.speedX = -1;
                this.speedY = 3;
                this.damage = 1;
                break;
            case 5:
                this.speedX = 1;
                this.speedY = 3;
                this.damage = 1;
                break;
        }
    }

    @Override
    public void step() {
        setX(getX() + speedX);
        setY(getY() - speedY);
    }

    @Override
    public Image getImage() {
        if (isLife()) {
            return imageIcons[type].getImage();
        }

        if (isBomb()) {
            // 子弹没有爆炸特效
            state = DEAD;
            return null;
        }
        return null;
    }

    public Integer getDamage() {
        return damage;
    }

    public Integer getType() {
        return type;
    }
}
