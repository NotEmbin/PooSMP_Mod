package embin.poosmp.world.tree;

import embin.poosmp.world.PooSMPConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class PooSMPSaplingGens {
    public static final SaplingGenerator PALE_OAK = new SaplingGenerator("poosmp:pale_oak", Optional.of(PooSMPConfiguredFeatures.PALE_OAK_KEY), Optional.empty(), Optional.empty());
}
