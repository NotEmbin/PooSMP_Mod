package embin.poosmp.mixin;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.upgrade.ServerUpgradeData;
import embin.poosmp.upgrade.Upgrade;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickMixin(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer)(Object)this;
        Upgrade.syncData(player);
        for (Identifier id : ServerUpgradeData.INSTANCE.savedUpgrades(player)) {
            Upgrade upgrade = player.registryAccess().lookupOrThrow(PooSMPRegistries.Keys.UPGRADE).getValue(id);
            if (upgrade != null) {
                upgrade.onTick(player);
                if (upgrade.statusEffect().isPresent() && !ServerUpgradeData.INSTANCE.activeEffects.containsKey(player.getUUID())) {
                    if (ServerUpgradeData.INSTANCE.activeEffects.containsKey(player.getUUID())) {
                        if (!ServerUpgradeData.INSTANCE.activeEffects.get(player.getUUID()).containsKey(id)) {
                            ServerUpgradeData.INSTANCE.addStatusEffect(player, id, upgrade.statusEffect().get());
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void copyMixin(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer)(Object)this;
        AttributeMap attributes = oldPlayer.getAttributes();

        for (AttributeInstance eai : attributes.getSyncableAttributes()) {
            for (AttributeModifier modifier : eai.getModifiers()) {
                if (modifier.id().getPath().startsWith("upgrade/")) {
                    if (player.getAttributes().hasAttribute(eai.getAttribute())) {
                        player.getAttribute(eai.getAttribute()).addPermanentModifier(modifier);
                    }
                }
            }
        }
    }
}
