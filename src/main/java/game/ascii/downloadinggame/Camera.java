package game.ascii.downloadinggame;

import actor.ActorConstants;
import actor.ascii.MutableAsciiActor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Camera implements Obstacle{

    private DownloadingGame game;
    private MutableAsciiActor camera;
    private Vector2D pos;
    private Vector2D direction;
    private int turnDelay, waitDelay;
    private int tickCount;
    private int minTurnAngle, maxTurnAngle, currentTurnAngle;
    private int normalTurnVelocity, currentTurnVelocity;
    private TurnState turnState;
    private int coneWidth, coneSize;

    private List<Vision> visions;

    public Camera(DownloadingGame game, char symbol, int x, int y, Vector2D direction, int turnDelay, int waitDelay, int turnAngle, int coneWidth, int coneSize) {
        this.game = game;
        pos = new Vector2D(x, y);
        this.direction = direction;
        this.turnDelay = turnDelay;
        this.waitDelay = waitDelay;
        tickCount = 0;
        minTurnAngle = -turnAngle/2;
        maxTurnAngle = turnAngle/2;
        currentTurnAngle = 0;
        normalTurnVelocity = 5;
        currentTurnVelocity = normalTurnVelocity;
        turnState = TurnState.TURNING;
        this.coneWidth = coneWidth;
        this.coneSize = coneSize;

        visions = new ArrayList<>();

        camera = game.createSymbolActor();
        Vector2D screenPos = game.calcScreenPosition(x, y);
        camera.moveTo((int)screenPos.x, (int)screenPos.y);
        camera.setSymbol(symbol);
        camera.setColor(Color.lightGray);
        camera.setZ(ActorConstants.Z_DEFAULT);
        camera.show();

        game.addToObstaclesAndEverything(this);
        game.addToCameras(this);
        generateCone();

    }

    private void generateCone() {
        int directionAngle = Vector2D.getAngleFromVector2D(direction);
        int finalAngle = currentTurnAngle + directionAngle;
        Vector2D angleVector = Vector2D.getVectorFromAngle(finalAngle);
        for (Vision vision : visions) {
            MutableAsciiActor actor = vision.getActor();
            actor.hide();
            game.removeFromVisionAndEverything(vision);
            game.removeActor(actor);
        }
        visions = VisionCalculator.calculateConeCells(pos, angleVector, coneWidth, coneSize, game, Color.YELLOW);
    }

    private void turn() {
        currentTurnAngle += currentTurnVelocity;
        if (currentTurnAngle >= maxTurnAngle) {
            turnState = TurnState.WAITING;
            currentTurnVelocity = -normalTurnVelocity;
        }
        if (currentTurnAngle <= minTurnAngle) {
            turnState = TurnState.WAITING;
            currentTurnVelocity = normalTurnVelocity;
        }
        generateCone();
        game.checkPlayerInVision();
    }

    public void tryTurn() {
        switch (turnState) {
            case TURNING:
                if (tickCount >= turnDelay) {
                    tickCount = 0;
                } else {
                    turn();
                }
                break;
            case WAITING:
                if (tickCount >= waitDelay) {
                    turnState = TurnState.TURNING;
                    tickCount = 0;
                }
                break;
        }
        tickCount++;
    }

    @Override
    public boolean isAt(int x, int y) {
        return pos.x == x && pos.y == y;
    }

    @Override
    public MutableAsciiActor getActor() {
        return camera;
    }
}
