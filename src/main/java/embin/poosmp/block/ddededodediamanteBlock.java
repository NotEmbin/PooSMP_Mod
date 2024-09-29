package embin.poosmp.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class ddededodediamanteBlock extends Block {
    public static final MapCodec<ddededodediamanteBlock> CODEC = createCodec(ddededodediamanteBlock::new);

    @Override
    public MapCodec<? extends ddededodediamanteBlock> getCodec() {
        return CODEC;
    }

    public ddededodediamanteBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        super.appendTooltip(stack, context, tooltip, options);
        tooltip.add(Text.translatable("block.poosmp.ddededodediamante_block.desc").formatted(Formatting.LIGHT_PURPLE));
    }
}
