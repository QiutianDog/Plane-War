package game.models;

import game.panels.Manage;

import javax.swing.*;
import java.awt.*;

public class Boss extends FlyObject {

    private static ImageIcon[] imageIcons;

    static {
        imageIcons = new ImageIcon[2];
        imageIcons[0] = new ImageIcon("src/game/images/plane_5.png");
        imageIcons[1] = new ImageIcon("src/game/images/plane_6.png");
    }

    private Integer type;

    private Integer speedX;

    private Integer speedY;

    public Boss(Integer type) {
        super(imageIcons[type].getIconWidth()
                , imageIcons[type].getIconHeight()
                , (Manage.width - imageIcons[type].getIconWidth()) / 2
                , -imageIcons[type].getIconHeight());
        this.type = type;
        this.speedY = 1;
        this.speedX = 1;
        switch (type) {
            case 0:
                this.life = 200;
                break;
            case 1:
                this.life = 400;
        }
    }

    @Override
    public void step() {
        // 开场下降
        if (getY() <= 30 && isLife()) {
            setY(getY() + speedY);
        }

        // 左右平移
        if (getX() >= Manage.width - imageIcons[type].getIconWidth()
                || getX() <= 0) {
            speedX = -speedX;
        }
        setX(getX() + speedX);

        // 死亡回去
        if (getY() >= -imageIcons[type].getIconHeight() - 300 && !isLife()) {
            setY(getY() - speedY);
        }
    }

    @Override
    public Image getImage() {
        if (isLife()) {
            return imageIcons[type].getImage();
        }

        if (isBomb()) {
            // 爆炸状态等boss退场了再死亡
            if (getY() <= -imageIcons[type].getIconHeight()) {
                state = DEAD;
            }
            return imageIcons[type].getImage();
        }

        return null;
    }

    public Integer getType() {
        return type;
    }
}
