package embin.poosmp.upgrade;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class UpgradeAttributeModifiersEntry {
    private final Holder<Attribute> attribute;
    private final double amountPerLevel;

    private UpgradeAttributeModifiersEntry(Holder<Attribute> attribute, double amountPerLevel) {
        this.attribute = attribute;
        this.amountPerLevel = amountPerLevel;
    }

    public static UpgradeAttributeModifiersEntry of(Holder<Attribute> attribute, double amountPerLevel) {
        return new UpgradeAttributeModifiersEntry(attribute, amountPerLevel);
    }

    public List<ItemAttributeModifiers.Entry> build(Identifier upgrade, int amountPurchased) {
        List<ItemAttributeModifiers.Entry> attributes = new ArrayList<>(amountPurchased);
        for (int i = 1; i <= amountPurchased; i++) {
            attributes.add(new ItemAttributeModifiers.Entry(
                    this.attribute,
                    new AttributeModifier(upgrade.withSuffix("_" + i), this.amountPerLevel, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.ANY
            ));
        }
        return attributes;
    }
}
