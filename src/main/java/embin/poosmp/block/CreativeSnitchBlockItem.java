package embin.poosmp.block;

import embin.poosmp.items.CreativeSnitchItem;
import embin.poosmp.items.component.PooSMPItemComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

public class CreativeSnitchBlockItem extends BlockItem {
    public CreativeSnitchBlockItem(Block block, Properties settings) {
        super(block, settings.component(PooSMPItemComponents.FROM_CREATIVE, true));
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel serverLevel, Entity entity, @Nullable EquipmentSlot equipmentSlot) {
        super.inventoryTick(itemStack, serverLevel, entity, equipmentSlot);
        CreativeSnitchItem.invTick(itemStack, serverLevel, entity);
    }
}