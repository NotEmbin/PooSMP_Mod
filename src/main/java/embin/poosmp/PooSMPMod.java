package embin.poosmp;

import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.items.BiomeStickItem;
import embin.poosmp.items.MobStickItem;
import embin.poosmp.items.PooSMPItems;
import embin.poosmp.util.ConvertNamespace;
import embin.poosmp.util.TradeConstructors;
import embin.poosmp.villager.PooSMPPoi;
import embin.poosmp.villager.PooSMPVillagers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PooSMPMod implements ModInitializer {
	public static final String MOD_ID = "poosmp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final PooSMPLogFilter filter = new PooSMPLogFilter();
	public static final boolean componentless_installed = FabricLoader.getInstance().isModLoaded("componentless");

	public static final class PooSMPItemGroups {
		public static void init() {
			Registry.register(Registries.ITEM_GROUP, ConvertNamespace.convert("poosmp_items"), POOSMP_ITEMS);
			Registry.register(Registries.ITEM_GROUP, ConvertNamespace.convert("poosmp_biome_sticks"), BIOME_STICKS);
			Registry.register(Registries.ITEM_GROUP, ConvertNamespace.convert("poosmp_music_discs"), MUSIC_DISCS);
			Registry.register(Registries.ITEM_GROUP, ConvertNamespace.convert("poosmp_mob_sticks"), MOB_STICKS);
			Registry.register(Registries.ITEM_GROUP, ConvertNamespace.convert("poosmp_money"), MONEY);
		}

		@Deprecated
		public static void add_empty(ItemGroup.Entries entries, int amount) { // it dooo not work sad face
			for (int i = 0; i < (amount - 1); i++) {
				entries.add(ItemStack.EMPTY);
			}
		}

		public static final ItemGroup POOSMP_ITEMS = FabricItemGroup.builder()
			.icon(() -> new ItemStack(PooSMPItems.POOPLET))
			.displayName(Text.literal("PooSMP"))
			.entries(((displayContext, entries) -> {
				entries.add(PooSMPBlocks.POOP_BLOCK);
				entries.add(PooSMPItems.POOP_STICK);
				entries.add(PooSMPItems.SERVER_SAYS_WHAT_STICK);
				entries.add(PooSMPItems.BIOME_STICK);
				entries.add(PooSMPItems.BOOM_STICK);
				entries.add(PooSMPItems.ZOMBIE_STICK);
				entries.add(PooSMPItems.DIAMOND_SHARD);
				entries.add(PooSMPBlocks.MISSINGNO_BLOCK);
				entries.add(PooSMPItems.WEDDING_RING);
				entries.add(PooSMPItems.RED_NETHER_BRICK);
				entries.add(PooSMPItems.POOP_BRICK);
				entries.add(PooSMPBlocks.POOP_BRICKS);
				entries.add(PooSMPItems.POOPLET);
				entries.add(PooSMPBlocks.POOP_BRICK_STAIRS);
				entries.add(PooSMPBlocks.POOP_BRICK_SLAB);
				entries.add(PooSMPBlocks.POOP_BRICK_WALL);
				entries.add(PooSMPItems.RING);
				entries.add(PooSMPBlocks.RED_NETHER_BRICK_FENCE);
				entries.add(PooSMPItems.TOTEM_OF_HEALTH);
				entries.add(PooSMPItems.WARP_STICK);
				entries.add(PooSMPItems.FILL_ARMOR_TRIM_TEMPLATE);
				entries.add(PooSMPItems.DISC_TRIFECTA_CAP);
				entries.add(PooSMPItems.DISC_BUTTERFLIES_AND_HURRICANES_INSTRUMENTAL);
				entries.add(PooSMPItems.DISC_BUDDY_HOLLY);
				entries.add(PooSMPItems.DISC_STEREO_MADNESS);
				entries.add(PooSMPItems.DISC_NOT_LIKE_US);
				entries.add(PooSMPItems.DISC_RESISTANCE_INSTRUMENTAL);
				entries.add(PooSMPItems.TOTEM_OF_REACH);
				entries.add(PooSMPItems.BLANK_MUSIC_DISC);
				entries.add(PooSMPItems.ENCHANTED_TOTEM_OF_HEALTH);
				entries.add(PooSMPItems.ENCHANTED_TOTEM_OF_REACH);
				entries.add(PooSMPItems.DISC_BLISS_INSTRUMENTAL);
				entries.add(PooSMPItems.DISC_ENDLESSLY_INSTRUMENTAL);
				entries.add(PooSMPItems.DISC_ENDLESSLY);
				entries.add(PooSMPItems.DISC_ENDLESSLY_STEREO);
				entries.add(PooSMPItems.ZAP_STICK);
				entries.add(PooSMPItems.VILLAGER_STICK);
				entries.add(PooSMPBlocks.SUS);
				entries.add(PooSMPBlocks.DDEDEDODEDIAMANTE_BLOCK);
				entries.add(PooSMPItems.BACON_BUCKET);
				entries.add(PooSMPItems.COW_STICK);
				entries.add(PooSMPBlocks.BANKERS_TABLE);
				entries.add(PooSMPBlocks.PALE_MOSS_BLOCK);
				entries.add(PooSMPBlocks.PALE_MOSS_CARPET);
				entries.add(PooSMPBlocks.PALE_HANGING_MOSS);
				entries.add(PooSMPBlocks.PALE_OAK_LOG);
				entries.add(PooSMPBlocks.PALE_OAK_WOOD);
				entries.add(PooSMPBlocks.STRIPPED_PALE_OAK_LOG);
				entries.add(PooSMPBlocks.STRIPPED_PALE_OAK_WOOD);
				entries.add(PooSMPBlocks.PALE_OAK_PLANKS);
				entries.add(PooSMPBlocks.PALE_OAK_STAIRS);
				entries.add(PooSMPBlocks.PALE_OAK_SLAB);
				entries.add(PooSMPBlocks.PALE_OAK_FENCE);
				entries.add(PooSMPBlocks.PALE_OAK_FENCE_GATE);
				entries.add(PooSMPBlocks.PALE_OAK_DOOR);
				entries.add(PooSMPBlocks.PALE_OAK_TRAPDOOR);
				entries.add(PooSMPBlocks.PALE_OAK_PRESSURE_PLATE);
				entries.add(PooSMPBlocks.PALE_OAK_BUTTON);
				entries.add(PooSMPBlocks.RESIN_BLOCK);
				entries.add(PooSMPBlocks.RESIN_BRICKS);
				entries.add(PooSMPBlocks.RESIN_BRICK_STAIRS);
				entries.add(PooSMPBlocks.RESIN_BRICK_SLAB);
				entries.add(PooSMPBlocks.RESIN_BRICK_WALL);
				entries.add(PooSMPBlocks.CHISELED_RESIN_BRICKS);
				entries.add(PooSMPBlocks.RESIN_CLUMP);
				entries.add(PooSMPItems.RESIN_BRICK);
				entries.add(PooSMPBlocks.PALE_OAK_SAPLING);
				//entries.add(PooSMPItems.STRANGE_DIAMOND_PICKAXE);
				//entries.add(PooSMPItems.STRANGE_NETHERITE_PICKAXE);
				//entries.add(PooSMPItems.STRANGE_UPGRADE_SMITHING_TEMPLATE);
				entries.add(PooSMPBlocks.PALE_OAK_LEAVES);
			})).build();

		public static final ItemGroup BIOME_STICKS = FabricItemGroup.builder()
			.icon(() -> new ItemStack(PooSMPItems.BIOME_STICK))
			.displayName(Text.literal("PooSMP: Biome Sticks"))
			.entries((displayContext, entries) -> {
                for (String vanilla_biome : BiomeStickItem.vanilla_biomes) {
                    entries.add(PooSMPItems.getBiomeStickStack(vanilla_biome));
                }
			}).build();

		public static final ItemGroup MUSIC_DISCS = FabricItemGroup.builder()
			.icon(() -> new ItemStack(PooSMPItems.DISC_TRIFECTA_CAP))
			.displayName(Text.literal("PooSMP: Music Discs"))
			.entries((displayContext, entries) -> {
				entries.add(PooSMPItems.DISC_BUTTERFLIES_AND_HURRICANES_INSTRUMENTAL);
				entries.add(PooSMPItems.DISC_RESISTANCE_INSTRUMENTAL);
				entries.add(PooSMPItems.DISC_BLISS_INSTRUMENTAL);
				entries.add(PooSMPItems.DISC_ENDLESSLY);
				entries.add(PooSMPItems.DISC_ENDLESSLY_INSTRUMENTAL);
				entries.add(PooSMPItems.DISC_ENDLESSLY_STEREO);
				entries.add(PooSMPItems.DISC_TRIFECTA_CAP);
				entries.add(PooSMPItems.DISC_BUDDY_HOLLY);
				entries.add(PooSMPItems.DISC_STEREO_MADNESS);
				entries.add(PooSMPItems.DISC_NOT_LIKE_US);
			}).build();

		public static final ItemGroup MOB_STICKS = FabricItemGroup.builder()
			.icon(() -> new ItemStack(Items.ZOMBIE_HEAD))
			.displayName(Text.literal("PooSMP: Mob Sticks"))
			.entries((displayContext, entries) -> {
				for (EntityType<?> entity : MobStickItem.Stack.mob_list) {
					entries.add(MobStickItem.Stack.getMobStick((EntityType<MobEntity>) entity));
				}
			}).build();

		public static final ItemGroup MONEY = FabricItemGroup.builder()
			.icon(() -> new ItemStack(PooSMPItems.ONE_DOLLAR_BILL))
			.displayName(Text.literal("PooSMP: Money"))
			.entries((displayContext, entries) -> {
				entries.add(PooSMPBlocks.BANKERS_TABLE);
				entries.add(PooSMPItems.ONE_DOLLAR_BILL);
				entries.add(PooSMPItems.TWO_DOLLAR_BILL);
				entries.add(PooSMPItems.FIVE_DOLLAR_BILL);
				entries.add(PooSMPItems.TEN_DOLLAR_BILL);
				entries.add(PooSMPItems.TWENTY_FIVE_DOLLAR_BILL);
				entries.add(PooSMPItems.FIFTY_DOLLAR_BILL);
				entries.add(PooSMPItems.HUNDRED_DOLLAR_BILL);
				//entries.add(PooSMPItems.ONE_CENT_COIN);
				//entries.add(PooSMPItems.FIVE_CENT_COIN);
				//entries.add(PooSMPItems.TEN_CENT_COIN);
				//entries.add(PooSMPItems.TWENTY_FIVE_CENT_COIN);
				//entries.add(PooSMPItems.ONE_DOLLAR_COIN);
			}).build();
	}

	@Override
	public void onInitialize() {
		PooSMPSoundEvents.init(); // so that music discs actually work
		PooSMPBlocks.init();
		PooSMPItems.init();
		PooSMPItemComponents.init();
		PooSMPItemGroups.init();
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
			entries.addAfter(Items.RED_NETHER_BRICK_WALL, PooSMPBlocks.RED_NETHER_BRICK_FENCE.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.PALE_OAK_LOG.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.PALE_OAK_WOOD.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.STRIPPED_PALE_OAK_LOG.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.STRIPPED_PALE_OAK_WOOD.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.PALE_OAK_PLANKS.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.PALE_OAK_STAIRS.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.PALE_OAK_SLAB.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.PALE_OAK_FENCE.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.PALE_OAK_FENCE_GATE.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.PALE_OAK_DOOR.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.PALE_OAK_TRAPDOOR.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.PALE_OAK_PRESSURE_PLATE.asItem());
			entries.addBefore(Items.BAMBOO_BLOCK, PooSMPBlocks.PALE_OAK_BUTTON.asItem());
			entries.addBefore(Items.SANDSTONE, PooSMPBlocks.RESIN_BRICKS.asItem());
			entries.addBefore(Items.SANDSTONE, PooSMPBlocks.RESIN_BRICK_STAIRS.asItem());
			entries.addBefore(Items.SANDSTONE, PooSMPBlocks.RESIN_BRICK_SLAB.asItem());
			entries.addBefore(Items.SANDSTONE, PooSMPBlocks.RESIN_BRICK_WALL.asItem());
			entries.addBefore(Items.SANDSTONE, PooSMPBlocks.CHISELED_RESIN_BRICKS.asItem());
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
			entries.addAfter(Items.HONEY_BLOCK, PooSMPBlocks.RESIN_BLOCK.asItem());
			entries.addAfter(Items.CHERRY_SAPLING, PooSMPBlocks.PALE_OAK_SAPLING.asItem());
			entries.addAfter(Items.MOSS_CARPET, PooSMPBlocks.PALE_HANGING_MOSS.asItem());
			entries.addAfter(Items.MOSS_CARPET, PooSMPBlocks.PALE_MOSS_CARPET.asItem());
			entries.addAfter(Items.MOSS_CARPET, PooSMPBlocks.PALE_MOSS_BLOCK.asItem());
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			entries.addAfter(Items.HONEYCOMB, PooSMPBlocks.RESIN_CLUMP.asItem());
			entries.addAfter(Items.NETHER_BRICK, PooSMPItems.RESIN_BRICK);
		});
		PooSMPPoi.init();
		PooSMPVillagers.init();
		TradeConstructors.register_villager_trades();
		StrippableBlockRegistry.register(PooSMPBlocks.PALE_OAK_LOG, PooSMPBlocks.STRIPPED_PALE_OAK_LOG);
		StrippableBlockRegistry.register(PooSMPBlocks.PALE_OAK_WOOD, PooSMPBlocks.STRIPPED_PALE_OAK_WOOD);
		FlammableBlockRegistry.getDefaultInstance().add(PooSMPBlocks.PALE_OAK_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(PooSMPBlocks.STRIPPED_PALE_OAK_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(PooSMPBlocks.PALE_OAK_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(PooSMPBlocks.STRIPPED_PALE_OAK_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(PooSMPBlocks.PALE_OAK_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PooSMPBlocks.PALE_OAK_LEAVES, 30, 60);
		FlammableBlockRegistry.getDefaultInstance().add(PooSMPBlocks.PALE_OAK_STAIRS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PooSMPBlocks.PALE_OAK_SLAB, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PooSMPBlocks.PALE_OAK_FENCE, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PooSMPBlocks.PALE_OAK_FENCE_GATE, 5, 20);
		LOGGER.info("im all pooped up");
	}
}