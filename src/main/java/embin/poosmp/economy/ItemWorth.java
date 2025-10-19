package embin.poosmp.economy;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.economy.shop.ShopCategories;
import embin.poosmp.economy.shop.ShopCategory;
import embin.poosmp.items.PooSMPItems;
import embin.poosmp.items.component.PooSMPItemComponents;
import embin.poosmp.items.component.ValueComponent;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;

public final class ItemWorth {

    public static void setPrice(DefaultItemComponentEvents.ModifyContext modifyContext, ShopCategory category, Item item, double price, double sellPrice) {
        RegistryEntry<ShopCategory> categoryEntry = PooSMPRegistries.SHOP_CATEGORY.getEntry(category);
        modifyContext.modify(item, builder -> builder.add(PooSMPItemComponents.ITEM_VALUE, new ValueComponent(price, sellPrice, categoryEntry)));
    }

    public static void setPrice(DefaultItemComponentEvents.ModifyContext modifyContext, ShopCategory category, Item item, double price) {
        setPrice(modifyContext, category, item, price, price / 2);
    }

    public static void setPrices(DefaultItemComponentEvents.ModifyContext modifyContext) {
        setPrice(modifyContext, ShopCategories.MATERIALS, Items.IRON_INGOT, 4.50);
        setPrice(modifyContext, ShopCategories.MATERIALS, Items.IRON_NUGGET, 0.5, 0.2);
        setPrice(modifyContext, ShopCategories.MATERIALS, Items.GOLD_INGOT, 8.50);
        setPrice(modifyContext, ShopCategories.MATERIALS, Items.GOLD_NUGGET, 0.9, 0.4);
        setPrice(modifyContext, ShopCategories.MATERIALS, Items.DIAMOND, 18);
        setPrice(modifyContext, ShopCategories.MATERIALS, PooSMPItems.DIAMOND_SHARD, 1.5, 1);
    }
}
