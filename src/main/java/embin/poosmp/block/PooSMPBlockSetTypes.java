package embin.poosmp.block;

import embin.poosmp.util.ConvertNamespace;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.block.BlockSetType;

public class PooSMPBlockSetTypes {
    public static final BlockSetType PALE_OAK = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).register(ConvertNamespace.convert("pale_oak"));
}
