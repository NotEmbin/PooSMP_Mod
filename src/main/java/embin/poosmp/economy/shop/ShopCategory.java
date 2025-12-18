package embin.poosmp.economy.shop;

import embin.poosmp.PooSMPRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import java.util.List;
import java.util.stream.Stream;

public class ShopCategory {
    private final Component name;
    private final ItemStack displayed_item_stack;
    public ShopCategory(Component name, Item displayed_item) {
        this.name = name;
        this.displayed_item_stack = new ItemStack(displayed_item);
    }

    @Deprecated(forRemoval = true)
    public void addProduct(Item item, int price) {
        //this.products.add(item);
    }

    public boolean hasItem(Item item) {
        return item.getDefaultInstance().is(getShopProductsTag());
    }

    public boolean isIn(TagKey<ShopCategory> tagKey) {
        return PooSMPRegistries.SHOP_CATEGORY.wrapAsHolder(this).is(tagKey);
    }

    public ItemStack getDisplayedItem() {
        return this.displayed_item_stack;
    }

    public Component getName() {
        return this.name;
    }

    public Identifier getId() {
        return PooSMPRegistries.SHOP_CATEGORY.getKey(this);
    }

    public TagKey<Item> getShopProductsTag() {
        return TagKey.create(Registries.ITEM, this.getId().withPrefix("in_shop_category/"));
    }

    public List<Holder<Item>> getItems(HolderLookup.Provider wrapperLookup) {
        Stream<Holder<Item>> itemStream = wrapperLookup.lookupOrThrow(Registries.ITEM).getOrThrow(getShopProductsTag()).stream();
        return itemStream.toList();
    }
}
