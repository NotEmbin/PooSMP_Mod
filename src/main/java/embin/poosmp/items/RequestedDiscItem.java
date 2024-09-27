package embin.poosmp.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class RequestedDiscItem extends Item {
    private final String requester;
    private final boolean stereo;
    public RequestedDiscItem(String requester, Settings settings, boolean stereo) {
        super(settings);
        this.requester = requester;
        this.stereo = stereo;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (this.stereo) {
            tooltip.add(Text.literal("This song is stereo, so the sound created by").formatted(Formatting.LIGHT_PURPLE, Formatting.ITALIC));
            tooltip.add(Text.literal("the jukebox will not be played locationally.").formatted(Formatting.LIGHT_PURPLE, Formatting.ITALIC));
        }
        tooltip.add(Text.literal("Requested by ").append(this.requester).formatted(Formatting.GRAY));
    }
}
