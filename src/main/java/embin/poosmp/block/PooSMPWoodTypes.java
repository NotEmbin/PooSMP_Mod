package embin.poosmp.block;

import embin.poosmp.util.Id;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.WoodType;

public class PooSMPWoodTypes {
    public static final WoodType PALE_OAK = WoodTypeBuilder.copyOf(WoodType.OAK).register(Id.of("pale_oak"), PooSMPBlockSetTypes.PALE_OAK);
}
