package embin.poosmp.mixin;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.upgrade.ServerUpgradeData;
import embin.poosmp.upgrade.Upgrade;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void tickMixin(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        Upgrade.syncData(player);
        for (Identifier id : ServerUpgradeData.INSTANCE.savedUpgrades()) {
            Upgrade upgrade = PooSMPRegistries.UPGRADE.get(id);
            if (upgrade != null) {
                upgrade.onTick(player);
                if (upgrade.statusEffect().isPresent() && !ServerUpgradeData.INSTANCE.activeEffects.containsKey(id)) {
                    ServerUpgradeData.INSTANCE.addStatusEffect(id, upgrade.statusEffect().get());
                }
            }
        }
    }
}
