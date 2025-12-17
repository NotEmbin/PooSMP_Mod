package embin.poosmp.items.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.economy.shop.ShopCategory;
import embin.poosmp.util.PooUtil;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

public record ValueComponent(double buyValue, double sellValue, Holder<ShopCategory> category) implements TooltipProvider {
    public static final double MAX_PRICE = 500_000_000D;
    public static final Codec<ValueComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.doubleRange(0, MAX_PRICE).fieldOf("buy_price").forGetter(ValueComponent::buyValue),
            Codec.doubleRange(-1, MAX_PRICE).fieldOf("sell_price").forGetter(ValueComponent::sellValue),
            PooSMPRegistries.SHOP_CATEGORY.holderByNameCodec().fieldOf("shop_category").forGetter(ValueComponent::category)
    ).apply(builder, ValueComponent::new));

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag type) {
        if (type.isAdvanced()) {
            tooltip.accept(Component.translatable("tooltip.poosmp.item_value.sell_price", PooUtil.roundTwo(this.sellValue)).withStyle(ChatFormatting.GRAY));
        }
    }

    public boolean canBeSold() {
        return this.sellValue >= 0;
    }
}
