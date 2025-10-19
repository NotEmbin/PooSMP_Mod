package embin.poosmp.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;

import java.util.List;

public class FakeBlock extends Block {
    private final Block fakeOf;

    public FakeBlock(Block block, Settings settings) {
        super(settings.noCollision());
        this.fakeOf = block;
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        return List.of(this.asItem().getDefaultStack());
    }
}
