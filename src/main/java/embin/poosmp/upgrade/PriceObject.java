package embin.poosmp.upgrade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.client.ClientUpgradeData;
import embin.poosmp.upgrade.ServerUpgradeData;
import embin.poosmp.util.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

public record PriceObject(int base_price, int price_increase_base, Optional<PriceIncreasePerLevel> price_increase_per_level) {
    public static final Codec<PriceObject> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codecs.rangedInt(0, 500_000_000).fieldOf("base_price").forGetter(PriceObject::base_price),
                    Codecs.rangedInt(0, 500_000_000).fieldOf("price_increase_base").forGetter(PriceObject::price_increase_base),
                    PriceIncreasePerLevel.CODEC.optionalFieldOf("price_increase_per_level").forGetter(PriceObject::price_increase_per_level)
            ).apply(instance, PriceObject::new)
    );

    public static PriceObject of(int base_price, int price_increase_base, int value, float multiplier) {
        return new PriceObject(base_price, price_increase_base, Optional.of(new PriceIncreasePerLevel(value, multiplier)));
    }

    public static PriceObject of(int base_price, int price_increase_base, Optional<PriceIncreasePerLevel> price_increase_per_level) {
        return new PriceObject(base_price, price_increase_base, price_increase_per_level);
    }

    public static PriceObject of(int base_price, int price_increase_base) {
        return new PriceObject(base_price, price_increase_base, Optional.empty());
    }

    public static PriceObject of(int base_price) {
        return new PriceObject(base_price, 0, Optional.empty());
    }

    public static int getCurrentPrice(Upgrade upgrade, PlayerEntity playerEntity, int amountPurchased) {
        /*
        int basePrice = upgrade.price().base_price();
        int priceIncreaseBase = upgrade.price().price_increase_base();

        int priceIncrease = priceIncreaseBase;
        if (amountPurchased > 1) {
            Optional<PriceIncreasePerLevel> pipl = upgrade.price().price_increase_per_level();
            if (pipl.isPresent()) {
                priceIncrease = priceIncrease + (pipl.get().value() * (amountPurchased - 1));
                if (priceIncrease < priceIncreaseBase) priceIncrease = priceIncreaseBase;
            }
        }

        return (basePrice + priceIncrease);
         */
        if (amountPurchased > 0) {
            return upgrade.price().base_price() + (upgrade.price().price_increase_base() * (amountPurchased));
        }
        return upgrade.price().base_price();
    }

    public static int getCurrentPrice(Upgrade upgrade, PlayerEntity playerEntity) {
        return getCurrentPrice(upgrade, playerEntity, ClientUpgradeData.INSTANCE.getPurchasedAmount(upgrade));
    }

    public static boolean canBeSold(Upgrade upgrade, PlayerEntity playerEntity) {
        if (ServerUpgradeData.INSTANCE.getPurchasedAmount(upgrade) <= 0) return false;
        return upgrade.can_be_sold();
    }

    public static int getSellPrice(Upgrade upgrade, PlayerEntity playerEntity, int amountPurchased) {
        return upgrade.price().base_price();
    }

    public static int getSellPrice(Upgrade upgrade, PlayerEntity playerEntity) {
        return getSellPrice(upgrade, playerEntity, upgrade.amountPurchased());
    }

    public record PriceIncreasePerLevel(int value, float multiplier) {
        public static final Codec<PriceIncreasePerLevel> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codecs.rangedInt(0, 500_000_000).fieldOf("value").forGetter(PriceIncreasePerLevel::value),
                        Codecs.POSITIVE_FLOAT.fieldOf("multiplier").forGetter(PriceIncreasePerLevel::multiplier)
                ).apply(instance, PriceIncreasePerLevel::new)
        );
    }
}
