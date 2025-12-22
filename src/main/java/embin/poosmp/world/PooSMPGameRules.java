package embin.poosmp.world;

import com.mojang.serialization.Codec;
import embin.poosmp.util.Id;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.world.level.gamerules.GameRule;

@SuppressWarnings({"NullableProblems", "SameParameterValue"})
public final class PooSMPGameRules {
    private PooSMPGameRules() {}

    public static final GameRule<Boolean> SHOW_BALANCE = ofBool("show_balance", false);

    private static GameRule<Boolean> ofBool(String id, boolean defaultValue) {
        return GameRuleBuilder.forBoolean(defaultValue).codec(Codec.BOOL).buildAndRegister(Id.of(id));
    }

    public static void acknowledge() {}
}
