package embin.poosmp.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

@Deprecated
public class ItemShopBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(22, ItemStack.EMPTY);
    private static final int payment_slot = 0;
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int max_progress = 20;

    public ItemShopBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
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
            public int size() {
                return 2;
            }
        };
    }

    //@Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return null;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Item Shop");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }
}
