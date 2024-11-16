package game.ascii.downloadinggame;

import actor.ascii.MutableAsciiActor;
import actor.ascii.text.MutableTextAsciiActor;
import game.ascii.AbstractAsciiGame;
import ui.mouse.ClickEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class DownloadingGame extends AbstractAsciiGame {

    private static final int MIN_MOVE_DELAY = 2;
    private static final int MAX_MOVE_DELAY = 5;

    private int moveDelay;
    private int timeSinceLastMove;
    private List<MutableAsciiActor> everythingExceptPlayer;
    private List<Obstacle> obstacles;
    private List<Vision> visions;

    private Player player;
    private Human human;
    private Looker looker;
    private Camera camera;
    private BreathingMeter breathingMeter;

    public DownloadingGame() {
        super("Downloading...", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, 10);
    }

    @Override
    public void setup() {

        moveDelay = 2;

        everythingExceptPlayer = new ArrayList<>();
        obstacles = new ArrayList<>();
        visions = new ArrayList<>();

        setFontSize(24);
        setBackgroundColor(Color.darkGray);

        player = new Player(this);

        human = new Human(this, 10, 10, 3);
        human.setPath(new ArrayList<Vector2D>(
                List.of(new Vector2D(10,10), new Vector2D(10,15), new Vector2D(15,15), new Vector2D(15,10))
        ));
        human.setDirs(new ArrayList<Vector2D>(
                List.of(new Vector2D(-1,0), new Vector2D(0,1), new Vector2D(1,0), new Vector2D(0,-1))
        ));

        looker = new Looker(this, 10, 22, new Vector2D(1, 1), 20);

        camera = new Camera(this, '▼', 30, 10, new Vector2D(0,1), 2, 20, 90);

        breathingMeter = new BreathingMeter(this);

        generateWalls();

        player.moveToWorldPos(0, 1);
    }

    private void tryMove() {
        timeSinceLastMove++;

        if (timeSinceLastMove >= moveDelay) {
            if (isKeyDown(KeyEvent.VK_LEFT)) {
                player.move(-1, 0);
            } else if (isKeyDown(KeyEvent.VK_RIGHT)) {
                player.move(1, 0);
            } else if (isKeyDown(KeyEvent.VK_UP)) {
                player.move(0, -1);
            } else if (isKeyDown(KeyEvent.VK_DOWN)) {
                player.move(0, 1);
            } else {
                player.standStill();
            }
            timeSinceLastMove = 0;
            checkPlayerInVision();
        }
    }

    private void showDeathScreen() {
        stopGameLoop();
        MutableTextAsciiActor deathMessage = createSymbolTextActor(20);
        deathMessage.setText("You died...");
        deathMessage.setColor(Color.WHITE);
        deathMessage.show();
    }

    public void checkPlayerInVision() {
        boolean dead = false;
        if (!player.isInDanger()) {
            return;
        }
        for (Vision vision : visions) {
            if (vision.isAt((int)getPlayerPosition().x, (int)getPlayerPosition().y)) {
                dead = true;
            }
        }
        if (dead) {
            showDeathScreen();
        }
    }

    public void moveEverythingExceptPlayerOpposite(int dx, int dy) {
        for (MutableAsciiActor actor : everythingExceptPlayer) {
            actor.move(-dx, -dy);
        }
    }

    public boolean obstacleAt(int x, int y) {
        return obstacles.stream().anyMatch(o -> o.isAt(x, y));
    }

    public Vector2D getPlayerPosition() {
        return player.getPosition();
    }

    public Vector2D calcScreenPosition(int mapX, int mapY) {
        int screenX = Constants.MID_SCREEN_X + mapX - (int)getPlayerPosition().x;
        int screenY = Constants.MID_SCREEN_Y + mapY - (int)getPlayerPosition().y;
        return new Vector2D(screenX, screenY);
    }

    public void addToEverything(MutableAsciiActor actor) {
        everythingExceptPlayer.add(actor);
    }

    public void addToObstaclesAndEverything(Obstacle obstacle) {
        obstacles.add(obstacle);
        addToEverything(obstacle.getActor());
    }

    public void addToVisionAndEverything(Vision vision) {
        visions.add(vision);
        addToEverything(vision.getActor());
    }

    public void removeFromVisionAndEverything(Vision vision) {
        visions.remove(vision);
        everythingExceptPlayer.remove(vision.getActor());
    }

    public void adjustMoveDelayFromBreathRate(int breathRate) {
        moveDelay = MIN_MOVE_DELAY + (MAX_MOVE_DELAY - MIN_MOVE_DELAY) * (BreathingMeter.MAX_BREATH_RATE - breathRate) / (BreathingMeter.MAX_BREATH_RATE - BreathingMeter.MIN_BREATH_RATE);
    }

    @Override
    public void tick(long deltaTime) {

    }

    @Override
    public void fixedTick() {
        tryMove();
        human.tryStep();
        looker.tryLook();
        camera.tryTurn();
        breathingMeter.tryTick();
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            breathingMeter.breathIn();
        }
    }

    @Override
    public void onKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            breathingMeter.breathOut();
        }
    }

    @Override
    public void onKeyTyped(KeyEvent e) {

    }

    @Override
    public void onClick(ClickEvent e) {

    }

    private void generateWalls() {
        List<MutableAsciiActor> obstacleActors = new ArrayList<>();
        for (int x = 0; x < Constants.MAP_WIDTH; x++) {
            MutableAsciiActor wallActor = createSymbolActor();
            obstacleActors.add(wallActor);
            Wall wall = new Wall(wallActor, x, -1, '═');
            obstacles.add(wall);
        }
        for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
            MutableAsciiActor wallActor = createSymbolActor();
            obstacleActors.add(wallActor);
            Wall wall = new Wall(wallActor, -1, y, '║');
            obstacles.add(wall);
        }
        for (int x = 0; x < Constants.MAP_WIDTH; x++) {
            MutableAsciiActor wallActor = createSymbolActor();
            obstacleActors.add(wallActor);
            Wall wall = new Wall(wallActor, x, Constants.MAP_HEIGHT, '═');
            obstacles.add(wall);
        }
        for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
            MutableAsciiActor wallActor = createSymbolActor();
            obstacleActors.add(wallActor);
            Wall wall = new Wall(wallActor, Constants.MAP_WIDTH, y, '║');
            obstacles.add(wall);
        }

        MutableAsciiActor wallActorTL = createSymbolActor();
        obstacleActors.add(wallActorTL);
        Wall wallTL = new Wall(wallActorTL, -1, -1, '╔');
        obstacles.add(wallTL);

        MutableAsciiActor wallActorTR = createSymbolActor();
        obstacleActors.add(wallActorTR);
        Wall wallTR = new Wall(wallActorTR, Constants.MAP_WIDTH, -1, '╗');
        obstacles.add(wallTR);

        MutableAsciiActor wallActorBL = createSymbolActor();
        obstacleActors.add(wallActorBL);
        Wall wallBL = new Wall(wallActorBL, -1, Constants.MAP_HEIGHT, '╚');
        obstacles.add(wallBL);

        MutableAsciiActor wallActorBR = createSymbolActor();
        obstacleActors.add(wallActorBR);
        Wall wallBR = new Wall(wallActorBR, Constants.MAP_WIDTH, Constants.MAP_HEIGHT, '╝');
        obstacles.add(wallBR);

        everythingExceptPlayer.addAll(obstacleActors);
    }
}
