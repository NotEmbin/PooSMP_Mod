package embin.poosmp.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnchantingTableBlockEntity.class)
public class TestingMixin {

    @Shadow @Final private static RandomSource RANDOM;

    /**
     * @author Embin
     * @reason the ide yells at me if i don't have this
     */
    @Overwrite()
    public static void bookAnimationTick(Level world, BlockPos pos, BlockState state, EnchantingTableBlockEntity blockEntity) {
        blockEntity.oOpen = blockEntity.open;
        blockEntity.oRot = blockEntity.rot;
        Player playerEntity = world.getNearestPlayer((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 3.0, false);
        if (playerEntity != null && !playerEntity.hasEffect(MobEffects.INVISIBILITY)) {
            double d = playerEntity.getX() - ((double)pos.getX() + 0.5);
            double e = playerEntity.getZ() - ((double)pos.getZ() + 0.5);
            blockEntity.tRot = (float)Mth.atan2(e, d);
            blockEntity.open += 0.1F;
            if (blockEntity.open < 0.5F || RANDOM.nextInt(40) == 0) {
                float f = blockEntity.flipT;

                do {
                    blockEntity.flipT = blockEntity.flipT + (float)(RANDOM.nextInt(4) - RANDOM.nextInt(4));
                } while (f == blockEntity.flipT);
            }
        } else {
            blockEntity.tRot += 0.02F;
            blockEntity.open -= 0.1F;
        }

        while (blockEntity.rot >= (float) Math.PI) {
            blockEntity.rot -= (float) (Math.PI * 2);
        }

        while (blockEntity.rot < (float) -Math.PI) {
            blockEntity.rot += (float) (Math.PI * 2);
        }

        while (blockEntity.tRot >= (float) Math.PI) {
            blockEntity.tRot -= (float) (Math.PI * 2);
        }

        while (blockEntity.tRot < (float) -Math.PI) {
            blockEntity.tRot += (float) (Math.PI * 2);
        }

        float g = blockEntity.tRot - blockEntity.rot;

        while (g >= (float) Math.PI) {
            g -= (float) (Math.PI * 2);
        }

        while (g < (float) -Math.PI) {
            g += (float) (Math.PI * 2);
        }

        blockEntity.rot += g * 0.4F;
        blockEntity.open = Mth.clamp(blockEntity.open, 0.0F, 1.0F);
        blockEntity.time++;
        blockEntity.oFlip = blockEntity.flip;
        float h = (blockEntity.flipT - blockEntity.flip) * 0.4F;
        float i = 0.2F;
        h = Mth.clamp(h, -0.2F, 0.2F);
        blockEntity.flipA = blockEntity.flipA + (h - blockEntity.flipA) * 0.9F;
        blockEntity.flip = blockEntity.flip + blockEntity.flipA;
    }
}
