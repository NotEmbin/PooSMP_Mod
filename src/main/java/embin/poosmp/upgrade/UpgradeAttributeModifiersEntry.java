package embin.poosmp.upgrade;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class UpgradeAttributeModifiersEntry {
    private final RegistryEntry<EntityAttribute> attribute;
    private final double amountPerLevel;

    private UpgradeAttributeModifiersEntry(RegistryEntry<EntityAttribute> attribute, double amountPerLevel) {
        this.attribute = attribute;
        this.amountPerLevel = amountPerLevel;
    }

    public static UpgradeAttributeModifiersEntry of(RegistryEntry<EntityAttribute> attribute, double amountPerLevel) {
        return new UpgradeAttributeModifiersEntry(attribute, amountPerLevel);
    }

    public List<AttributeModifiersComponent.Entry> build(Identifier upgrade, int amountPurchased) {
        List<AttributeModifiersComponent.Entry> attributes = new ArrayList<>(amountPurchased);
        for (int i = 1; i <= amountPurchased; i++) {
            attributes.add(new AttributeModifiersComponent.Entry(
                    this.attribute,
                    new EntityAttributeModifier(upgrade.withSuffixedPath("_" + i), this.amountPerLevel, EntityAttributeModifier.Operation.ADD_VALUE),
                    AttributeModifierSlot.ANY
            ));
        }
        return attributes;
    }
}
