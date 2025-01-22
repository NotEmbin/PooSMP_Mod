package embin.poosmp.util;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.item.Item;

public class PooSMPTags {
    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, ConvertNamespace.convert(name));
        }

        public static final TagKey<Block> PALE_OAK_LOGS = createTag("poosmp:pale_oak_logs");
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, ConvertNamespace.convert(name));
        }

        public static final TagKey<Item> TOTEMS = createTag("poosmp:totems");
        public static final TagKey<Item> POOSMP_DISCS = createTag("poosmp:poosmp_discs");
        public static final TagKey<Item> STEREO_DISCS = createTag("poosmp:stereo_discs");
        public static final TagKey<Item> PALE_OAK_LOGS = createTag("poosmp:pale_oak_logs");
    }
}