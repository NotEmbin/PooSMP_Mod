package embin.poosmp.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damageMixin(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cl) {
        if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
            cl.setReturnValue(false);
        }
    }
}
