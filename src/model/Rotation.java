package model;

import java.util.Random;

/**
 * The Rotation class, which enumerates any possible rotation that
 * a given Tetris piece can perform.
 * 
 * DONE
 * 
 * @author Logan Stafford
 * @version 1.2
 */
public enum Rotation {

    /**
     * The original starting rotation.
     */
    START,

    /**
     * A quarter rotation (90 degrees).
     */
    QUARTER,

    /**
     * A half rotation (180 degrees).
     */
    HALF,

    /**
     * A three-quarters rotation (270 degrees).
     */
    THREEQUARTER;

    /**
     * A Random object used for generating random rotations.
     */
    private static final Random RANDOM = new Random();

    /**
     * Create a new Rotation that is rotated 90 degrees clockwise
     * as compared to the current one.
     * 
     * @return A new Rotation that is rotated 90 degrees clockwise.
     */
    public Rotation clockwise() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    /**
     * Creates a new Rotation with a random angle.
     * 
     * @return A new Rotation object with a random angle.
     */
    public static Rotation random() {
        return values()[RANDOM.nextInt(values().length)];
    }

}