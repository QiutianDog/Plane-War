package game.panels;

import javax.swing.*;
import java.awt.*;

public class Manage {
    // 窗口大小参数
    public static final Integer width = 400;
    public static final Integer height = 600;

    public void start() {
        // 创建界面
        JFrame frame = new JFrame();

        // 设置窗口大小
        frame.setSize(width, height);

        // 设置窗口不可拖动大小
        frame.setResizable(false);

        // 设计窗口标题
        frame.setTitle("The Plane War");

        // 设置窗口居中
        frame.setLocationRelativeTo(null);

        // 创建画布 JPlane
        PlanePanel panel = new PlanePanel();
        panel.setBackground(Color.white);

        // 将画布添加到窗口内
        frame.add(panel);

        // 安装监听器
        frame.addMouseListener(panel);
        frame.addMouseMotionListener(panel);

        // 画布动起来
        panel.move();

        // 设置关闭窗口后JVM终止运行
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 显示窗口
        frame.setVisible(true);
    }

}
