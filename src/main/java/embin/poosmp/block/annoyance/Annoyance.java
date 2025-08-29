package embin.poosmp.block.annoyance;

import net.minecraft.sound.SoundEvent;

public class Annoyance {
    private final SoundEvent annoyanceSound;

    public Annoyance(SoundEvent soundEvent) {
        this.annoyanceSound = soundEvent;
    }

    public SoundEvent getSound() {
        return this.annoyanceSound;
    }
}
