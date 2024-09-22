package embin.poosmp;

import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.items.BiomeStickItem;
import embin.poosmp.items.PooSMPItems;
import embin.poosmp.util.ConvertNamespace;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PooSMPMod implements ModInitializer {
	public static final String MOD_ID = "poosmp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final class PooSMPItemGroups {
		public static void init() {
			Registry.register(Registries.ITEM_GROUP, ConvertNamespace.cn.convert("poosmp_biome_sticks"), BIOME_STICKS);
			Registry.register(Registries.ITEM_GROUP, ConvertNamespace.cn.convert("poosmp_items"), POOSMP_ITEMS);
		}

		public static final ItemGroup POOSMP_ITEMS = FabricItemGroup.builder()
			.icon(() -> new ItemStack(PooSMPBlocks.POOP_BLOCK))
			.displayName(Text.literal("PooSMP"))
			.entries(((displayContext, entries) -> {
				entries.add(PooSMPBlocks.POOP_BLOCK);
				entries.add(PooSMPItems.POOP_STICK);
				entries.add(PooSMPItems.SERVER_SAYS_WHAT_STICK);
				entries.add(PooSMPItems.BOOM_STICK);
				entries.add(PooSMPItems.ZOMBIE_STICK);
				entries.add(PooSMPItems.DIAMOND_SHARD);
			})).build();

		public static final ItemGroup BIOME_STICKS = FabricItemGroup.builder()
			.icon(() -> new ItemStack(PooSMPItems.BIOME_STICK))
			.displayName(Text.literal("PooSMP: Biome Sticks"))
			.entries((displayContext, entries) -> {
                for (String vanillaBiome : BiomeStickItem.vanilla_biomes) {
                    entries.add(PooSMPItems.getBiomeStickStack(vanillaBiome));
                }
			})
			.build();
	}

	@Override
	public void onInitialize() {
		PooSMPBlocks.init();
		PooSMPItems.init();
		PooSMPItemComponents.init();
		PooSMPItemGroups.init();
		LOGGER.info("Hello Fabric world!");
	}
}