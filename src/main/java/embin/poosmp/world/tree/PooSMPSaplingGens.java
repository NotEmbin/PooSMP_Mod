package embin.poosmp.world.tree;

import embin.poosmp.world.PooSMPConfiguredFeatures;
import java.util.Optional;
import net.minecraft.world.level.block.grower.TreeGrower;

public class PooSMPSaplingGens {
    public static final TreeGrower PALE_OAK = new TreeGrower("poosmp:pale_oak", Optional.of(PooSMPConfiguredFeatures.PALE_OAK_KEY), Optional.empty(), Optional.empty());
}
