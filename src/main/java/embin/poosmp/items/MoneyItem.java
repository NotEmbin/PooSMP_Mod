package embin.poosmp.items;

import embin.poosmp.items.component.PooSMPItemComponents;
import java.util.function.Consumer;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

public class MoneyItem extends Item {
    public MoneyItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltipDisplay, consumer, type);
        if (stack.has(PooSMPItemComponents.MONEY)) {
            double total_money = stack.getOrDefault(PooSMPItemComponents.MONEY, 0.0D);
            String display_money = "$" + total_money;
            consumer.accept(Component.literal(display_money).withStyle(ChatFormatting.GREEN));
        }
    }
}
