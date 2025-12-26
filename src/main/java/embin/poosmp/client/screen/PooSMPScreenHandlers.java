package embin.poosmp.client.screen;

import embin.poosmp.client.screen.shop.ShopScreenHandler;
import embin.poosmp.util.Id;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class PooSMPScreenHandlers {
    public static final MenuType<ShopScreenHandler> SHOP = register("shop", ShopScreenHandler::new);

    private static <T extends AbstractContainerMenu> MenuType<T> register(String id, MenuType.MenuSupplier<T> factory) {
        return Registry.register(BuiltInRegistries.MENU, Id.of(id), new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }
}
