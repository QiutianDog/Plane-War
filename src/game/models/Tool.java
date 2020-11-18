package game.models;

import game.panels.Manage;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Tool extends FlyObject {

    private static ImageIcon imageIcons;

    static {
        imageIcons = new ImageIcon("src/game/images/boom2.png");
    }

    private Integer speedX;
    private Integer speedY;

    // 奖励 0 加血  1 加子弹 80 发
    private Integer reward;

    public Tool() {
        super(imageIcons.getIconWidth()
                , imageIcons.getIconHeight()
                , new Random().nextInt(Manage.width - imageIcons.getIconWidth())
                , -imageIcons.getIconHeight());
        this.speedX = 3;
        this.speedY = 2;
        this.reward = new Random().nextInt(2);
    }

    @Override
    public void step() {
        if (getX() >= Manage.width - imageIcons.getIconWidth()
                || getX() <= 0) {
            speedX = -speedX;
        }
        setX(getX() + speedX);
        setY(getY() + speedY);
    }

    @Override
    public Image getImage() {
        if (isLife()) {
            return imageIcons.getImage();
        }

        if (isBomb()) {
            state = DEAD;
            return null;
        }

        return null;
    }

    public Integer getReward() {
        return reward;
    }
}
