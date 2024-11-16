package game.ascii.downloadinggame;

import actor.ActorConstants;
import actor.ascii.text.MutableTextAsciiActor;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BreathingMeter {

    private static final int SIZE = 11;
    public static final int MAX_BREATH_RATE = 16;
    public static final int MIN_BREATH_RATE = 10;
    private static final int PENALTY_THRESHOLD = 3;
    private static final int BONUS_THRESHOLD = 1;

    private DownloadingGame game;
    private MutableTextAsciiActor row1, row2, mainRow, row4, row5;

    private char[] breathSymbols;
    private int breathRate, breathCount;
    private BreathState meterBreathState;
    private List<Integer> breathInTimings, breathOutTimings;
    private List<Integer> soundInTimings, soundOutTimings;

    private Clip breatheInSound, breatheOutSound;

    public BreathingMeter(DownloadingGame game) {
        this.game = game;

        breathSymbols = new char[SIZE];
        breathRate = MAX_BREATH_RATE;
        breathCount = 0;
        meterBreathState = BreathState.OUT;
        breathInTimings = new LinkedList<>();
        breathOutTimings = new LinkedList<>();
        soundInTimings = new LinkedList<>();
        soundOutTimings = new LinkedList<>();

        loadSoundFiles();

        row1 = game.createSymbolTextActor(SIZE + 2);
        row2 = game.createSymbolTextActor(SIZE + 2);
        mainRow = game.createSymbolTextActor(SIZE + 2);
        row4 = game.createSymbolTextActor(SIZE + 2);
        row5 = game.createSymbolTextActor(SIZE + 2);

        row1.setColor(Color.white);
        row2.setColor(Color.white);
        mainRow.setColor(Color.white);
        row4.setColor(Color.white);
        row5.setColor(Color.white);

        row1.setZ(ActorConstants.Z_OVERLAY);
        row2.setZ(ActorConstants.Z_OVERLAY);
        mainRow.setZ(ActorConstants.Z_OVERLAY);
        row4.setZ(ActorConstants.Z_OVERLAY);
        row5.setZ(ActorConstants.Z_OVERLAY);

        row1.setOrigin(Constants.SCREEN_WIDTH - 13, 0);
        row2.setOrigin(Constants.SCREEN_WIDTH - 13, 1);
        mainRow.setOrigin(Constants.SCREEN_WIDTH - 13, 2);
        row4.setOrigin(Constants.SCREEN_WIDTH - 13, 3);
        row5.setOrigin(Constants.SCREEN_WIDTH - 13, 4);

        row1.setText("┌─────┬─────┐");
        row2.setText("│     │     │");
        row4.setText("│     │     │");
        row5.setText("└─────┴─────┘");

        row1.show();
        row2.show();
        mainRow.show();
        row4.show();
        row5.show();

        initBreathSymbols();

    }

    private void loadSoundFiles() {
        try {
            // Load the sound file from the resources folder
            InputStream inputStreamIn = BreathState.class.getResourceAsStream("/sounds/breathe-in.wav");
            if (inputStreamIn == null) {
                System.out.println("File not found!");
                return;
            }
            // Get an audio input stream
            AudioInputStream audioInIn = AudioSystem.getAudioInputStream(inputStreamIn);
            // Get a sound clip resource
            breatheInSound = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream
            breatheInSound.open(audioInIn);

            // Load the sound file from the resources folder
            InputStream inputStreamOut = BreathState.class.getResourceAsStream("/sounds/breathe-in.wav");
            if (inputStreamOut == null) {
                System.out.println("File not found!");
                return;
            }
            // Get an audio input stream
            AudioInputStream audioInOut = AudioSystem.getAudioInputStream(inputStreamOut);
            // Get a sound clip resource
            breatheOutSound = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream
            breatheOutSound.open(audioInOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initBreathSymbols() {
        for (int i = 0; i < SIZE; i++) {
            breathSymbols[i] = ' ';
        }
        for (int i = 0; i < SIZE; i++) {
            tick();
        }
    }

    private void handleBreathScore(int breathScore) {
        System.out.println(breathScore);
        if (breathScore <= BONUS_THRESHOLD) {
            increaseBreathRate();
        }
        if (breathScore >= PENALTY_THRESHOLD) {
            decreaseBreathRate();
        }
    }

    private void increaseBreathRate() {
        if (breathRate > MAX_BREATH_RATE) {
            return;
        }
        breathRate++;
        game.adjustMoveDelayFromBreathRate(breathRate);
    }

    private void decreaseBreathRate() {
        if (breathRate < MIN_BREATH_RATE) {
            return;
        }
        breathRate--;
        game.adjustMoveDelayFromBreathRate(breathRate);
    }

    private void increaseBreathTimings() {
        breathInTimings = breathInTimings.stream().map(t -> t+1).collect(Collectors.toList());
        breathOutTimings = breathOutTimings.stream().map(t -> t+1).collect(Collectors.toList());
        if (breathInTimings.stream().filter(t -> t>=0).count() >= 2) { // You missed a timing
            breathInTimings.removeFirst();
            decreaseBreathRate();
        }
        if (breathOutTimings.stream().filter(t -> t>=0).count() >= 2) { // You missed a timing
            breathOutTimings.removeFirst();
            decreaseBreathRate();
        }
    }

    private void increaseSoundTimings() {
        soundInTimings = soundInTimings.stream().map(t -> t+1).collect(Collectors.toList());
        soundOutTimings = soundOutTimings.stream().map(t -> t+1).collect(Collectors.toList());

        if (!soundInTimings.isEmpty() && soundInTimings.getFirst() >= -1) {
            breatheInSound.setFramePosition(0);
            breatheInSound.start();
            soundInTimings.removeFirst();
        }

        if (!soundOutTimings.isEmpty() && soundOutTimings.getFirst() >= -1) {
            breatheOutSound.setFramePosition(0);
            breatheOutSound.start();
            soundOutTimings.removeFirst();
        }
    }

    private void tick() {
        breathCount++;
        increaseBreathTimings();
        increaseSoundTimings();

        if (breathCount >= breathRate) {
            breathCount = 0;
            if (meterBreathState == BreathState.IN) {
                meterBreathState = BreathState.OUT;
                breathOutTimings.add(-(SIZE-1)/2);
                soundOutTimings.add(-(SIZE-1)/2);
            } else {
                meterBreathState = BreathState.IN;
                breathInTimings.add(-(SIZE-1)/2);
                soundInTimings.add(-(SIZE-1)/2);
            }
        }

        for (int i = 0; i < SIZE - 1; i++) {
            breathSymbols[i] = breathSymbols[i + 1];
        }

        if (meterBreathState == BreathState.IN) {
            breathSymbols[SIZE - 1] = '▀';
        } else {
            breathSymbols[SIZE - 1] = '▄';
        }

        String asString = new String(breathSymbols);
        mainRow.setText("│" + asString + "│");
    }

    public void tryTick() {
        tick();
    }

    public void breathIn() {
        if (breathInTimings.isEmpty()) {
            decreaseBreathRate();
            return;
        }
        int breathScore = Math.abs(breathInTimings.getFirst());
        breathInTimings.removeFirst();
        handleBreathScore(breathScore);
    }

    public void breathOut() {
        if (breathOutTimings.isEmpty()) {
            decreaseBreathRate();
            return;
        }
        int breathScore = Math.abs(breathOutTimings.getFirst());
        breathOutTimings.removeFirst();
        handleBreathScore(breathScore);
    }
}
