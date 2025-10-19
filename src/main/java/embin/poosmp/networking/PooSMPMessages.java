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
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
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
        PayloadTypeRegistry.playC2S().register(BuyUpgradePayload.ID, BuyUpgradePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(SellUpgradePayload.ID, SellUpgradePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(UpgradeSyncPayload.ID, UpgradeSyncPayload.CODEC);
    }


    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(BuyUpgradePayload.ID, (payload, context) -> {
            MinecraftServer server = context.player().getServer();

            if (server != null && !context.player().getWorld().isClient()) {
                server.execute(() -> {
                    Registry<Upgrade> upgradeRegistry = server.getRegistryManager().get(PooSMPRegistries.Keys.UPGRADE);
                    if (upgradeRegistry.getEntry(payload.upgrade()).isPresent()) {
                        RegistryEntry<Upgrade> upgrade = upgradeRegistry.getEntry(payload.upgrade()).get();
                        int currentPurchasedAmount = ServerUpgradeData.INSTANCE.getPurchasedAmount(context.player(), upgrade.value());
                        ServerUpgradeData.INSTANCE.setPurchasedAmount(context.player(), upgrade.value(), currentPurchasedAmount + 1);
                        upgrade.value().onPurchase(context.player());
                        context.player().getServerWorld().playSound(null, context.player().getBlockPos(), SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS);
                        Upgrade.syncData(context.player());
                        server.sendMessage(Text.literal("Bought upgrade"));
                    } else {
                        server.sendMessage(Text.literal("Invalid upgrade").formatted(Formatting.RED));
                    }
                });
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(SellUpgradePayload.ID, (payload, context) -> {
            MinecraftServer server = context.player().getServer();

            if (server != null && !context.player().getWorld().isClient()) {
                server.execute(() -> {
                    Registry<Upgrade> upgradeRegistry = server.getRegistryManager().get(PooSMPRegistries.Keys.UPGRADE);
                    if (upgradeRegistry.getEntry(payload.upgrade()).isPresent()) {
                        RegistryEntry<Upgrade> upgrade = upgradeRegistry.getEntry(payload.upgrade()).get();
                        upgrade.value().onSell(context.player());
                        int currentPurchasedAmount = ServerUpgradeData.INSTANCE.getPurchasedAmount(context.player(), upgrade.value());
                        ServerUpgradeData.INSTANCE.setPurchasedAmount(context.player(), upgrade.value(), currentPurchasedAmount - 1);
                        Upgrade.syncData(context.player());
                    } else {
                        server.sendMessage(Text.literal("Invalid upgrade").formatted(Formatting.RED));
                    }
                });
            }
        });
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(UpgradeSyncPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                for (String upgradeId : payload.nbt().getKeys()) {
                    Identifier id = Id.of(upgradeId);
                    ClientUpgradeData.INSTANCE.setPurchasedAmount(id, payload.nbt().getInt(upgradeId));
                    ClientUpgradeData.INSTANCE.setBalance(payload.nbt().getDouble("poosmp:balance"));
                }
            });
        });
    }
}
