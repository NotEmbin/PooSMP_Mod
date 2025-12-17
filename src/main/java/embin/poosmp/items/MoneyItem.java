package embin.poosmp.items;

import embin.poosmp.items.component.PooSMPItemComponents;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class MoneyItem extends Item {
    public MoneyItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (stack.has(PooSMPItemComponents.MONEY)) {
            double total_money = stack.getOrDefault(PooSMPItemComponents.MONEY, 0.0D);
            String display_money = "$" + total_money;
            tooltip.add(Component.literal(display_money).withStyle(ChatFormatting.GREEN));
        }
    }
}
