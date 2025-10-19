package embin.poosmp.client.screen.shop;

import embin.poosmp.client.screen.PooSMPScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

@Deprecated
public class ShopScreenHandler extends ScreenHandler {
    public static final int MAX_ITEMS_PER_PAGE = 12;
    public static final int MAX_ROWS = 2;
    public int currentPage = 1;
    public RegistryEntry<Item> selectedItem = null;

    public ShopScreenHandler(int syncId, Inventory inventory) {
        super(PooSMPScreenHandlers.SHOP, syncId);

        // Inventory slots
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(inventory, column + row * 9 + 9, 36 + column * 18, 137 + row * 18));
            }
        }

        // Hotbar slots
        for (int slot = 0; slot < 9; slot++) {
            this.addSlot(new Slot(inventory, slot, 36 + slot * 18, 195));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
