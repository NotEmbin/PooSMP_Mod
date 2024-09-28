package embin.poosmp.util;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.item.Item;

public class PooSMPTags {
    private static final ConvertNamespace cn = new ConvertNamespace();

    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, cn.convert(name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, cn.convert(name));
        }

        public static final TagKey<Item> TOTEMS = createTag("poosmp:totems");
        public static final TagKey<Item> POOSMP_DISCS = createTag("poosmp:poosmp_discs");
        public static final TagKey<Item> STEREO_DISCS = createTag("poosmp:stereo_discs");
    }
}