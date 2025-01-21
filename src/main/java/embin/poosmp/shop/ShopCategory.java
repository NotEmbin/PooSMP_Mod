package embin.poosmp.shop;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class ShopCategory {
    private final Text name;
    private final Item displayed_item;
    private Map<String, ItemStack> products = new HashMap<>();
    private Map<String, Integer> prices = new HashMap<>();
    private Map<String, Integer> sell_prices = new HashMap<>();
    public ShopCategory(Text name, Item displayed_item) {
        this.name = name;
        this.displayed_item = displayed_item;
    }

    public void add_product(String id, ItemStack stack, int price, int sell_price) {
        this.products.put(id, stack);
        this.prices.put(id, price);
        this.sell_prices.put(id, sell_price);
    }

    public void add_product(String id, ItemStack stack, int price) {
        add_product(id, stack, price, (int)Math.floor((double) price * 0.8));
    }

    public void add_product(Item item, int price, int sell_price) {
        String id = Registries.ITEM.getId(item).getPath();
        this.products.put(id, new ItemStack(item));
        this.prices.put(id, price);
        this.sell_prices.put(id, sell_price);
    }

    public void add_product(Item item, int price) {
        add_product(item, price, (int)Math.floor((double) price * 0.8));
    }

    public ItemStack get_product(String id) {
        return this.products.get(id).copy();
    }

    public ItemStack get_displayed_item() {
        return new ItemStack(this.displayed_item);
    }

    public Text get_name() {
        return this.name;
    }
}
