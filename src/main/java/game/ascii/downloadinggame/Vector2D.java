package game.ascii.downloadinggame;


public class Vector2D {

    public static int getAngleFromVector2D(Vector2D v) {
        int angle = (int) Math.round(Math.toDegrees(Math.atan2(v.y, v.x)));
        return angle;
    }

    public static Vector2D getVectorFromAngle(int angle) {
        double x = Math.cos(Math.toRadians(angle));
        double y = Math.sin(Math.toRadians(angle));
        Vector2D angleVector = new Vector2D(x, y);
        return angleVector;
    }

    public double x, y;
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2D other) {
        x += other.x;
        y += other.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector2D) {
            Vector2D other = (Vector2D) obj;
            return x == other.x && y == other.y;
        }
        return false;
    }
}
