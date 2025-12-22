package embin.poosmp.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public interface ImpersonatingBlock {
    Block getBlockImpersonatingAs();

    default Identifier getBlockIdImpersonatingAs() {
        return BuiltInRegistries.BLOCK.getKey(this.getBlockImpersonatingAs());
    }
}
