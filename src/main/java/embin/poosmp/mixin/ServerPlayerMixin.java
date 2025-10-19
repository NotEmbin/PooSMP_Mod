package embin.poosmp.mixin;

import embin.poosmp.PooSMPMod;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.upgrade.ServerUpgradeData;
import embin.poosmp.upgrade.Upgrade;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin {
    @Shadow public abstract void playSoundToPlayer(SoundEvent sound, SoundCategory category, float volume, float pitch);

    @Shadow public abstract ServerWorld getServerWorld();

    @Shadow public abstract void playerTick();

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickMixin(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        Upgrade.syncData(player);
        for (Identifier id : ServerUpgradeData.INSTANCE.savedUpgrades(player)) {
            Upgrade upgrade = this.getServerWorld().getRegistryManager().get(PooSMPRegistries.Keys.UPGRADE).get(id);
            if (upgrade != null) {
                upgrade.onTick(player);
                if (upgrade.statusEffect().isPresent() && !ServerUpgradeData.INSTANCE.activeEffects.containsKey(player.getUuid())) {
                    if (ServerUpgradeData.INSTANCE.activeEffects.containsKey(player.getUuid())) {
                        if (!ServerUpgradeData.INSTANCE.activeEffects.get(player.getUuid()).containsKey(id)) {
                            ServerUpgradeData.INSTANCE.addStatusEffect(player, id, upgrade.statusEffect().get());
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void copyMixin(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        AttributeContainer attributes = oldPlayer.getAttributes();

        for (EntityAttributeInstance eai : attributes.getAttributesToSend()) {
            for (EntityAttributeModifier modifier : eai.getModifiers()) {
                if (modifier.id().getPath().startsWith("upgrade/")) {
                    if (player.getAttributes().hasAttribute(eai.getAttribute())) {
                        player.getAttributeInstance(eai.getAttribute()).addPersistentModifier(modifier);
                        PooSMPMod.LOGGER.info("Reapplying attribute {} to {} for {}", modifier.id(), player.getNameForScoreboard(), eai.getAttribute().getIdAsString());
                    }
                }
            }
        }
    }
}
