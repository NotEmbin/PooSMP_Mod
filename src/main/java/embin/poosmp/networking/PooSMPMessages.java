package embin.poosmp.networking;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.PooSMPSavedData;
import embin.poosmp.client.PooSMPModClient;
import embin.poosmp.networking.payload.BuyUpgradePayload;
import embin.poosmp.networking.payload.SellUpgradePayload;
import embin.poosmp.networking.payload.DataSyncPayload;
import embin.poosmp.upgrade.PriceObject;
import embin.poosmp.upgrade.ServerUpgradeData;
import embin.poosmp.upgrade.Upgrade;
import embin.poosmp.util.Id;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class PooSMPMessages {
    public static final Identifier BUY_UPGRADE = Id.of("buy_upgrade");
    public static final Identifier SELL_UPGRADE = Id.of("sell_upgrade");
    public static final Identifier UPGRADE_SYNC = Id.of("upgrade_sync");

    public static void register() {
        PayloadTypeRegistry.playC2S().register(BuyUpgradePayload.ID, BuyUpgradePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(SellUpgradePayload.ID, SellUpgradePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(DataSyncPayload.ID, DataSyncPayload.CODEC);
    }


    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(BuyUpgradePayload.ID, (payload, context) -> {
            ServerPlayer player = context.player();
            ServerLevel level = player.level();
            PooSMPSavedData savedData = PooSMPSavedData.get(context.server());

            if (!level.isClientSide()) {
                MinecraftServer server = level.getServer();
                server.execute(() -> {
                    Registry<Upgrade> upgradeRegistry = server.registryAccess().lookupOrThrow(PooSMPRegistries.Keys.UPGRADE);
                    if (upgradeRegistry.get(payload.upgrade()).isPresent()) {
                        Holder<Upgrade> upgrade = upgradeRegistry.get(payload.upgrade()).get();
                        int currentPurchasedAmount = ServerUpgradeData.INSTANCE.getPurchasedAmount(player, upgrade.value());
                        player.setExperienceLevels(player.experienceLevel - PriceObject.getCurrentPrice(upgrade.value(), player, currentPurchasedAmount));
                        // ServerUpgradeData.INSTANCE.setPurchasedAmount(player, upgrade.value(), currentPurchasedAmount + 1);
                        savedData.buyUpgrade(player, upgrade.value());
                        upgrade.value().onPurchase(player);
                        level.playSound(null, player.blockPosition(), SoundEvents.ARROW_HIT_PLAYER, SoundSource.PLAYERS);
                        PooSMPSavedData.syncToClient(player);
                    } else {
                        server.sendSystemMessage(Component.literal("Invalid upgrade").withStyle(ChatFormatting.RED));
                    }
                });
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(SellUpgradePayload.ID, (payload, context) -> {
            ServerPlayer player = context.player();
            ServerLevel level = player.level();
            PooSMPSavedData savedData = PooSMPSavedData.get(context.server());

            if (!level.isClientSide()) {
                MinecraftServer server = level.getServer();
                server.execute(() -> {
                    Registry<Upgrade> upgradeRegistry = server.registryAccess().lookupOrThrow(PooSMPRegistries.Keys.UPGRADE);
                    if (upgradeRegistry.get(payload.upgrade()).isPresent()) {
                        Holder<Upgrade> upgrade = upgradeRegistry.get(payload.upgrade()).get();
                        int currentPurchasedAmount = savedData.upgradePurchaseAmount(player, upgrade.value());
                        player.setExperienceLevels(player.experienceLevel + PriceObject.getCurrentPrice(upgrade.value(), player, currentPurchasedAmount - 1));
                        savedData.sellUpgrade(player, upgrade.value());
                        PooSMPSavedData.syncToClient(player);
                    } else {
                        server.sendSystemMessage(Component.literal("Invalid upgrade").withStyle(ChatFormatting.RED));
                    }
                });
            }
        });
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(DataSyncPayload.ID, (payload, context) -> {
            if (PooSMPModClient.SYNC_DATA) {
                PooSMPSavedData.Client.sync(payload.nbt().get("poosmp_data"));
            }
        });
    }
}
