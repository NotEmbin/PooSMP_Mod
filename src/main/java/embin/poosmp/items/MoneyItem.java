package embin.poosmp.items;

import embin.poosmp.items.component.PooSMPItemComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class MoneyItem extends Item {
    public MoneyItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (stack.contains(PooSMPItemComponents.MONEY)) {
            double total_money = stack.getOrDefault(PooSMPItemComponents.MONEY, 0.0D);
            String display_money = "$" + total_money;
            tooltip.add(Text.literal(display_money).formatted(Formatting.GREEN));
        }
    }
}
