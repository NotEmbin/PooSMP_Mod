package embin.poosmp.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class ddededodediamanteBlock extends Block implements BlockWithTooltip {
    public static final MapCodec<ddededodediamanteBlock> CODEC = simpleCodec(ddededodediamanteBlock::new);

    @Override
    public MapCodec<? extends ddededodediamanteBlock> codec() {
        return CODEC;
    }

    public ddededodediamanteBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag type) {
        consumer.accept(Component.translatable("block.poosmp.ddededodediamante_block.desc").withStyle(ChatFormatting.LIGHT_PURPLE));
    }
}
