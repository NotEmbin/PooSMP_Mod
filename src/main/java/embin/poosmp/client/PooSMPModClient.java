package embin.poosmp.client;

import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.client.screen.UpgradesScreen;
import embin.poosmp.networking.PooSMPMessages;
import embin.poosmp.util.Id;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class PooSMPModClient implements ClientModInitializer {
	public static KeyBinding openUpgradesScreen = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.poosmp.open_upgrades_screen",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_0,
			"category.poosmp.keybinds"
	));

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(Id.of("open_upgrades_screen"), client -> {
			while (openUpgradesScreen.wasPressed()) {
				client.setScreen(new UpgradesScreen(client.currentScreen));
			}
		});

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

		PooSMPMessages.registerS2CPackets();
	}
}