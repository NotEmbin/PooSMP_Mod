package embin.poosmp.economy.shop;

import embin.poosmp.PooSMPRegistries;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ShopCategory {
    private final Text name;
    private final ItemStack displayed_item_stack;
    public ShopCategory(Text name, Item displayed_item) {
        this.name = name;
        this.displayed_item_stack = new ItemStack(displayed_item);
    }

    @Deprecated(forRemoval = true)
    public void addProduct(Item item, int price) {
        //this.products.add(item);
    }

    public boolean hasItem(Item item) {
        return item.getDefaultStack().isIn(getShopProductsTag());
    }

    public boolean isIn(TagKey<ShopCategory> tagKey) {
        return PooSMPRegistries.SHOP_CATEGORY.getEntry(this).isIn(tagKey);
    }

    public ItemStack getDisplayedItem() {
        return this.displayed_item_stack;
    }

    public Text getName() {
        return this.name;
    }

    public Identifier getId() {
        return PooSMPRegistries.SHOP_CATEGORY.getId(this);
    }

    public TagKey<Item> getShopProductsTag() {
        return TagKey.of(RegistryKeys.ITEM, this.getId().withPrefixedPath("in_shop_category/"));
    }

    public List<RegistryEntry<Item>> getItems(RegistryWrapper.WrapperLookup wrapperLookup) {
        RegistryEntryLookup. RegistryLookup registryLookup = wrapperLookup.createRegistryLookup();
        Stream<RegistryEntry<Item>> itemStream = registryLookup.getOrThrow(RegistryKeys.ITEM).getOrThrow(getShopProductsTag()).stream();
        return itemStream.toList();
    }
}
