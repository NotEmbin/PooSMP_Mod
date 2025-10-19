package embin.poosmp.items.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.economy.shop.ShopCategory;
import embin.poosmp.util.PooUtil;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.Consumer;

public record ValueComponent(double buyValue, double sellValue, RegistryEntry<ShopCategory> category) implements TooltipAppender {
    public static final double MAX_PRICE = 500_000_000D;
    public static final Codec<ValueComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.doubleRange(0, MAX_PRICE).fieldOf("buy_price").forGetter(ValueComponent::buyValue),
            Codec.doubleRange(-1, MAX_PRICE).fieldOf("sell_price").forGetter(ValueComponent::sellValue),
            PooSMPRegistries.SHOP_CATEGORY.getEntryCodec().fieldOf("shop_category").forGetter(ValueComponent::category)
    ).apply(builder, ValueComponent::new));

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        if (type.isAdvanced()) {
            tooltip.accept(Text.translatable("tooltip.poosmp.item_value.sell_price", PooUtil.roundTwo(this.sellValue)).formatted(Formatting.GRAY));
        }
    }

    public boolean canBeSold() {
        return this.sellValue >= 0;
    }
}
