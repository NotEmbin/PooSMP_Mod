package embin.poosmp.shop;

import net.minecraft.item.Items;
import net.minecraft.text.Text;

@Deprecated
public class ShopCategories {
    public static final ShopCategory MISC = new ShopCategory(Text.literal("Misc"), Items.SADDLE);
    public static final ShopCategory FOOD = new ShopCategory(Text.literal("Food"), Items.APPLE);
    public static final ShopCategory MATERIALS = new ShopCategory(Text.literal("Materials"), Items.IRON_INGOT);
    public static final ShopCategory NATURE = new ShopCategory(Text.literal("Nature"), Items.GRASS_BLOCK);
    public static final ShopCategory BUILDING_BLOCKS = new ShopCategory(Text.literal("Building Blocks"), Items.BRICKS);
}
