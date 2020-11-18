package game.models;

import game.panels.Manage;

import javax.swing.*;
import java.awt.*;

public class Background extends FlyObject{

    private static ImageIcon[] imageIcons;

    static {
        imageIcons = new ImageIcon[4];
        for (int i = 0; i < imageIcons.length; i++) {
            imageIcons[i] = new ImageIcon("src/game/images/background_" + (i + 1) + ".png");
        }
    }

    public Background() {
        super(imageIcons[0].getIconWidth()
                , imageIcons[0].getIconHeight()
                , 0
                , -(imageIcons[0].getIconHeight() - Manage.height));
    }

    @Override
    public void step() {
        if (getY() <= 0) {
            setY(getY() + 1);
        } else if (getY() >= 0) {
            setY(-(imageIcons[0].getIconHeight() - Manage.height));
        }
    }

    @Override
    public Image getImage() {
        return null;
    }

    public Image getImage(Integer passNum) {
        return imageIcons[passNum - 1].getImage();
    }

    // 刷新背景图片，从头开始
    public void flush() {
        x = 0;
        y = -(imageIcons[0].getIconHeight() - Manage.height);
    }
}
