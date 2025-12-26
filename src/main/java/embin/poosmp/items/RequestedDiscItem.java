package embin.poosmp.items;

import embin.poosmp.util.PooSMPTags;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

public class RequestedDiscItem extends Item {
    private final String requester;
    public RequestedDiscItem(String requester, Properties settings) {
        super(settings);
        this.requester = requester;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltipDisplay, consumer, type);
        if (stack.is(PooSMPTags.Items.STEREO_DISCS)) {
            consumer.accept(Component.literal("This song is stereo, so the sound created by").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
            consumer.accept(Component.literal("the jukebox will not be played locationally.").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
        }
        if (!Objects.equals(this.requester, "Embin")) {
            consumer.accept(Component.literal("Requested by ").append(this.requester).withStyle(ChatFormatting.GRAY));
        }
    }
}
