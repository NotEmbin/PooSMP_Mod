package embin.poosmp.items;

import embin.poosmp.PooSMPItemComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.stat.StatFormatter;
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
            int total_money = stack.getOrDefault(PooSMPItemComponents.MONEY, 0);
            String money_string = String.valueOf(total_money);
            int dollar_value = (int)Math.floor(total_money * 0.01);
            int money_length = money_string.length();
            String cent_value;
            if (money_length >= 3) {
                cent_value = String.valueOf(money_string.charAt(money_length - 2)) + money_string.charAt(money_length - 1);
            } else if (money_length == 1) {
                cent_value = "0" + total_money;
            } else {
                cent_value = String.valueOf(total_money);
            }
            String display_money = "$" + StatFormatter.DEFAULT.format(dollar_value) + "." + cent_value;
            tooltip.add(Text.literal(display_money).formatted(Formatting.GREEN));
        }
    }
}
