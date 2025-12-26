package embin.poosmp.block.annoyance;

import net.minecraft.sounds.SoundEvent;

public class Annoyance {
    private final SoundEvent annoyanceSound;
    private final float volume;
    private final float pitch;
    private final int chance;

    public Annoyance(SoundEvent soundEvent, float volume, float pitch, int chance) {
        this.annoyanceSound = soundEvent;
        this.volume = volume;
        this.pitch = pitch;
        this.chance = chance;
    }

    public SoundEvent getSound() {
        return this.annoyanceSound;
    }

    public float getVolume() {
        return this.volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    /**
     * Value is a percentage in integer form, i.e. {@code 75} representing 75%.
     */
    public int getChance() {
        return this.chance;
    }
}
