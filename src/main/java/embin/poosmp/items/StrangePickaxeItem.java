package embin.poosmp.items;

import embin.poosmp.items.component.PooSMPItemComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.item.PickaxeItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import java.util.List;

public class StrangePickaxeItem extends PickaxeItem {
    public StrangePickaxeItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
        int count = stack.getOrDefault(PooSMPItemComponents.BLOCKS_MINED, 0);
        stack.set(PooSMPItemComponents.BLOCKS_MINED, count + 1);
        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        int count = stack.getOrDefault(PooSMPItemComponents.BLOCKS_MINED, 0);
        tooltip.add(Component.translatable("tooltip.poosmp.blocks_mined", count));
    }

    @Override
    public Component getName() {
        return Component.translatable(this.getTranslationKey()).formatted(ChatFormatting.GOLD);
    }
}
