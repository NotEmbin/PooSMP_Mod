package embin.poosmp.block;

import embin.poosmp.util.ConvertNamespace;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.WoodType;

public class PooSMPWoodTypes {
    public static final WoodType PALE_OAK = WoodTypeBuilder.copyOf(WoodType.OAK).register(ConvertNamespace.convert("pale_oak"), PooSMPBlockSetTypes.PALE_OAK);
}
