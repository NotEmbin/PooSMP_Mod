package embin.poosmp.economy.shop;

import com.mojang.logging.LogUtils;
import embin.poosmp.world.PooSMPRegistries;
import embin.poosmp.items.PooSMPItems;
import embin.poosmp.util.Id;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ShopCategories {
    public static final ShopCategory MISC = register("misc", Items.SADDLE);
    public static final ShopCategory FOOD = register("food", Items.APPLE);
    public static final ShopCategory MATERIALS = register("materials", Items.IRON_INGOT);
    public static final ShopCategory NATURE = register("nature", Items.GRASS_BLOCK);
    public static final ShopCategory BUILDING_BLOCKS = register("building_blocks", Items.BRICKS);
    public static final ShopCategory BANKERS_TABLE = register("bankers_table", PooSMPItems.HUNDRED_DOLLAR_BILL);

    public static ShopCategory register(String id, Item icon) {
        Identifier id2 = Id.of(id);
        Component translatedText = Component.translatable(id2.toLanguageKey("shop_category"));
        return Registry.register(PooSMPRegistries.SHOP_CATEGORY, id2, new ShopCategory(translatedText, icon));
    }

    public static void registerCategories() {
        LogUtils.getLogger().info("Shop this!");
    }
}
