package embin.poosmp.economy;

import embin.poosmp.economy.shop.ShopCategories;
import embin.poosmp.economy.shop.ShopCategory;
import embin.poosmp.items.PooSMPItems;
import embin.poosmp.items.component.PooSMPItemComponents;
import embin.poosmp.items.component.ValueComponent;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class ItemWorth {

    public static void setPrice(DefaultItemComponentEvents.ModifyContext modifyContext, ShopCategory category, Item item, double price, double sellPrice) {
        modifyContext.modify(item, builder -> builder.set(PooSMPItemComponents.ITEM_VALUE, new ValueComponent(price, sellPrice, category)));
    }

    public static void setPrice(DefaultItemComponentEvents.ModifyContext modifyContext, ShopCategory category, Item item, double price) {
        setPrice(modifyContext, category, item, price, price / 2D);
    }

    public static void setPrices(DefaultItemComponentEvents.ModifyContext context) {
        setPrice(context, ShopCategories.MATERIALS, Items.IRON_INGOT, 4.50);
        setPrice(context, ShopCategories.MATERIALS, Items.IRON_NUGGET, 0.5, 0.2);
        setPrice(context, ShopCategories.MATERIALS, Items.GOLD_INGOT, 8.50);
        setPrice(context, ShopCategories.MATERIALS, Items.GOLD_NUGGET, 0.9, 0.4);
        setPrice(context, ShopCategories.MATERIALS, Items.DIAMOND, 18);
        setPrice(context, ShopCategories.MATERIALS, PooSMPItems.DIAMOND_SHARD, 1.5, 1);
        setPrice(context, ShopCategories.MATERIALS, Items.COPPER_INGOT, 2.25);
        setPrice(context, ShopCategories.MATERIALS, Items.COPPER_NUGGET, 0.25);
        setPrice(context, ShopCategories.NATURE, Items.GRASS_BLOCK, 0.3, 0.05);
        setPrice(context, ShopCategories.NATURE, Items.DIRT, 0.2, 0.02);
        setPrice(context, ShopCategories.NATURE, Items.STONE, 0.25, 0.03);
        setPrice(context, ShopCategories.NATURE, Items.NETHERRACK, 0.55, 0.1);
        setPrice(context, ShopCategories.NATURE, Items.END_STONE, 1.5, 1);
        setPrice(context, ShopCategories.NATURE, Items.COBBLESTONE, 0.25, 0.03);
        setPrice(context, ShopCategories.MATERIALS, Items.EMERALD, 8);
        setPrice(context, ShopCategories.MATERIALS, Items.NETHERITE_SCRAP, 400);
        setPrice(context, ShopCategories.MATERIALS, Items.NETHERITE_INGOT, 1600);
    }
}
