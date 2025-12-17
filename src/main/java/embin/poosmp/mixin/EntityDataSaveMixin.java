package embin.poosmp.mixin;

import embin.poosmp.client.ClientUpgradeData;
import embin.poosmp.upgrade.ServerUpgradeData;
import embin.poosmp.util.IEntityDataSaver;
import embin.poosmp.util.Id;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityDataSaveMixin implements IEntityDataSaver {
    private CompoundTag persistentData;

    @Override
    public CompoundTag poosmpmod$getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new CompoundTag();
            this.persistentData.putBoolean("dummydata", true);
        }
        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWrite(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> cir) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof ServerPlayer serverPlayerEntity) {
            CompoundTag upgradeData = new CompoundTag();
            for (Identifier upgrade : ServerUpgradeData.INSTANCE.savedUpgrades(serverPlayerEntity)) {
                upgradeData.putInt(upgrade.toString(), ServerUpgradeData.INSTANCE.getPurchasedAmount(serverPlayerEntity, upgrade));
            }
            nbt.put("poosmp:upgrades", upgradeData);
            nbt.putDouble("poosmp:balance", ServerUpgradeData.INSTANCE.getBalance(serverPlayerEntity));
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectRead(CompoundTag nbt, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        if (nbt.contains("poosmp:upgrades", Tag.TAG_COMPOUND)) {
            CompoundTag upgradeData = nbt.getCompound("poosmp:upgrades");
            for (String upgradeId : upgradeData.keySet()) {
                Identifier id = Id.of(upgradeId);
                if (!entity.getWorld().isClient() && entity instanceof ServerPlayer serverPlayerEntity) {
                    ServerUpgradeData.INSTANCE.setPurchasedAmount(serverPlayerEntity, id, upgradeData.getInt(upgradeId));
                }
            }
        }
        if (nbt.contains("poosmp:balance", Tag.TAG_DOUBLE)) {
            double balance = nbt.getDouble("poosmp:balance");
            if (!entity.getWorld().isClient() && entity instanceof ServerPlayer serverPlayerEntity) {
                ServerUpgradeData.INSTANCE.setBalance(serverPlayerEntity, balance);
            }
        }
    }
}
