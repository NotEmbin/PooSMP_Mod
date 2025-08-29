package embin.poosmp.block;

import com.mojang.serialization.MapCodec;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.PooSMPSoundEvents;
import embin.poosmp.block.annoyance.Annoyance;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class AnnoyanceBlock extends Block {
    private final Annoyance annoyance;

    public AnnoyanceBlock(Annoyance annoyance, AbstractBlock.Settings settings) {
        super(settings);
        this.annoyance = annoyance;
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            float v = this.annoyance.getVolume() * 1.5F;
            float p = this.annoyance.getPitch() * 1.5F;
            world.playSound(null, blockPos, this.annoyance.getSound(), SoundCategory.BLOCKS, v, p);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextBetween(1, 100) <= this.annoyance.getChance()) {
            world.playSound(null, pos, this.annoyance.getSound(), SoundCategory.BLOCKS, this.annoyance.getVolume(), this.annoyance.getPitch());
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if (options.isAdvanced()) {
            Text text = Text.literal("Annoyance: ").append(PooSMPRegistries.ANNOYANCE.getId(this.annoyance).toString()).formatted(Formatting.DARK_GRAY);
            tooltip.add(text);
        }
    }
}
