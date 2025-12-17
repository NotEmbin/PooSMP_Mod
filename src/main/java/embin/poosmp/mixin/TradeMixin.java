package embin.poosmp.mixin;

import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantOffer.class)
public class TradeMixin {
	@Shadow private @Final @Mutable int maxUses;
	@Shadow private int demand;
	@Inject(at = @At("RETURN"), method = "<init>*")
	public void setAlmostInfiniteTrades(CallbackInfo info) { this.maxUses = 999999; this.demand = 0; }
	@Inject(at = @At("RETURN"), method = "updateDemand")
	public void getRidOfDemand(CallbackInfo info) { this.demand = 0; }
}