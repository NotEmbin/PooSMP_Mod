package embin.poosmp.upgrade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.PooSMPSavedData;
import embin.poosmp.networking.payload.DataSyncPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record Upgrade(
        Item icon, PriceObject price, boolean can_be_sold, Optional<Integer> max_purchases,
        Optional<List<ItemAttributeModifiers.Entry>> attribute_modifiers, Optional<MobEffectInstance> statusEffect
) {
    public static final Codec<Upgrade> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("icon").forGetter(Upgrade::icon),
                    PriceObject.CODEC.fieldOf("price").forGetter(Upgrade::price),
                    Codec.BOOL.optionalFieldOf("can_be_sold", true).forGetter(Upgrade::can_be_sold),
                    ExtraCodecs.POSITIVE_INT.optionalFieldOf("max_purchases").forGetter(Upgrade::max_purchases),
                    ItemAttributeModifiers.Entry.CODEC.listOf().optionalFieldOf("attribute_modifiers").forGetter(Upgrade::attribute_modifiers),
                    MobEffectInstance.CODEC.optionalFieldOf("status_effect").forGetter(Upgrade::statusEffect)
            ).apply(instance, Upgrade::new)
    );

    public Component getName(Registry<Upgrade> registry) {
        return Component.translatable(registry.getKey(this).toLanguageKey("upgrade"));
    }

    public Component getIdAsText(Registry<Upgrade> registry) {
        return Component.literal(registry.getKey(this).toString()).withStyle(ChatFormatting.DARK_GRAY);
    }

    public Identifier getId(Registry<Upgrade> registry) {
        return registry.getKey(this);
    }

    public int amountPurchased(Player player) {
        PooSMPSavedData savedData = PooSMPSavedData.get(player);
        return savedData != null ? savedData.upgradePurchaseAmount(player, this) : 0;
    }

    public static void syncData(ServerPlayer player) {
        CompoundTag data = new CompoundTag();
        data.put("poosmp_data", PooSMPSavedData.get(player.level().getServer()).asNbt());
        ServerPlayNetworking.send(player, new DataSyncPayload(data));
    }

    public List<ItemAttributeModifiers.Entry> getAttributesForAmount(int amount, Registry<Upgrade> upgradeRegistry) {
        if (amount < 0) return List.of();
        if (this.attribute_modifiers.isPresent()) {
            List<ItemAttributeModifiers.Entry> attributes = new ArrayList<>(amount * this.attribute_modifiers.get().size());
            for (ItemAttributeModifiers.Entry attributeEntry : this.attribute_modifiers.get()) {
                //int amountPerLevel = upgradeRegistry.getEntry(this).isIn(PooSMPTags.Upgrades.DOUBLE_ATTRIBUTE_GIVE) ? 2 : 1;
                Identifier id = attributeEntry.modifier().id();
                attributes.addAll(UpgradeAttributeModifiersEntry.of(attributeEntry.attribute(), attributeEntry.modifier().amount()).build(id, amount));
            }
            return attributes;
        }
        return List.of();
    }

    public void onTick(ServerPlayer player) {
        if (this.statusEffect.isPresent() && ServerUpgradeData.INSTANCE.getPurchasedAmount(player, this) > 0) {
            if (!player.getActiveEffects().contains(this.statusEffect.get())) {
                player.addEffect(this.statusEffect.get());
            }
        }
    }

    public void onPurchase(Player player) {
        if (this.attribute_modifiers.isPresent()) {
            MinecraftServer server = player.level().getServer();
            Commands commandManager = server.getCommands();
            CommandSourceStack commandSource = server.createCommandSourceStack();

            Registry<Upgrade> upgradeRegistry = server.registryAccess().lookupOrThrow(PooSMPRegistries.Keys.UPGRADE);

            for (ItemAttributeModifiers.Entry entry : this.getAttributesForAmount(this.amountPurchased(player), upgradeRegistry)) {
                Holder<Attribute> attribute = entry.attribute();
                String attributeId = attribute.getRegisteredName();
                String id = entry.modifier().id().toString();
                double amount = entry.modifier().amount();
                String playerUuid = player.getStringUUID();
                int index = Integer.parseInt(Arrays.stream(entry.modifier().id().toString().split("_")).toList().getLast());

                if (index >= this.amountPurchased(player)) {
                    commandManager.performPrefixedCommand(commandSource, String.format("attribute %s %s modifier add %s %s add_value",
                            playerUuid, attributeId, id, amount
                    ));
                }
            }
        }
    }

    public void onSell(Player player) {
        this.statusEffect.ifPresent(instance -> player.removeEffect(instance.getEffect()));

        if (this.attribute_modifiers.isPresent()) {
            MinecraftServer server = player.level().getServer();
            Commands commandManager = server.getCommands();
            CommandSourceStack commandSource = server.createCommandSourceStack();

            int purchasedAmount = this.amountPurchased(player);
            Registry<Upgrade> upgradeRegistry = server.registryAccess().lookupOrThrow(PooSMPRegistries.Keys.UPGRADE);

            for (ItemAttributeModifiers.Entry entry : this.getAttributesForAmount(purchasedAmount, upgradeRegistry)) {
                Holder<Attribute> attribute = entry.attribute();
                String attributeId = attribute.getRegisteredName();
                String id = entry.modifier().id().toString();
                int index = Integer.parseInt(Arrays.stream(entry.modifier().id().toString().split("_")).toList().getLast());
                //PooSMPMod.LOGGER.info(Arrays.toString(entry.modifier().id().toString().split("_")));
                //PooSMPMod.LOGGER.info("Index: {}, Amount Purchased: {}, Entry: {}", index, this.amountPurchased(), id);
                String playerUuid = player.getStringUUID();

                if (index >= purchasedAmount) {
                    commandManager.performPrefixedCommand(commandSource, String.format(
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
