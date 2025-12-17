package embin.poosmp.mixin;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    public void damageMixin(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cl) {
        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            cl.setReturnValue(false);
        }
    }
}
