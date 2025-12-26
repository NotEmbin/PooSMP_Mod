package embin.poosmp.client.screen.shop;

import embin.poosmp.client.screen.PooSMPScreenHandlers;
import net.minecraft.core.Holder;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Deprecated
public class ShopScreenHandler extends AbstractContainerMenu {
    public static final int MAX_ITEMS_PER_PAGE = 12;
    public static final int MAX_ROWS = 2;
    public int currentPage = 1;
    public Holder<Item> selectedItem = null;

    public ShopScreenHandler(int syncId, Container inventory) {
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
    public ItemStack quickMoveStack(Player player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
