package embin.poosmp.upgrade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.util.IEntityDataSaver;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

public record Upgrade(RegistryEntry<Item> icon, PriceObject price, boolean can_be_sold, Optional<Integer> max_purchases) {
    public static final Codec<Upgrade> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Registries.ITEM.getEntryCodec().fieldOf("icon").forGetter(Upgrade::icon),
                    PriceObject.CODEC.fieldOf("price").forGetter(Upgrade::price),
                    Codec.BOOL.optionalFieldOf("can_be_sold", true).forGetter(Upgrade::can_be_sold),
                    Codecs.POSITIVE_INT.optionalFieldOf("max_purchases").forGetter(Upgrade::max_purchases)
            ).apply(instance, Upgrade::new)
    );

    public void buyUpgrade(IEntityDataSaver player) {
        boolean registered = false;
        for (Upgrade upgrade : PooSMPRegistries.UPGRADE) {
            if (upgrade == this) {
                registered = true;
                break;
            }
        }
        if (!registered) {
            return;
        }
        NbtCompound saveData = player.poosmpmod$getPersistentData();
        String thisId = PooSMPRegistries.UPGRADE.getId(this).toString();
        int amount_bought;
        if (!saveData.getCompound("upgrades").contains(thisId)) {
            amount_bought = 0;
        } else {
            amount_bought = saveData.getCompound("upgrades").getInt(thisId);
        }
        int current_price = price().base_price();
    }

    public Text getName() {
        return Text.translatable(PooSMPRegistries.UPGRADE.getId(this).toTranslationKey("upgrade"));
    }

    public Text getIdAsText() {
        return Text.literal(PooSMPRegistries.UPGRADE.getId(this).toString()).formatted(Formatting.DARK_GRAY);
    }
}
