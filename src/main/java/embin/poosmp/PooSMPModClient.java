package embin.poosmp;

import embin.poosmp.block.PooSMPBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;

public class PooSMPModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
			Blocks.MOSS_CARPET,
			Blocks.RED_CARPET,
			Blocks.YELLOW_CARPET,
			Blocks.ORANGE_CARPET,
			Blocks.BLACK_CARPET,
			Blocks.BLUE_CARPET,
			Blocks.CYAN_CARPET,
			Blocks.LIGHT_BLUE_CARPET,
			Blocks.LIME_CARPET,
			Blocks.LIGHT_GRAY_CARPET,
			Blocks.GRAY_CARPET,
			Blocks.BROWN_CARPET,
			Blocks.GREEN_CARPET,
			Blocks.MAGENTA_CARPET,
			Blocks.PINK_CARPET,
			Blocks.MAGENTA_CARPET,
			Blocks.WHITE_CARPET,
			PooSMPBlocks.PALE_OAK_SAPLING,
			PooSMPBlocks.PALE_HANGING_MOSS,
			PooSMPBlocks.RESIN_CLUMP,
			PooSMPBlocks.PALE_MOSS_CARPET,
			PooSMPBlocks.POTTED_PALE_OAK_SAPLING
		);
	}
}