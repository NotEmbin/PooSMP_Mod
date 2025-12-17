package embin.poosmp.items;

import embin.poosmp.util.PooSMPTags;
import java.util.List;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class RequestedDiscItem extends Item {
    private final String requester;
    public RequestedDiscItem(String requester, Properties settings) {
        super(settings);
        this.requester = requester;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (stack.is(PooSMPTags.Items.STEREO_DISCS)) {
            tooltip.add(Component.literal("This song is stereo, so the sound created by").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
            tooltip.add(Component.literal("the jukebox will not be played locationally.").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
        }
        if (!Objects.equals(this.requester, "Embin")) {
            tooltip.add(Component.literal("Requested by ").append(this.requester).withStyle(ChatFormatting.GRAY));
        }
    }
}
