package embin.poosmp.upgrade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.util.IEntityDataSaver;
import net.minecraft.client.network.ClientPlayerEntity;
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

    public static PriceObject of(int base_price, int price_increase_base, Optional<PriceIncreasePerLevel> price_increase_per_level) {
        return new PriceObject(base_price, price_increase_base, price_increase_per_level);
    }

    public static PriceObject of(int base_price, int price_increase_base) {
        return new PriceObject(base_price, price_increase_base, Optional.empty());
    }

    public static PriceObject of(int base_price) {
        return new PriceObject(base_price, 0, Optional.empty());
    }

    public static int getCurrentPrice(Upgrade upgrade, PlayerEntity playerEntity) {
        IEntityDataSaver player = (IEntityDataSaver) playerEntity;
        NbtCompound saveData = player.poosmpmod$getPersistentData();
        String id = PooSMPRegistries.UPGRADE.getId(upgrade).toString();
        return 2123456789;
    }

    public static boolean canBeSold(Upgrade upgrade, PlayerEntity playerEntity) {
        IEntityDataSaver player = (IEntityDataSaver) playerEntity;
        NbtCompound saveData = player.poosmpmod$getPersistentData();
        String id = PooSMPRegistries.UPGRADE.getId(upgrade).toString();
        if (!saveData.getCompound("upgrades").contains(id) || saveData.getCompound("upgrades").getInt(id) <= 0) {
            return false;
        }
        return upgrade.can_be_sold();
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
