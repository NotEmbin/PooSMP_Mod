package embin.poosmp.block;

import net.minecraft.world.level.block.Block;

public class FakeBlock extends Block {
    private final Block fakeOf;

    public FakeBlock(Block block, Properties settings) {
        super(settings.noCollision());
        this.fakeOf = block;
    }
}
