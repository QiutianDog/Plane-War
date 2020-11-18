package game.panels;

import game.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

public class PlanePanel extends JPanel implements MouseListener, MouseMotionListener {
    // 设置游戏的运行状态
    public static final Integer START = 0;
    public static final Integer RUNNING = 1;
    public static final Integer PAUSE = 2;
    public static final Integer OVER = 3;
    public static final Integer PASS = 4;
    public static final Integer OVERCOME = 5;
    // 游戏默认的运行状态
    private Integer state;

    // 设置游戏的关卡
    private Integer passNum;

    // 设置游戏的关卡得分
    private Integer passScore;

    // 英雄机的等级
    private Integer heroLevel;

    // 英雄机的技能数量
    private Integer skillNum;

    // 是否是boss战
    private boolean isBoss;

    // boss数量
    private Integer bossNum;

    // 是否过关
    private boolean isPass;

    // 各种飞行对象
    Hero hero;
    Background background;
    ArrayList<FlyObject> enemys; // 敌机列表
    ArrayList<FlyObject> enemyBullets; // 敌机子弹列表
    ArrayList<FlyObject> bullets; // 子弹列表
    ArrayList<FlyObject> tools; // 道具列表

    public PlanePanel() {
        reset();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        /* state == RUNNING */

        // 画背景
        g.drawImage(background.getImage(passNum), background.getX(), background.getY(), null);

        // 画分数 关卡 技能数量
        g.setFont(new Font("TimesRoman", Font.BOLD, 18));
        g.setColor(Color.YELLOW);
        g.drawString("Score:" + passScore + "", 2, 20); // 画出分数
        g.drawString("Life：" + hero.getLife() + "", 2, 40); // 血量
        g.drawString("Pass：" + passNum + "", 310, 20); // 关卡
        g.drawString("Skill：" + skillNum + "       鼠标左键放技能", 2, 550); // 关卡

        // 画英雄机
        g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);

        // 画子弹
        for (FlyObject bullet : bullets) {
            g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), null);
        }
        for (FlyObject bullet : enemyBullets) {
            g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), null);
        }

        // 画敌机
        for (FlyObject enemy : enemys) {
            g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), null);
        }

        // 画敌机的子弹
        for (FlyObject enemyBullet : enemyBullets) {
            g.drawImage(enemyBullet.getImage(), enemyBullet.getX(), enemyBullet.getY(), null);
        }

        // 画道具
        for (FlyObject tool : tools) {
            g.drawImage(tool.getImage(), tool.getX(), tool.getY(), null);
        }

        if (state.equals(PAUSE)) {
            ImageIcon imageIcon = new ImageIcon("src/game/images/pause.png");
            Image img = imageIcon.getImage();
            g.drawImage(img, 0, 0, null);
        } else if (state.equals(PASS)) {
            ImageIcon imageIcon = new ImageIcon("src/game/images/passback.png");
            Image img = imageIcon.getImage();
            g.drawImage(img, 0, 0, null);
            g.setFont(new Font("TimesRoman", Font.BOLD, 30));
            g.setColor(Color.BLACK);
            g.drawString("第 " + passNum + " 关", Manage.width / 2 - 55, Manage.height / 2 - 35);
        } else if (state.equals(START)) {
            ImageIcon imageIcon = new ImageIcon("src/game/images/interface_1.png");
            Image img = imageIcon.getImage();
            g.drawImage(img, 0, 0, null);
        } else if (state.equals(OVER)) {
            ImageIcon imageIcon = new ImageIcon("src/game/images/jeimian_2.png");
            Image img = imageIcon.getImage();
            g.drawImage(img, 0, 0, null);
        } else if (state.equals(OVERCOME)) {
            ImageIcon imageIcon = new ImageIcon("src/game/images/passback.png");
            Image img = imageIcon.getImage();
            g.drawImage(img, 0, 0, null);
            g.setFont(new Font("TimesRoman", Font.BOLD, 30));
            g.setColor(Color.BLACK);
            g.drawString("恭喜你通关了！", Manage.width / 2 - 100, Manage.height / 2 - 35);
        }
    }

    public void move() {
        new Thread(()->{
            // 同步计数器
            int count = 0;
            // 无敌时间计数器
            int hurtCount = 0;
            while (true) {
                if (state.equals(RUNNING)) {
                    // 移动画布
                    background.step();

                    // 添加英雄机子弹
                    if (heroLevel == 0) {
                        if (hero.getFire() <= 0 && count % 20 == 0) {
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 2 - 3, hero.getY() - 3, 0));
                        } else if (hero.getFire() >= 1 && hero.getFire() < 40 && count % 20 == 0) {
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 - 8, hero.getY() + 20, 0));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 * 2 + 4, hero.getY() + 20, 0));
                            hero.setFire(hero.getFire() - 1);
                        } else if (hero.getFire() >= 40 && count % 20 == 0) {
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 - 8, hero.getY() + 20, 0));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 * 2 + 4, hero.getY() + 20, 0));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 - 8, hero.getY() + 20, 4));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 * 2 + 4, hero.getY() + 20, 5));
                            hero.setFire(hero.getFire() - 2);
                        }
                    } else if (heroLevel == 1) {
                        if (hero.getFire() <= 0 && count % 20 == 0) {
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 2 - 8, hero.getY() - 8, 1));
                        } else if (hero.getFire() >= 1 && hero.getFire() < 40 && count % 20 == 0) {
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 - 12, hero.getY() + 20, 1));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 * 2, hero.getY() + 20, 1));
                            hero.setFire(hero.getFire() - 1);
                        } else if (hero.getFire() >= 40 && count % 20 == 0) {
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 - 12, hero.getY() + 20, 1));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 * 2, hero.getY() + 20, 1));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 - 12, hero.getY() + 20, 4));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 * 2, hero.getY() + 20, 5));
                            hero.setFire(hero.getFire() - 2);
                        }
                    } else if (heroLevel == 2) {
                        if (hero.getFire() <= 1 && count % 20 == 0) {
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 2 - 5, hero.getY() - 15, 2));
                        } else if (hero.getFire() >= 2 && hero.getFire() < 40 && count % 20 == 0) {
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 2 - 5, hero.getY() - 15, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 - 15, hero.getY() + 20, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 * 2 + 6, hero.getY() + 20, 2));
                            hero.setFire(hero.getFire() - 1);
                        } else if (hero.getFire() >= 40 && count % 20 == 0) {
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 2 - 5, hero.getY() - 15, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 - 15, hero.getY() + 20, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 * 2 + 6, hero.getY() + 20, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 - 15, hero.getY() + 20, 4));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 * 2 + 6, hero.getY() + 20, 5));
                            hero.setFire(hero.getFire() - 2);
                        }
                    } else if (heroLevel == 3) {
                        if (hero.getFire() <= 1 && count % 20 == 0) {
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 - 8, hero.getY() - 20, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 * 2 + 3, hero.getY() - 20, 2));
                        } else if (hero.getFire() >= 2 && hero.getFire() < 40 && count % 20 == 0) {
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 - 8, hero.getY() - 20, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 * 2 + 3, hero.getY() - 20, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 6 - 12, hero.getY() + 10, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 6 * 5 + 10, hero.getY() + 10, 2));
                            hero.setFire(hero.getFire() - 1);
                        } else if (hero.getFire() >= 40 && count % 20 == 0) {
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 - 8, hero.getY() - 20, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 3 * 2 + 3, hero.getY() - 20, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 6 - 12, hero.getY() + 10, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 6 * 5 + 10, hero.getY() + 10, 2));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 6 - 12, hero.getY() + 10, 4));
                            bullets.add(new Bullet(hero.getX() + hero.getWidth() / 6 * 5 + 10, hero.getY() + 10, 5));
                            hero.setFire(hero.getFire() - 2);
                        }
                    }

                    // 让子弹飞
                    for (FlyObject bullet : bullets) {
                        bullet.step();
                    }

                    // 添加敌机
                    if (count % 50 == 0) {
                        enemys.add(new Enemy(new Random().nextInt(3) + 3));
                    }
                    if (count % 250 == 0) {
                        enemys.add(new Enemy(new Random().nextInt(3)));
                    }
                    if (count % 400 == 0) {
                        enemys.add(new Enemy(new Random().nextInt(4) + 6));
                    }

                    // 添加boss
                    if (isBoss && bossNum == 0) {
                        bossNum++;
                        enemys.add(new Boss(passNum - 2));
                    }

                    // 让敌机飞
                    for (FlyObject enemy : enemys) {
                        enemy.step();
                    }

                    // 添加敌机子弹
                    for (FlyObject enemy : enemys) {
                        if (!enemy.isLife()) continue;
                        if (enemy instanceof Boss) {
                            // 是boss才发射子弹
                            if (((Boss) enemy).getType() == 0) {
                                if (count % 40 == 0) {
                                    enemyBullets.add(new BossBullet(enemy.getX() + enemy.getWidth() / 2 - 9, enemy.getY() + enemy.getHeight(), 2));
                                }

                                if (count % 120 == 0) {
                                    enemyBullets.add(new BossBullet(enemy.getX() + enemy.getWidth() / 2 - 9, enemy.getY() + enemy.getHeight(), 0));
                                }
                            } else if (((Boss) enemy).getType() == 1) {
                                if (count % 40 == 0) {
                                    enemyBullets.add(new BossBullet(enemy.getX() + enemy.getWidth() / 2 - 9, enemy.getY() + enemy.getHeight(), 3));
                                }

                                if (count % 160 == 0) {
                                    enemyBullets.add(new BossBullet(enemy.getX() + enemy.getWidth() / 2 - 9, enemy.getY() + enemy.getHeight(), 1));
                                }
                            }

                        }
                    }

                    // 让子弹飞
                    for (FlyObject enemyBullet : enemyBullets) {
                        enemyBullet.step();
                    }

                    // 添加工具
                    if (count % 400 == 0) {
                        tools.add(new Tool());
                    }

                    // 让工具飞
                    for (FlyObject tool : tools) {
                        tool.step();
                    }


                    /*                  碰撞处理                 */

                    // 我方子弹和敌机碰撞
                    for (FlyObject bullet : bullets) {
                        if (!bullet.isLife()) continue;
                        for (FlyObject enemy : enemys) {
                            if (!enemy.isLife()) continue;
                            if (Crashs.isCrash(bullet, enemy)) {
                                // 子弹打出伤害 敌机受伤
                                if (((Bullet) bullet).getType() != 3) {
                                    bullet.bomb();
                                }
                                enemy.setLife(enemy.getLife() - ((Bullet) bullet).getDamage());
                                // 判断敌机是否死亡
                                if (enemy.getLife() <= 0) {
                                    enemy.bomb();

                                    // 获得奖励
                                    if (enemy instanceof Enemy) {
                                        passScore += ((Enemy) enemy).getReward();
                                    }
                                }
                            }
                        }
                    }

                    // 我方子弹和敌方子弹
                    for (FlyObject bullet : bullets) {
                        if (!bullet.isLife()) continue;
                        for (FlyObject enemyBullet : enemyBullets) {
                            if (!enemyBullet.isLife()) continue;
                            // 只能打掉小子弹
                            if (Crashs.isCrash(bullet, enemyBullet)) {
                                if (((BossBullet) enemyBullet).getType() == 2
                                        || ((BossBullet) enemyBullet).getType() == 3) {
                                    // 子弹掉血
                                    if (((Bullet) bullet).getType() != 3) {
                                        bullet.bomb();
                                    }
                                    enemyBullet.setLife(enemyBullet.getLife() - ((Bullet) bullet).getDamage());

                                    if (enemyBullet.getLife() <= 0) {
                                        enemyBullet.bomb();
                                    }
                                }
                            }
                        }
                    }

                    // 我方英雄机和敌机碰撞
                    for (FlyObject enemy : enemys) {
                        if (!enemy.isLife()) continue;
                        if (Crashs.isCrash(hero, enemy)) {
                            // 英雄机受到伤害并无敌1秒
                            if (count > hurtCount) {
                                hero.setLife(hero.getLife() - 1);
                                hurtCount = count + 100;

                                // 敌机受到10点伤害
                                enemy.setLife(enemy.getLife() - 10);

                                // 判断敌机是否死亡
                                if (enemy.getLife() <= 0) {
                                    enemy.bomb();

                                    // 获得奖励
                                    passScore += ((Enemy) enemy).getReward();
                                }
                            }
                        }
                    }

                    // 我方英雄级和敌方子弹碰撞
                    for (FlyObject enemyBullet : enemyBullets) {
                        if (!enemyBullet.isLife()) continue;
                        if (Crashs.isCrash(hero, enemyBullet)) {
                            // 英雄机受到伤害并无敌1秒
                            if (count > hurtCount) {
                                hero.setLife(hero.getLife() - 1);
                                hurtCount = count + 100;

                                // 子弹受到10点伤害
                                enemyBullet.setLife(enemyBullet.getLife() - 10);

                                // 判断敌方子弹是否死亡
                                if (enemyBullet.getLife() <= 0) {
                                    enemyBullet.bomb();
                                }
                            }
                        }
                    }

                    // 我方英雄机和道具碰撞
                    for (FlyObject tool : tools) {
                        if (!tool.isLife()) continue;
                        if (Crashs.isCrash(hero, tool)) {
                            // 获得奖励 道具消失
                            tool.bomb();
                            if (((Tool) tool).getReward() == 0) {
                                hero.setLife(hero.getLife() + 1);
                                hero.setFire(hero.getFire() + 50);
                            } else skillNum++;
                        }
                    }


                    /*              清除多余缓存               */

                    // 清除超过边界和死亡的子弹
                    for (int i = 0; i < bullets.size(); i++) {
                        if (bullets.get(i).getY() <= -(bullets.get(i).getHeight())
                                || bullets.get(i).isDead()) {
                            bullets.remove(i--);
                        }
                    }

                    // 清除超过边界和死亡的敌方子弹
                    for (int i = 0; i < enemyBullets.size(); i++) {
                        if (enemyBullets.get(i).getY() >= Manage.height
                                || enemyBullets.get(i).isDead()) {
                            enemyBullets.remove(i--);
                        }
                    }

                    // 清除超过边界和死亡的敌机
                    for (int i = 0; i < enemys.size(); i++) {
                        if (enemys.get(i).getY() >= Manage.height
                                || enemys.get(i).isDead()) {
                            if (enemys.get(i) instanceof Boss) {
                                isPass = true;
                            }
                            enemys.remove(i--);
                        }
                    }

                    // 清除超过边界和死亡的道具
                    for (int i = 0; i < tools.size(); i++) {
                        if (tools.get(i).getY() >= Manage.height
                                || tools.get(i).isDead()) {
                            tools.remove(i--);
                        }
                    }

                    if (hero.getLife() <= 0) {
                        state = OVER;
                    }

                    // 结算游戏 判断是否升级 开启boss战 进入下一关
                    done();

                    // 重置计数器
                    count++;
                    if (count == Integer.MAX_VALUE) {
                        hurtCount = hurtCount - count;
                        count = 0;
                    }
                }

                // 重画
                repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 初始化参数
    private void reset() {
        state = START;
        passNum = 1;
        passScore = 0;
        heroLevel = 0;
        skillNum = 3;
        bossNum = 0;
        isBoss = false;
        isPass = false;
        hero = new Hero(Manage.width / 2, Manage.height / 2, heroLevel);
        background = new Background();
        enemys = new ArrayList<>();
        enemyBullets = new ArrayList<>();
        bullets = new ArrayList<>();
        tools = new ArrayList<>();
    }

    // 结算游戏 判断是否升级 开启boss战 进入下一关
    private void done() {
        // 升级战机
        if (hero.getLife() >= 1 && hero.getLife() < 5) {
            heroLevel = 0;
        } else if (hero.getLife() >= 5 && hero.getLife() < 10) {
            heroLevel = 1;
        } else if (hero.getLife() >= 10 && hero.getLife() < 15) {
            heroLevel = 2;
        } else if (hero.getLife() >= 15 && hero.getLife() < 20) {
            heroLevel = 3;
        }
        if (!hero.getLevel().equals(heroLevel)) {
            hero = new Hero(hero.getX(), hero.getY(), heroLevel);
        }

        // 是否开启是boss战
        if (passScore >= 100 && passNum == 2) {
            isBoss = true;
        } else if (passScore >= 100 && passNum == 3) {
            isBoss = true;
        }

        // 是否下一关
        if (passScore >= 200 && passNum == 1) {
            passNum++;
            state = PASS;
        } else if (isPass && passNum < 3) {
            passNum++;
            state = PASS;
        } else if (isPass && passNum == 3) {
            // 结束游戏
            state = OVERCOME;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (state.equals(RUNNING)) {
            // 释放技能
            if (skillNum >= 1) {
                int begin = -15;
                bullets.add(new Bullet(begin, Manage.height, 3));
                bullets.add(new Bullet(begin + 70, Manage.height, 3));
                bullets.add(new Bullet(begin + 140, Manage.height, 3));
                bullets.add(new Bullet(begin + 210, Manage.height, 3));
                bullets.add(new Bullet(begin + 280, Manage.height, 3));
                bullets.add(new Bullet(begin + 350, Manage.height, 3));
                skillNum--;
            }
        } else if (state.equals(START) || state.equals(PAUSE)) {
            state = RUNNING;
        } else if (state.equals(PASS)) {
            // 进入下一关
            passScore = 0;
            bossNum = 0;
            isBoss = false;
            isPass = false;
            enemys = new ArrayList<>();
            enemyBullets = new ArrayList<>();
            bullets = new ArrayList<>();
            tools = new ArrayList<>();
            state = RUNNING;
        } else if (state.equals(OVER) || state.equals(OVERCOME)) {
            // 初始化参数重新开始游戏
            reset();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // 鼠标移出游戏窗口 暂停
        if (state.equals(RUNNING)) {
            state = PAUSE;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (state.equals(RUNNING)) {
            hero.setX(e.getX() - hero.getWidth() / 2 - 7);
            hero.setY(e.getY() - hero.getHeight() / 2 - 20);
        }
    }
}
