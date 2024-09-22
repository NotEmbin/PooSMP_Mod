package embin.poosmp.mixin;

import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TradeOffer.class)
public class ExampleMixin {
	@Shadow private @Final @Mutable int maxUses;
	@Shadow private int demandBonus;
	@Inject(at = @At("RETURN"), method = "<init>*")
	public void setAlmostInfiniteTrades(CallbackInfo info) { this.maxUses = 999999; this.demandBonus = 0; }
	@Inject(at = @At("RETURN"), method = "updateDemandBonus")
	public void getRidOfDemand(CallbackInfo info) { this.demandBonus = 0; }
}