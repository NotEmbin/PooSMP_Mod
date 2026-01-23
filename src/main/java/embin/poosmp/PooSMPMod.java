package embin.poosmp;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.economy.ItemWorth;
import embin.poosmp.economy.shop.ShopCategories;
import embin.poosmp.items.ItemUses;
import embin.poosmp.items.PooSMPItems;
import embin.poosmp.items.component.PooSMPItemComponents;
import embin.poosmp.items.component.ValueComponent;
import embin.poosmp.networking.PooSMPMessages;
import embin.poosmp.upgrade.Upgrade;
import embin.poosmp.util.*;
import embin.poosmp.villager.PooSMPPoi;
import embin.poosmp.villager.PooSMPVillagers;
import embin.poosmp.world.PooSMPGameRules;
import embin.poosmp.world.PooSMPRegistries;
import embin.poosmp.world.PooSMPSavedData;
import embin.strangeitems.event.ServerPlayerEvents;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.impl.item.ComponentTooltipAppenderRegistryImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.Locale;

public class PooSMPMod implements ModInitializer {
	public static final String MOD_ID = "poosmp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final boolean componentless_installed = FabricLoader.getInstance().isModLoaded("componentless");
	public static final boolean SHOP_ENABLED = true; // not ready yet

	@Override
	public void onInitialize() {
		PooSMPRegistries.acknowledge();
		PooSMPSoundEvents.init(); // so that music discs actually work
		//Upgrades.init();
        PooSMPItems.init();
		PooSMPBlocks.init();
		PooSMPItemComponents.init();
		PooSMPItemGroups.init();
		ShopCategories.registerCategories();
        PooSMPGameRules.acknowledge();
        ItemUses.register();

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
			entries.addAfter(Items.RED_NETHER_BRICK_WALL, PooSMPBlocks.RED_NETHER_BRICK_FENCE.asItem());
		});

		PooSMPPoi.init();
		PooSMPVillagers.init();
		TradeConstructors.register_villager_trades();

		PooSMPMessages.register();
		PooSMPMessages.registerC2SPackets();

		DynamicRegistries.registerSynced(PooSMPRegistries.Keys.UPGRADE, Upgrade.CODEC);

        ComponentTooltipAppenderRegistryImpl.addBefore(DataComponents.ENCHANTMENTS, PooSMPItemComponents.ITEM_VALUE);

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> PooSMPCommands.register(dispatcher, registryAccess));

		DefaultItemComponentEvents.MODIFY.register(Id.of("poosmp:displayed_id"), modifyContext -> {
			modifyContext.modify(item -> true, (builder, item) -> {
				Identifier itemId = BuiltInRegistries.ITEM.getKey(item);
				builder.set(PooSMPItemComponents.DISPLAYED_ID, itemId);
				//if (itemId.getNamespace().equals(Identifier.DEFAULT_NAMESPACE)) {
				//	builder.add(PooSMPItemComponents.DISPLAYED_ID, Identifier.of("mojang", itemId.getPath()));
				//} else {
				//	builder.add(PooSMPItemComponents.DISPLAYED_ID, itemId);
				//}
			});
		});

        ServerPlayerEvents.ON_TICK.register(Id.of("on_tick"), serverPlayer -> {
            if (serverPlayer.getRandom().nextIntBetweenInclusive(0, 3000) == 2) {
                double x = serverPlayer.getX() + 8;
                double y = serverPlayer.getY() + 3;
                double z = serverPlayer.getZ() + 4;
                SoundEvent soundEvent = switch (serverPlayer.getRandom().nextIntBetweenInclusive(0, 5)) {
                    case 1 -> SoundEvents.GRAVEL_BREAK;
                    case 2 -> SoundEvents.SAND_BREAK;
                    case 3 -> SoundEvents.WOOD_BREAK;
                    case 4 -> SoundEvents.COPPER_BREAK;
                    case 5 -> SoundEvents.NETHERRACK_BREAK;
                    default -> SoundEvents.STONE_BREAK;
                };
                if (soundEvent != null) serverPlayer.level().playSound(null, x, y, z, soundEvent, SoundSource.BLOCKS, 0.3f, 1f);
                int BAT = serverPlayer.getRandom().nextIntBetweenInclusive(0, 50);
                switch (BAT) {
                    case 4 -> serverPlayer.sendSystemMessage(Component.literal("VpmNu06pT_o").withStyle(ChatFormatting.DARK_GRAY));
                    case 7, 8, 9 -> {
                        Component message = serverPlayer.getDisplayName().copy().append(" has shit themselves.");
                        PlayerList playerList = serverPlayer.level().getServer().getPlayerList();
                        playerList.broadcastSystemMessage(message, false);
                    }
                    default -> {}
                }
            }
            return InteractionResult.PASS;
        });

		if (PooSMPMod.SHOP_ENABLED) {
			DefaultItemComponentEvents.MODIFY.register(Id.of("poosmp:set_item_prices"), ItemWorth::setPrices);
		}

		LOGGER.info("im all pooped up");
	}
}