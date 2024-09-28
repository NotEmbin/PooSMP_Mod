package embin.poosmp.items;

import embin.poosmp.util.PooSMPTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Objects;

public class RequestedDiscItem extends Item {
    private final String requester;
    public RequestedDiscItem(String requester, Settings settings) {
        super(settings);
        this.requester = requester;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (stack.isIn(PooSMPTags.Items.STEREO_DISCS)) {
            tooltip.add(Text.literal("This song is stereo, so the sound created by").formatted(Formatting.LIGHT_PURPLE, Formatting.ITALIC));
            tooltip.add(Text.literal("the jukebox will not be played locationally.").formatted(Formatting.LIGHT_PURPLE, Formatting.ITALIC));
        }
        if (!Objects.equals(this.requester, "Embin")) {
            tooltip.add(Text.literal("Requested by ").append(this.requester).formatted(Formatting.GRAY));
        }
    }
}
