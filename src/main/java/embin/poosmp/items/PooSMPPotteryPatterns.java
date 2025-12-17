package embin.poosmp.items;

import embin.poosmp.PooSMPMod;
import embin.poosmp.util.Id;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

public class PooSMPPotteryPatterns {
    // aaaaaaaAAAAAAAAAAAAAAAAAAA
    public static void init() {
        PooSMPMod.LOGGER.info("Creating PooSMP Mod Pottery Patterns, but they don't work...");
    }

    private static DecoratedPotPattern register(String name) {
        Identifier pot_id = Id.of(name);
        Identifier pot_pattern_id = Id.of(name + "_pottery_pattern");
        return Registry.register(BuiltInRegistries.DECORATED_POT_PATTERN, pot_id, new DecoratedPotPattern(pot_pattern_id));
    }

    public static final DecoratedPotPattern CUBE = register("cube");
    public static final DecoratedPotPattern POO = register("poo");

}
