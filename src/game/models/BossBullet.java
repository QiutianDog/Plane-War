package game.models;

import game.panels.Manage;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BossBullet extends FlyObject {

    private static ImageIcon[] imageIcons;

    static {
        imageIcons = new ImageIcon[4];
        imageIcons[0] = new ImageIcon("src/game/images/bullet_8.png");
        imageIcons[1] = new ImageIcon("src/game/images/bullet_6.png");
        imageIcons[2] = new ImageIcon("src/game/images/bullet_7.png");
        imageIcons[3] = new ImageIcon("src/game/images/bullet_5.png");
    }

    private Integer type;

    private Integer damage;

    private Integer speedX;

    private Integer speedY;

    public BossBullet(Integer x, Integer y,Integer type) {
        super(imageIcons[type].getIconWidth()
                , imageIcons[type].getIconHeight()
                , x
                , y);
        this.type = type;
        switch (type) {
            case 0:
                this.speedY = 2;
                this.damage = 1;
                break;
            case 1:
                this.speedY = 2;
                this.damage = 2;
                break;
            case 2:
                int i = new Random().nextInt(2);
                this.speedX = i > 0 ? 1 : -1;
                this.speedY = 1;
                this.damage = 1;
                this.life = 1;
                break;
            case 3:
                i = new Random().nextInt(2);
                this.speedX = i > 0 ? 1 : -1;
                this.speedY = 1;
                this.damage = 2;
                this.life = 2;
                break;
            case 4:
                this.damage = 1;
                break;
            case 5:
                this.damage = 2;
        }
    }

    @Override
    public void step() {
        switch (type) {
            case 0:
            case 1:
                setY(getY() + speedY);
                break;
            case 2:
            case 3:
                if (getX() >= Manage.width - imageIcons[type].getIconWidth()
                        || getX() <= 0) {
                    speedX = -speedX;
                }
                setX(getX() + speedX);
                setY(getY() + speedY);
        }
    }

    @Override
    public Image getImage() {
        if (isLife()) {
            return imageIcons[type].getImage();
        }

        if (isBomb()) {
            // 马上死
            state = DEAD;
        }

        return null;
    }

    public Integer getType() {
        return type;
    }
}
