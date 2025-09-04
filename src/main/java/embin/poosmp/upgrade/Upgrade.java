package embin.poosmp.upgrade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embin.poosmp.PooSMPMod;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.networking.payload.UpgradeSyncPayload;
import embin.poosmp.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record Upgrade(
        RegistryEntry<Item> icon, PriceObject price, boolean can_be_sold, Optional<Integer> max_purchases,
        Optional<List<AttributeModifiersComponent.Entry>> attribute_modifiers, Optional<StatusEffectInstance> statusEffect
) {
    public static final Codec<Upgrade> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Registries.ITEM.getEntryCodec().fieldOf("icon").forGetter(Upgrade::icon),
                    PriceObject.CODEC.fieldOf("price").forGetter(Upgrade::price),
                    Codec.BOOL.optionalFieldOf("can_be_sold", true).forGetter(Upgrade::can_be_sold),
                    Codecs.POSITIVE_INT.optionalFieldOf("max_purchases").forGetter(Upgrade::max_purchases),
                    AttributeModifiersComponent.Entry.CODEC.listOf().optionalFieldOf("attribute_modifiers").forGetter(Upgrade::attribute_modifiers),
                    StatusEffectInstance.CODEC.optionalFieldOf("status_effect").forGetter(Upgrade::statusEffect)
            ).apply(instance, Upgrade::new)
    );

    public Text getName() {
        return Text.translatable(PooSMPRegistries.UPGRADE.getId(this).toTranslationKey("upgrade"));
    }

    public Text getIdAsText() {
        return Text.literal(PooSMPRegistries.UPGRADE.getId(this).toString()).formatted(Formatting.DARK_GRAY);
    }

    public Identifier getId() {
        return PooSMPRegistries.UPGRADE.getId(this);
    }

    public int amountPurchased() {
        return ServerUpgradeData.INSTANCE.getPurchasedAmount(this);
    }

    public static void syncData(ServerPlayerEntity player) {
        NbtCompound data = new NbtCompound();
        for (Identifier upgrade : ServerUpgradeData.INSTANCE.savedUpgrades()) {
            data.putInt(upgrade.toString(), ServerUpgradeData.INSTANCE.getPurchasedAmount(upgrade));
        }
        ServerPlayNetworking.send(player, new UpgradeSyncPayload(data));
    }

    public List<AttributeModifiersComponent.Entry> getAttributesForAmount(int amount) {
        if (amount < 0) return List.of();
        if (this.attribute_modifiers.isPresent()) {
            List<AttributeModifiersComponent.Entry> attributes = new ArrayList<>(amount * this.attribute_modifiers.get().size());
            for (AttributeModifiersComponent.Entry attributeEntry : this.attribute_modifiers.get()) {
                int amountPerLevel = this == Upgrades.HEALTH_INCREASE ? 2 : 1;
                Identifier id = attributeEntry.modifier().id();
                attributes.addAll(UpgradeAttributeModifiersEntry.of(attributeEntry.attribute(), amountPerLevel).build(id, amount));
            }
            return attributes;
        }
        return List.of();
    }

    public void onTick(ServerPlayerEntity player) {
        if (this.statusEffect.isPresent() && ServerUpgradeData.INSTANCE.getPurchasedAmount(this) > 0) {
            if (!player.getStatusEffects().contains(this.statusEffect.get())) {
                player.addStatusEffect(this.statusEffect.get());
            }
        }
    }

    public void onPurchase(ServerPlayerEntity player) {
        if (this.attribute_modifiers.isPresent()) {
            MinecraftServer server = player.getServer();
            CommandManager commandManager = server.getCommandManager();
            ServerCommandSource commandSource = server.getCommandSource();

            for (AttributeModifiersComponent.Entry entry : this.getAttributesForAmount(this.amountPurchased())) {
                RegistryEntry<EntityAttribute> attribute = entry.attribute();
                String attributeId = attribute.getIdAsString();
                String id = entry.modifier().id().toString();
                double amount = entry.modifier().value();
                String playerUuid = player.getUuidAsString();
                int index = Integer.parseInt(Arrays.stream(entry.modifier().id().toString().split("_")).toList().getLast());
                //PooSMPMod.LOGGER.info("Index: {}, Amount Purchased: {}, Entry: {}", index, this.amountPurchased(), id);

                if (index >= this.amountPurchased()) {
                    commandManager.executeWithPrefix(commandSource, String.format("attribute %s %s modifier add %s %s add_value",
                            playerUuid, attributeId, id, amount
                    ));
                }
            }
        }
    }

    public void onSell(ServerPlayerEntity player) {
        this.statusEffect.ifPresent(instance -> player.removeStatusEffect(instance.getEffectType()));

        if (this.attribute_modifiers.isPresent()) {
            MinecraftServer server = player.getServer();
            CommandManager commandManager = server.getCommandManager();
            ServerCommandSource commandSource = server.getCommandSource();

            int purchasedAmount = this.amountPurchased();


            for (AttributeModifiersComponent.Entry entry : this.getAttributesForAmount(purchasedAmount)) {
                RegistryEntry<EntityAttribute> attribute = entry.attribute();
                String attributeId = attribute.getIdAsString();
                String id = entry.modifier().id().toString();
                int index = Integer.parseInt(Arrays.stream(entry.modifier().id().toString().split("_")).toList().getLast());
                //PooSMPMod.LOGGER.info(Arrays.toString(entry.modifier().id().toString().split("_")));
                //PooSMPMod.LOGGER.info("Index: {}, Amount Purchased: {}, Entry: {}", index, this.amountPurchased(), id);
                String playerUuid = player.getUuidAsString();

                if (index >= purchasedAmount) {
                    commandManager.executeWithPrefix(commandSource, String.format(
                            "attribute %s %s modifier remove %s",
                            playerUuid, attributeId, id
                    ));
                }
            }
        }
    }

    public int maxPurchases() {
        return this.max_purchases.orElse(Integer.MAX_VALUE);
    }
}
