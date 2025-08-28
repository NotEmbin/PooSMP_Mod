package embin.poosmp.mixin;

import embin.poosmp.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
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
        }
        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWrite(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (persistentData != null) {
            nbt.put("poosmp.data", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectRead(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("poosmp.data", 10)) {
            persistentData = nbt.getCompound("poosmp.data");
        }
    }
}
