package embin.poosmp.util;

import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.items.PooSMPItems;
import embin.poosmp.villager.PooSMPVillagers;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import java.util.Optional;

public class TradeConstructors {
    public static final DataComponentMap TRADED_BOOK_ENCHANTS = DataComponentMap.builder().build();

    public static void register_villager_trades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.MASON, 1, factories -> {
            factories.add(((entity, random) -> new FromMoney(Items.STONE, 32, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.ANDESITE, 24, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.DIORITE, 28, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.GRANITE, 28, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.NETHERRACK, 64, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.COBBLESTONE, 48, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.COBBLED_DEEPSLATE, 32, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.DEEPSLATE, 32, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 1, factories -> {
            factories.add(((entity, random) -> new FromMoney(Items.IRON_INGOT, 1, PooSMPItems.ONE_DOLLAR_BILL, 5).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.IRON_INGOT, 1, PooSMPItems.FIVE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.GOLD_INGOT, 1, PooSMPItems.ONE_DOLLAR_BILL, 4).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.DIAMOND, 1, PooSMPItems.FIVE_DOLLAR_BILL, 3).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.LAPIS_LAZULI, 4, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.COPPER_INGOT, 12, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 2, factories -> {
            factories.add(((entity, random) -> new FromMoney(Items.REDSTONE, 4, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.NETHERITE_SCRAP, 1, PooSMPItems.ONE_DOLLAR_BILL, 75).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.NETHERITE_INGOT, 1, PooSMPItems.HUNDRED_DOLLAR_BILL, 3).create(entity, random)));
        });
        TradeOfferHelper.registerVillagerOffers(PooSMPVillagers.BANKER, 1, factories -> {
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.ONE_DOLLAR_BILL, 5, PooSMPItems.FIVE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.ONE_DOLLAR_BILL, 10, PooSMPItems.TEN_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.ONE_DOLLAR_BILL, 25, PooSMPItems.TWENTY_FIVE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.ONE_DOLLAR_BILL, 50, PooSMPItems.FIFTY_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.TWENTY_FIVE_DOLLAR_BILL, 4, PooSMPItems.HUNDRED_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.FIFTY_DOLLAR_BILL, 2, PooSMPItems.HUNDRED_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.TEN_DOLLAR_BILL, 5, PooSMPItems.FIFTY_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.TEN_DOLLAR_BILL, 10, PooSMPItems.HUNDRED_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.ONE_DOLLAR_BILL, 2, PooSMPItems.TWO_DOLLAR_BILL).create(entity, random)));
        });
        TradeOfferHelper.registerVillagerOffers(PooSMPVillagers.BANKER, 2, factories -> {
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.FIVE_DOLLAR_BILL, 1, PooSMPItems.ONE_DOLLAR_BILL, 5).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.TWO_DOLLAR_BILL, 1, PooSMPItems.ONE_DOLLAR_BILL, 2).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.TEN_DOLLAR_BILL, 1, PooSMPItems.ONE_DOLLAR_BILL, 10).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.TWENTY_FIVE_DOLLAR_BILL, 1, PooSMPItems.ONE_DOLLAR_BILL, 25).create(entity, random)));
        });
        TradeOfferHelper.registerVillagerOffers(PooSMPVillagers.BANKER, 4, factories -> {
            factories.add(((entity, random) -> new ToMoney(Items.GOLD_INGOT, 1, PooSMPItems.ONE_DOLLAR_BILL, 7).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(Items.IRON_INGOT, 1, PooSMPItems.ONE_DOLLAR_BILL, 8).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(Items.IRON_BLOCK, 1, PooSMPItems.ONE_DOLLAR_BILL, 72).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(Items.EMERALD, 1, PooSMPItems.ONE_DOLLAR_BILL, 5).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(Items.EMERALD, 1, PooSMPItems.FIVE_DOLLAR_BILL, 1).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(Items.DIAMOND, 1, PooSMPItems.ONE_DOLLAR_BILL, 25).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(Items.DIAMOND, 1, PooSMPItems.TWENTY_FIVE_DOLLAR_BILL, 1).create(entity, random)));
        });
        TradeOfferHelper.registerVillagerOffers(PooSMPVillagers.BANKER, 3, factories -> {
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.FIFTY_DOLLAR_BILL, 1, PooSMPItems.ONE_DOLLAR_BILL, 50).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.FIFTY_DOLLAR_BILL, 1, PooSMPItems.TWENTY_FIVE_DOLLAR_BILL, 2).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.HUNDRED_DOLLAR_BILL, 1, PooSMPItems.TWENTY_FIVE_DOLLAR_BILL, 4).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.HUNDRED_DOLLAR_BILL, 1, PooSMPItems.FIFTY_DOLLAR_BILL, 2).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(PooSMPItems.TEN_DOLLAR_BILL, 1, PooSMPItems.FIVE_DOLLAR_BILL, 2).create(entity, random)));
        });
        TradeOfferHelper.registerVillagerOffers(PooSMPVillagers.BANKER, 5, factories -> {
            factories.add(((entity, random) -> new ToMoney(Items.NETHERITE_SCRAP, 1, PooSMPItems.ONE_DOLLAR_BILL, 50).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(Items.NETHERITE_INGOT, 1, PooSMPItems.HUNDRED_DOLLAR_BILL, 2).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(Items.ELYTRA, 1, PooSMPItems.HUNDRED_DOLLAR_BILL, 8).create(entity, random)));
            factories.add(((entity, random) -> new ToMoney(Items.NETHERITE_SCRAP, 1, PooSMPItems.FIFTY_DOLLAR_BILL, 1).create(entity, random)));
            factories.add(((entity, random) -> {
                Optional<RegistryEntry<Enchantment>> enchantment = entity.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).getRandomEntry(PooSMPTags.Enchantments.BANKER_TRADEABLE, random);
                int maxLevel;
                if (enchantment.isPresent()) {
                    maxLevel = enchantment.get().value().getMaxLevel();
                } else {
                    return new ToMoney(new ItemStack(Items.BOOK), PooSMPItems.HUNDRED_DOLLAR_BILL, 1).create(entity, random);
                }
                return new ToMoney(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(enchantment.get(), maxLevel)), PooSMPItems.HUNDRED_DOLLAR_BILL, 3).create(entity, random);
            }));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 3, factories -> {
            factories.add(((entity, random) -> new FromMoney(Items.PUMPKIN, 32, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.WHEAT, 24, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.CARROT, 28, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.POTATO, 28, PooSMPItems.ONE_DOLLAR_BILL).create(entity, random)));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 5, factories -> {
            factories.add(((entity, random) -> new FromMoney(PooSMPItems.HUNDRED_DOLLAR_BILL, 1, Items.SHULKER_SHELL, 2).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.EMERALD, 1, PooSMPItems.BANANA, 4).create(entity, random)));
        });
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            factories.add(((entity, random) -> new FromMoney(Items.EMERALD, 1, PooSMPBlocks.PALE_MOSS_BLOCK, 8).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.EMERALD, 1, PooSMPBlocks.RESIN_CLUMP, 24).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.EMERALD, 1, PooSMPItems.RESIN_BRICK, 16).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.EMERALD, 2, PooSMPBlocks.PALE_OAK_SAPLING, 4).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.EMERALD, 1, PooSMPBlocks.PALE_HANGING_MOSS, 8).create(entity, random)));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 4, factories -> {
            factories.add(((entity, random) -> new FromMoney(Items.EMERALD, 1, PooSMPBlocks.PALE_MOSS_BLOCK, 4).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.EMERALD, 1, PooSMPBlocks.RESIN_CLUMP, 8).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.EMERALD, 1, PooSMPBlocks.PALE_HANGING_MOSS, 4).create(entity, random)));
            factories.add(((entity, random) -> new FromMoney(Items.EMERALD, 1, PooSMPBlocks.PALE_OAK_SAPLING, 2).create(entity, random)));
        });
    }

    public static class ToMoney implements VillagerTrades.ItemListing {
        private final ItemCost stack;
        private final int max_uses;
        private final int experience;
        private final ItemStack price;
        private final float multiplier;

        public ToMoney(ItemCost stack, ItemStack price) {
            this.stack = stack;
            this.max_uses = 999999;
            this.experience = 20;
            this.price = price;
            this.multiplier = 0.05F;
        }

        public ToMoney(ItemLike item, ItemLike sell_price) {
            this(new ItemCost(sell_price.asItem(), 1), new ItemStack(item));
        }

        public ToMoney(ItemLike item, int amount, ItemLike sell_price) {
            this(new ItemCost(sell_price.asItem(), 1), new ItemStack(item, amount));
        }

        public ToMoney(ItemLike item, int amount, ItemLike sell_price, int amount2) {
            this(new ItemCost(sell_price.asItem(), amount2), new ItemStack(item, amount));
        }

        public ToMoney(ItemStack item, ItemLike sell_price, int amount2) {
            this(new ItemCost(sell_price.asItem(), amount2), item);
        }

        @Override
        public MerchantOffer create(Entity entity, RandomSource random) {
            return new MerchantOffer(this.stack, this.price, this.max_uses, this.experience, this.multiplier);
        }
    }
    public static class FromMoney implements VillagerTrades.ItemListing {
        private final ItemCost stack;
        private final int max_uses;
        private final int experience;
        private final ItemStack price;
        private final float multiplier;

        public FromMoney(ItemCost stack, ItemStack price) {
            this.stack = stack;
            this.max_uses = 999999;
            this.experience = 20;
            this.price = price;
            this.multiplier = 0.05F;
        }

        public FromMoney(ItemLike item, ItemLike buy_price) {
            this(new ItemCost(item.asItem(), 1), new ItemStack(buy_price));
        }

        public FromMoney(ItemLike item, int amount, ItemLike buy_price) {
            this(new ItemCost(item.asItem(), amount), new ItemStack(buy_price));
        }

        public FromMoney(ItemLike item, int amount, ItemLike buy_price, int amount2) {
            this(new ItemCost(item.asItem(), amount), new ItemStack(buy_price, amount2));
        }

        @Override
        public MerchantOffer create(Entity entity, RandomSource random) {
            return new MerchantOffer(this.stack, this.price, this.max_uses, this.experience, this.multiplier);
        }
    }
}
