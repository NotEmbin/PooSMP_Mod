package embin.poosmp.networking;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.client.ClientUpgradeData;
import embin.poosmp.networking.payload.BuyUpgradePayload;
import embin.poosmp.networking.payload.SellUpgradePayload;
import embin.poosmp.networking.payload.UpgradeSyncPayload;
import embin.poosmp.upgrade.ServerUpgradeData;
import embin.poosmp.upgrade.Upgrade;
import embin.poosmp.util.Id;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class PooSMPMessages {
    public static final Identifier BUY_UPGRADE = Id.of("buy_upgrade");
    public static final Identifier SELL_UPGRADE = Id.of("sell_upgrade");
    public static final Identifier UPGRADE_SYNC = Id.of("upgrade_sync");

    public static void register() {
    }


    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(BuyUpgradePayload.ID, BuyUpgradePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(BuyUpgradePayload.ID, (payload, context) -> {
            MinecraftServer server = context.player().getServer();

            if (server != null && !context.player().getWorld().isClient()) {
                server.execute(() -> {
                    Upgrade upgrade = PooSMPRegistries.UPGRADE.get(payload.upgrade());
                    if (upgrade != null) {
                        int currentPurchasedAmount = ServerUpgradeData.INSTANCE.getPurchasedAmount(upgrade);
                        ServerUpgradeData.INSTANCE.setPurchasedAmount(upgrade, currentPurchasedAmount + 1);
                        upgrade.onPurchase(context.player());
                        context.player().getServerWorld().playSound(null, context.player().getBlockPos(), SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS);
                        Upgrade.syncData(context.player());
                    } else {
                        server.sendMessage(Text.literal("Invalid upgrade").formatted(Formatting.RED));
                    }
                });
            }
        });

        PayloadTypeRegistry.playC2S().register(SellUpgradePayload.ID, SellUpgradePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SellUpgradePayload.ID, (payload, context) -> {
            MinecraftServer server = context.player().getServer();

            if (server != null && !context.player().getWorld().isClient()) {
                server.execute(() -> {
                    Upgrade upgrade = PooSMPRegistries.UPGRADE.get(payload.upgrade());
                    if (upgrade != null) {
                        upgrade.onSell(context.player());
                        int currentPurchasedAmount = ServerUpgradeData.INSTANCE.getPurchasedAmount(upgrade);
                        ServerUpgradeData.INSTANCE.setPurchasedAmount(upgrade, currentPurchasedAmount - 1);
                        Upgrade.syncData(context.player());
                    } else {
                        server.sendMessage(Text.literal("Invalid upgrade").formatted(Formatting.RED));
                    }
                });
            }
        });
    }

    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(UpgradeSyncPayload.ID, UpgradeSyncPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(UpgradeSyncPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                for (String upgradeId : payload.nbt().getKeys()) {
                    Identifier id = Id.of(upgradeId);
                    ClientUpgradeData.INSTANCE.setPurchasedAmount(id, payload.nbt().getInt(upgradeId));
                }
            });
        });
    }
}
