package embin.poosmp.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class RequestedDiscItem extends Item {
    private final String requester;
    public RequestedDiscItem(String requester, Settings settings) {
        super(settings);
        this.requester = requester;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.literal("Requested by ").append(this.requester).formatted(Formatting.GRAY));
    }
}
