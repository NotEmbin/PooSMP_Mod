package embin.poosmp.block;

import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

public class ddededodediamanteBlock extends Block {
    public static final MapCodec<ddededodediamanteBlock> CODEC = simpleCodec(ddededodediamanteBlock::new);

    @Override
    public MapCodec<? extends ddededodediamanteBlock> codec() {
        return CODEC;
    }

    public ddededodediamanteBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        super.appendTooltip(stack, context, tooltip, options);
        tooltip.add(Component.translatable("block.poosmp.ddededodediamante_block.desc").withStyle(ChatFormatting.LIGHT_PURPLE));
    }
}
