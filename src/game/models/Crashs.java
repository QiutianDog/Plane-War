package game.models;

// 处理碰撞的工具类
public class Crashs {
    /**
     * 用来
     * @param t 碰撞体
     * @param k 碰撞体
     * @param <T> extends FlyObject
     * @param <K> extends FlyObject
     * @return
     */
    public static <T extends FlyObject, K extends FlyObject> boolean isCrash(T t, K k) {
        if (t == null || k == null) {
            return false;
        }

        // 判断两个飞行物是否有相交
        int xl1 = Math.abs(k.getX() + k.getWidth() - t.getX());
        int xl2 = Math.abs(t.getX() + t.getWidth() - k.getX());
        int xl = Math.max(xl1, xl2);

        int yl1 = Math.abs(k.getY() + k.getHeight() - t.getY());
        int yl2 = Math.abs(t.getY() + t.getHeight() - k.getY());
        int yl = Math.max(yl1, yl2);

        if (xl <= t.getWidth() + k.getWidth()
                && yl <= t.getHeight() + k.getHeight()) {
            return true;
        }
        return false;
    }

}
