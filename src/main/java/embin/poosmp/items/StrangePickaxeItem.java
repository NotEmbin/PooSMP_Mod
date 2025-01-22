package embin.poosmp.items;

import embin.poosmp.PooSMPItemComponents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class StrangePickaxeItem extends PickaxeItem {
    public StrangePickaxeItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        int count = stack.getOrDefault(PooSMPItemComponents.BLOCKS_MINED, 0);
        stack.set(PooSMPItemComponents.BLOCKS_MINED, count + 1);
        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        int count = stack.getOrDefault(PooSMPItemComponents.BLOCKS_MINED, 0);
        tooltip.add(Text.translatable("tooltip.poosmp.blocks_mined", count));
    }

    @Override
    public Text getName() {
        return Text.translatable(this.getTranslationKey()).formatted(Formatting.GOLD);
    }
}
