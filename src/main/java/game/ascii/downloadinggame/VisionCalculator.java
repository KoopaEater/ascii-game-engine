package game.ascii.downloadinggame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VisionCalculator {

    public static List<Vision> calculateConeCells(Vector2D apex, Vector2D direction, double angle, double length, DownloadingGame game, Color color) {
        List<Vector2D> coneCells = new ArrayList<>();
        double halfAngle = Math.toRadians(angle / 2);

        // Iterate over a bounding box around the apex
        for (int x = (int) (apex.x - length); x <= apex.x + length; x++) {
            for (int y = (int) (apex.y - length); y <= apex.y + length; y++) {
                Vector2D cell = new Vector2D(x, y);
                if (isWithinCone(apex, direction, cell, halfAngle, length)) {
                    coneCells.add(cell);
                }
            }
        }
        return convertFromVectorToVision(coneCells, game, color);
    }

    private static boolean isWithinCone(Vector2D apex, Vector2D direction, Vector2D cell, double halfAngle, double length) {
        double dx = cell.x - apex.x;
        double dy = cell.y - apex.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > length) { return false; }

        double angleToCell = Math.atan2(dy, dx);
        double angleToDirection = Math.atan2(direction.y, direction.x);
        double angleDifference = Math.abs(angleToCell - angleToDirection);

        // Normalize the angle difference to be within [0, π]
        if (angleDifference > Math.PI) {
            angleDifference = 2 * Math.PI - angleDifference;
        }

        return angleDifference <= halfAngle;
    }

    private static List<Vision> convertFromVectorToVision(List<Vector2D> vectors, DownloadingGame game, Color color) {
        List<Vision> visions = new ArrayList<>(vectors.size());
        for (Vector2D vector : vectors) {
            Vision vision = new Vision(game, (int)vector.x, (int)vector.y, color);
            visions.add(vision);
        }
        return visions;
    }
}
