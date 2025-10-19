package embin.poosmp.util;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.economy.shop.ShopCategory;
import embin.poosmp.upgrade.Upgrade;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.item.Item;

public class PooSMPTags {
    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Id.of(name));
        }

        public static final TagKey<Block> PALE_OAK_LOGS = createTag("poosmp:pale_oak_logs");
        public static final TagKey<Block> INCORRECT_FOR_RED_POO_TOOLS = createTag("poosmp:incorrect_for_red_poo_tools");
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Id.of(name));
        }

        public static final TagKey<Item> TOTEMS = createTag("poosmp:totems");
        public static final TagKey<Item> POOSMP_DISCS = createTag("poosmp:poosmp_discs");
        public static final TagKey<Item> STEREO_DISCS = createTag("poosmp:stereo_discs");
        public static final TagKey<Item> PALE_OAK_LOGS = createTag("poosmp:pale_oak_logs");
    }

    public static class Enchantments {
        private static TagKey<Enchantment> createTag(String name) {
            return TagKey.of(RegistryKeys.ENCHANTMENT, Id.of(name));
        }

        public static final TagKey<Enchantment> BANKER_TRADEABLE = createTag("poosmp:banker_tradeable");
    }

    public static class PaintingVariants {
        private static TagKey<PaintingVariant> createTag(String name) {
            return TagKey.of(RegistryKeys.PAINTING_VARIANT, Id.of(name));
        }

        public static final TagKey<PaintingVariant> POOSMP_PAINTINGS = createTag("poosmp:poosmp_paintings");
        public static final TagKey<PaintingVariant> PLACEABLE_PAINTINGS = createTag("poosmp:placeable_poosmp_paintings");
        public static final TagKey<PaintingVariant> NON_PLACEABLE_PAINTINGS = createTag("poosmp:non_placeable_poosmp_paintings");
    }

    public static class Upgrades {
        private static TagKey<Upgrade> createTag(String name) {
            return TagKey.of(PooSMPRegistries.Keys.UPGRADE, Id.of(name));
        }

        public static final TagKey<Upgrade> LIST_ORDER = createTag("poosmp:list_order");
        public static final TagKey<Upgrade> DOUBLE_ATTRIBUTE_GIVE = createTag("poosmp:double_attribute_give");
    }

    public static class ShopCategories {
        private static TagKey<ShopCategory> createTag(String name) {
            return TagKey.of(PooSMPRegistries.Keys.SHOP_CATEGORY, Id.of(name));
        }

        public static final TagKey<ShopCategory> HIDDEN = createTag("poosmp:hidden_from_shop_command");
    }
}