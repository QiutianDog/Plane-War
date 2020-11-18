package game.models;

import java.awt.*;

public abstract class FlyObject {
    // 飞行物的状态
    public static final Integer LIFE = 0;
    public static final Integer BOMB = 1;
    public static final Integer DEAD = 2;

    // 默认的飞行状态
    protected Integer state;

    // 飞行物的宽和高 还有对应坐标
    protected Integer width;
    protected Integer height;
    protected Integer x;
    protected Integer y;

    // 生命值
    protected Integer life;

    public FlyObject() {

    }

    public FlyObject(Integer width, Integer height, Integer x, Integer y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.state = LIFE;
        this.life = 1;
    }

    // 移动控制
    public abstract void step();

    // 根据状态获取图片模型
    public abstract Image getImage();

    public void bomb() {
        state = BOMB;
    }

    public boolean isLife(){ return state.equals(LIFE); }

    public boolean isBomb(){ return state.equals(BOMB); }

    public boolean isDead(){ return state.equals(DEAD); }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }
}
