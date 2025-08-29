package embin.poosmp.mixin;

import embin.poosmp.client.ClientUpgradeData;
import embin.poosmp.upgrade.ServerUpgradeData;
import embin.poosmp.util.IEntityDataSaver;
import embin.poosmp.util.Id;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityDataSaveMixin implements IEntityDataSaver {
    private NbtCompound persistentData;

    @Override
    public NbtCompound poosmpmod$getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new NbtCompound();
            this.persistentData.putBoolean("dummydata", true);
        }
        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWrite(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        NbtCompound saveData = new NbtCompound();
        for (Identifier upgrade : ServerUpgradeData.INSTANCE.savedUpgrades()) {
            saveData.putInt(upgrade.toString(), ServerUpgradeData.INSTANCE.getPurchasedAmount(upgrade));
        }
        nbt.put("poosmp:upgrades", saveData);
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectRead(NbtCompound nbt, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        if (nbt.contains("poosmp:upgrades", 10)) {
            NbtCompound upgradeData = nbt.getCompound("poosmp:upgrades");
            for (String upgradeId : upgradeData.getKeys()) {
                Identifier id = Id.of(upgradeId);
                if (!entity.getWorld().isClient()) {
                    ServerUpgradeData.INSTANCE.setPurchasedAmount(id, upgradeData.getInt(upgradeId));
                } else if (entity instanceof ClientPlayerEntity) {
                    ClientUpgradeData.INSTANCE.setPurchasedAmount(id, upgradeData.getInt(upgradeId));
                }
            }
        }
    }
}
