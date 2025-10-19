package embin.poosmp.client;

import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.client.screen.shop.ShopScreenOld;
import embin.poosmp.client.screen.shop.ShopScreenHandler;
import embin.poosmp.client.screen.upgrade.UpgradesScreen;
import embin.poosmp.items.component.PooSMPItemComponents;
import embin.poosmp.networking.PooSMPMessages;
import embin.poosmp.util.Id;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.GrassColors;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PooSMPModClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("poosmp/client");
	public static KeyBinding openUpgradesScreen = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.poosmp.open_upgrades_screen",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_0,
			"category.poosmp.keybinds"
	));
	public static KeyBinding openDeathScreen = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.poosmp.open_death_screen",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_KP_DIVIDE,
			"category.poosmp.keybinds"
	));

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(Id.of("open_upgrades_screen"), client -> {
			while (openUpgradesScreen.wasPressed()) {
				client.setScreen(new UpgradesScreen(client.currentScreen));
			}
			while (openDeathScreen.wasPressed()) {
				client.setScreen(new DeathScreen(Text.literal("nah jk (click title screen to respawn)"), false));
				for (EntityAttributeInstance attribute : client.player.getAttributes().getAttributesToSend()) {
					for (EntityAttributeModifier modifier : attribute.getModifiers()) {
						LOGGER.info("{} / {}", attribute.getAttribute().getIdAsString(), modifier.id().toString());
					}
				}
			}
		});

		//ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
		//	dispatcher.register(
		//			ClientCommandManager.literal("shop").executes(context -> {
		//				FabricClientCommandSource source = context.getSource();
		//				PlayerInventory inv = source.getPlayer().getInventory();
		//				source.getClient().setScreen(new ShopScreenOld(new ShopScreenHandler(0, inv), inv));
		//				return 1;
		//			})
		//	);
		//});

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

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), PooSMPBlocks.FAKE_GRASS_BLOCK);

		ColorProviderRegistry.BLOCK.register(
			(state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getDefaultColor(),
			PooSMPBlocks.FAKE_GRASS_BLOCK
		);

		ColorProviderRegistry.ITEM.register(
			(stack, tintIndex) -> GrassColors.getColor(0.5, 1.0),
			PooSMPBlocks.FAKE_GRASS_BLOCK
		);

		ItemTooltipCallback.EVENT.register(
				Id.of("poosmp:adjust_tooltip"),
				(itemStack, tooltipContext, tooltipType, list) -> {
					if (tooltipType.isAdvanced()) {
						Text disabledText = Text.translatable("item.disabled").formatted(Formatting.RED);
						boolean wasDisabled = false; // cant actually check if item is disabled in this callback
						if (list.getLast().equals(disabledText)) {
							list.removeLast();
							wasDisabled = true;
						}
						if (!itemStack.getComponents().isEmpty()) {
							list.removeLast();
						}
						list.removeLast();
						if (itemStack.contains(PooSMPItemComponents.ITEM_VALUE)) {
							itemStack.get(PooSMPItemComponents.ITEM_VALUE).appendTooltip(tooltipContext, list::add, tooltipType);
						}
						Identifier displayedId = itemStack.getOrDefault(PooSMPItemComponents.DISPLAYED_ID, Registries.ITEM.getId(itemStack.getItem()));
						boolean hasComponent = itemStack.contains(PooSMPItemComponents.DISPLAYED_ID);
						list.add(Text.literal(displayedId.toString()).formatted(hasComponent ? Formatting.DARK_GRAY : Formatting.RED));
						if (!itemStack.getComponents().isEmpty()) {
							list.add(Text.translatable("item.components", itemStack.getComponents().size()).formatted(Formatting.DARK_GRAY));
						}
						if (wasDisabled) {
							list.add(disabledText);
						}
					}
				}
		);

		PooSMPMessages.registerS2CPackets();
	}
}