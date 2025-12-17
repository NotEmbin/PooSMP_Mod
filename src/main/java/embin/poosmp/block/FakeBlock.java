package embin.poosmp.block;

import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import java.util.List;

public class FakeBlock extends Block {
    private final Block fakeOf;

    public FakeBlock(Block block, Properties settings) {
        super(settings.noCollision());
        this.fakeOf = block;
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        return List.of(this.asItem().getDefaultInstance());
    }
}
