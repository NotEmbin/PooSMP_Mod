package embin.poosmp.block;

import com.mojang.serialization.MapCodec;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.PooSMPSoundEvents;
import embin.poosmp.block.annoyance.Annoyance;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AnnoyanceBlock extends Block {
    private final Annoyance annoyance;

    public AnnoyanceBlock(Annoyance annoyance, BlockBehaviour.Properties settings) {
        super(settings);
        this.annoyance = annoyance;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!world.isClientSide) {
            BlockPos blockPos = hit.getBlockPos();
            float v = this.annoyance.getVolume() * 1.5F;
            float p = this.annoyance.getPitch() * 1.5F;
            world.playSound(null, blockPos, this.annoyance.getSound(), SoundSource.BLOCKS, v, p);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextIntBetweenInclusive(1, 100) <= this.annoyance.getChance()) {
            world.playSound(null, pos, this.annoyance.getSound(), SoundSource.BLOCKS, this.annoyance.getVolume(), this.annoyance.getPitch());
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        if (options.isAdvanced()) {
            Component text = Component.literal("Annoyance: ").append(PooSMPRegistries.ANNOYANCE.getKey(this.annoyance).toString()).withStyle(ChatFormatting.DARK_GRAY);
            tooltip.add(text);
        }
    }
}
