package embin.poosmp.client.screen;

import embin.poosmp.client.screen.shop.ShopScreenHandler;
import embin.poosmp.util.Id;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class PooSMPScreenHandlers {
    public static final ScreenHandlerType<ShopScreenHandler> SHOP = register("shop", ShopScreenHandler::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, Id.of(id), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }
}
