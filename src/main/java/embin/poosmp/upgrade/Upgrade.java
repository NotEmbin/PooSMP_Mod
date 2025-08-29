package embin.poosmp.upgrade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.networking.payload.UpgradeSyncPayload;
import embin.poosmp.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.List;
import java.util.Optional;

public record Upgrade(
        RegistryEntry<Item> icon, PriceObject price, boolean can_be_sold, Optional<Integer> max_purchases,
        Optional<List<AttributeModifiersComponent.Entry>> attribute_modifiers
) {
    public static final Codec<Upgrade> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Registries.ITEM.getEntryCodec().fieldOf("icon").forGetter(Upgrade::icon),
                    PriceObject.CODEC.fieldOf("price").forGetter(Upgrade::price),
                    Codec.BOOL.optionalFieldOf("can_be_sold", true).forGetter(Upgrade::can_be_sold),
                    Codecs.POSITIVE_INT.optionalFieldOf("max_purchases").forGetter(Upgrade::max_purchases),
                    AttributeModifiersComponent.Entry.CODEC.listOf().optionalFieldOf("attribute_modifiers").forGetter(Upgrade::attribute_modifiers)
            ).apply(instance, Upgrade::new)
    );

    public void buyUpgrade(ServerPlayerEntity player) {
    }

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
}
