package embin.poosmp.economy.shop;

import com.mojang.logging.LogUtils;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.items.PooSMPItems;
import embin.poosmp.util.Id;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class ShopCategories {
    public static final ShopCategory MISC = register("misc", Items.SADDLE);
    public static final ShopCategory FOOD = register("food", Items.APPLE);
    public static final ShopCategory MATERIALS = register("materials", Items.IRON_INGOT);
    public static final ShopCategory NATURE = register("nature", Items.GRASS_BLOCK);
    public static final ShopCategory BUILDING_BLOCKS = register("building_blocks", Items.BRICKS);
    public static final ShopCategory BANKERS_TABLE = register("bankers_table", PooSMPItems.HUNDRED_DOLLAR_BILL);

    public static ShopCategory register(String id, Item icon) {
        Identifier id2 = Id.of(id);
        Text translatedText = Text.translatable(id2.toTranslationKey("shop_category"));
        return Registry.register(PooSMPRegistries.SHOP_CATEGORY, id2, new ShopCategory(translatedText, icon));
    }

    public static void registerCategories() {
        LogUtils.getLogger().info("Shop this!");
    }
}
