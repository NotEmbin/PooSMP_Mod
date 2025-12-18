package embin.poosmp.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;

public class ddededodediamanteBlock extends Block {
    public static final MapCodec<ddededodediamanteBlock> CODEC = simpleCodec(ddededodediamanteBlock::new);

    @Override
    public MapCodec<? extends ddededodediamanteBlock> codec() {
        return CODEC;
    }

    public ddededodediamanteBlock(Properties settings) {
        super(settings);
    }
}
