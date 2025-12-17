package embin.poosmp.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

@Deprecated
public class ItemShopBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(22, ItemStack.EMPTY);
    private static final int payment_slot = 0;
    protected final ContainerData propertyDelegate;
    private int progress = 0;
    private int max_progress = 20;

    public ItemShopBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ItemShopBlockEntity.this.progress;
                    case 1 -> ItemShopBlockEntity.this.max_progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ItemShopBlockEntity.this.progress = value;
                    case 1 -> ItemShopBlockEntity.this.max_progress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    //@Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Object getScreenOpeningData(ServerPlayer serverPlayerEntity) {
        return null;
    }

    @Override
    protected void writeNbt(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Item Shop");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return null;
    }
}
