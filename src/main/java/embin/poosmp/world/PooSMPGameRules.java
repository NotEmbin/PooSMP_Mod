package embin.poosmp.world;

import com.mojang.serialization.Codec;
import embin.poosmp.util.Id;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.world.level.gamerules.GameRule;

@SuppressWarnings({"NullableProblems", "SameParameterValue"})
public final class PooSMPGameRules {
    private PooSMPGameRules() {}

    public static final GameRule<Double> STARTING_BALANCE = ofDouble("starting_balance", 32D);

    private static GameRule<Boolean> ofBool(String id, boolean defaultValue) {
        return GameRuleBuilder.forBoolean(defaultValue).codec(Codec.BOOL).buildAndRegister(Id.of(id));
    }

    private static GameRule<Double> ofDouble(String id, double defaultValue) {
        return GameRuleBuilder.forDouble(defaultValue).codec(Codec.doubleRange(0, 32767)).buildAndRegister(Id.of(id));
    }

    public static void acknowledge() {}
}
