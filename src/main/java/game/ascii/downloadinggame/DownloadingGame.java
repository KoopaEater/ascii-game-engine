package game.ascii.downloadinggame;

import actor.ActorConstants;
import actor.ascii.MutableAsciiActor;
import actor.ascii.text.MutableTextAsciiActor;
import game.ascii.AbstractAsciiGame;
import ui.mouse.ClickEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DownloadingGame extends AbstractAsciiGame {

    private static final int MIN_MOVE_DELAY = 2;
    private static final int MAX_MOVE_DELAY = 5;

    private int moveDelay;
    private int timeSinceLastMove;

    private List<MutableAsciiActor> everythingExceptPlayer;
    private List<Obstacle> obstacles;
    private List<Vision> visions;
    private List<Looker> lookers;
    private List<Camera> cameras;
    private List<Human> humans;

    private Player player;
    private Human human;
    private Looker looker;
    private Camera camera;
    private BreathingMeter breathingMeter;
    private Goal goal;

    private long startTime;

    public DownloadingGame() {
        super("Downloading...", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, 10);
    }

    @Override
    public void setup() {

        moveDelay = 2;

        everythingExceptPlayer = new ArrayList<>();
        obstacles = new ArrayList<>();
        visions = new ArrayList<>();
        lookers = new ArrayList<>();
        cameras = new ArrayList<>();
        humans = new ArrayList<>();

        setFontSize(24);
        setBackgroundColor(Color.darkGray);

        player = new Player(this);

//        human = new Human(this, 10, 10, 3);
//        human.setPath(new ArrayList<Vector2D>(
//                List.of(new Vector2D(10,10), new Vector2D(10,15), new Vector2D(15,15), new Vector2D(15,10))
//        ));
//        human.setDirs(new ArrayList<Vector2D>(
//                List.of(new Vector2D(-1,0), new Vector2D(0,1), new Vector2D(1,0), new Vector2D(0,-1))
//        ));
//
//        looker = new Looker(this, 10, 22, new Vector2D(1, 1), 20);
//
//        camera = new Camera(this, '▼', 30, 10, new Vector2D(0,1), 2, 20, 90);
//
        breathingMeter = new BreathingMeter(this);

        goal = new Goal(this, 25, 0);

        generateWalls();
        generateEnemies();

        player.moveToWorldPos(4, 59);

        beginTimer();
    }

    private void beginTimer() {
        startTime = System.currentTimeMillis();
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

        MutableTextAsciiActor deathMessage = createSymbolTextActor(Constants.SCREEN_WIDTH);
        deathMessage.setText("You were caught...");
        deathMessage.setColor(Color.WHITE);
        deathMessage.setOrigin(0, Constants.SCREEN_HEIGHT-3);
        deathMessage.setZ(ActorConstants.Z_OVERLAY+1);
        deathMessage.show();

        MutableTextAsciiActor infoMessage1 = createSymbolTextActor(Constants.SCREEN_WIDTH);
        infoMessage1.setText("Close and open game to");
        infoMessage1.setColor(Color.WHITE);
        infoMessage1.setOrigin(0, Constants.SCREEN_HEIGHT-2);
        infoMessage1.setZ(ActorConstants.Z_OVERLAY+1);
        infoMessage1.show();

        MutableTextAsciiActor infoMessage2 = createSymbolTextActor(Constants.SCREEN_WIDTH);
        infoMessage2.setText("play again");
        infoMessage2.setColor(Color.WHITE);
        infoMessage2.setOrigin(0, Constants.SCREEN_HEIGHT-1);
        infoMessage2.setZ(ActorConstants.Z_OVERLAY+1);
        infoMessage2.show();
    }

    private void showWinScreen() {
        stopGameLoop();

        MutableTextAsciiActor winMessage = createSymbolTextActor(Constants.SCREEN_WIDTH);
        winMessage.setText("You made it!");
        winMessage.setColor(Color.WHITE);
        winMessage.setOrigin(0, Constants.SCREEN_HEIGHT-2);
        winMessage.setZ(ActorConstants.Z_OVERLAY+1);
        winMessage.show();

        MutableTextAsciiActor timeMessage = createSymbolTextActor(Constants.SCREEN_WIDTH);
        timeMessage.setText("Your time: " + getTimeString());
        timeMessage.setColor(Color.WHITE);
        timeMessage.setOrigin(0, Constants.SCREEN_HEIGHT-1);
        timeMessage.setZ(ActorConstants.Z_OVERLAY+1);
        timeMessage.show();

    }

    private String getTimeString() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        return formatTime(elapsedTime);
    }

    // Courtesy of Copilot
    private String formatTime(long millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        long milliseconds = (millis / 100) % 10;
        return String.format("%02d:%02d.%01d", minutes, seconds, milliseconds);
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

    public void addToLookers(Looker looker) {
        lookers.add(looker);
    }

    public void addToCameras(Camera camera) {
        cameras.add(camera);
    }

    public void addToHumans(Human human) {
        humans.add(human);
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

        Vector2D playerPos = getPlayerPosition();
        if (goal.isAt((int) playerPos.x, (int) playerPos.y)) {
            showWinScreen();
        }

        for (Looker looker : lookers) {
            looker.tryLook();
        }

        for (Camera camera : cameras) {
            camera.tryTurn();
        }

        for (Human human : humans) {
            human.tryStep();
        }

//        human.tryStep();
//        looker.tryLook();
//        camera.tryTurn();
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

        // AROUND THE MAP!

        for (int x = 0; x < Constants.MAP_WIDTH; x++) {
            new Wall(this, x, -1, '═');
        }
        for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
            new Wall(this, -1, y, '║');
        }
        for (int x = 0; x < Constants.MAP_WIDTH; x++) {
            new Wall(this, x, Constants.MAP_HEIGHT, '═');
        }
        for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
            new Wall(this, Constants.MAP_WIDTH, y, '║');
        }
        new Wall(this, -1, -1, '╔');
        new Wall(this, Constants.MAP_WIDTH, -1, '╗');
        new Wall(this, -1, Constants.MAP_HEIGHT, '╚');
        new Wall(this, Constants.MAP_WIDTH, Constants.MAP_HEIGHT, '╝');

        // WALL A
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 2; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL B
        for (int x = 14; x <= 21; x++) {
            for (int y = 0; y <= 2; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL C
        for (int x = 29; x <= 29; x++) {
            for (int y = 0; y <= 2; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL D
        for (int x = 0; x <= 2; x++) {
            for (int y = 7; y <= 16; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL E
        for (int x = 7; x <= 11; x++) {
            for (int y = 7; y <= 16; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL F
        for (int x = 17; x <= 21; x++) {
            for (int y = 7; y <= 16; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL G1
        for (int x = 0; x <= 13; x++) {
            for (int y = 22; y <= 26; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL G2
        for (int x = 14; x <= 16; x++) {
            for (int y = 22; y <= 25; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL G3
        for (int x = 17; x <= 21; x++) {
            for (int y = 22; y <= 26; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL H1
        for (int x = 27; x <= 29; x++) {
            for (int y = 22; y <= 37; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL H2
        for (int x = 21; x <= 26; x++) {
            for (int y = 32; y <= 32; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL I1
        for (int x = 9; x <= 9; x++) {
            for (int y = 32; y <= 37; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL I2
        for (int x = 10; x <= 21; x++) {
            for (int y = 37; y <= 37; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL J
        for (int x = 13; x <= 17; x++) {
            for (int y = 32; y <= 32; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL K1
        for (int x = 9; x <= 13; x++) {
            for (int y = 43; y <= 49; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL K2
        for (int x = 14; x <= 16; x++) {
            for (int y = 43; y <= 47; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL K3
        for (int x = 17; x <= 21; x++) {
            for (int y = 43; y <= 49; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL L
        for (int x = 27; x <= 29; x++) {
            for (int y = 43; y <= 49; y++) {
                new Wall(this, x, y, '█');
            }
        }

        // WALL M
        for (int x = 9; x <= 29; x++) {
            for (int y = 55; y <= 59; y++) {
                new Wall(this, x, y, '█');
            }
        }
    }

    private void generateEnemies() {

        // LOOKERS
        new Looker(this, 15, 48, new Vector2D(0, 1), 20, 8);
        new Looker(this, 8, 46, new Vector2D(-1, 0), 30, 9);
        new Looker(this, 15, 26, new Vector2D(0, 1), 15, 6);
        new Looker(this, 2, 11, new Vector2D(1, 0), 15, 8);
        new Looker(this, 9, 0, new Vector2D(0, 1), 30, 8);

        // CAMERAS
        new Camera(this, '▼', 0, 27, new Vector2D(1, 1), 2, 10, 60, 45, 10);
        new Camera(this, '◄', 29, 52, new Vector2D(-1, 0), 3, 25, 45, 45, 10);
        new Camera(this, '◄', 29, 14, new Vector2D(-1, 0), 4, 30, 60, 40, 8);
        new Camera(this, '►', 22, 7, new Vector2D(1, 0), 4, 20, 60, 40, 8);

        // HUMANS
        Human h1 = new Human(this, 28, 40, 3, 8);
        h1.setDirection(new Vector2D(-1, 0));
        h1.setPath(List.of(new Vector2D(28, 40), new Vector2D(1, 40)));
        h1.setDirs(List.of(new Vector2D(1, 0), new Vector2D(-1, 0)));

        Human h2 = new Human(this, 13, 35, 6, 5);
        h2.setDirection(new Vector2D(1, 0));
        h2.setPath(List.of(new Vector2D(13, 35), new Vector2D(24, 35)));
        h2.setDirs(List.of(new Vector2D(-1, 0), new Vector2D(1, 0)));

        Human h3 = new Human(this, 14, 4, 2, 8);
        h3.setDirection(new Vector2D(0, 1));
        h3.setPath(List.of(new Vector2D(14, 4), new Vector2D(14, 15)));
        h3.setDirs(List.of(new Vector2D(0, -1), new Vector2D(0, 1)));
    }
}
