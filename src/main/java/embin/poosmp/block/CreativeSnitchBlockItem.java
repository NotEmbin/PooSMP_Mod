package embin.poosmp.block;

import embin.poosmp.items.component.PooSMPItemComponents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import java.util.Arrays;

import static embin.poosmp.items.CreativeSnitchItem.known_operators;

public class CreativeSnitchBlockItem extends BlockItem {
    public CreativeSnitchBlockItem(Block block, Properties settings) {
        super(block, settings.component(PooSMPItemComponents.FROM_CREATIVE, true));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!world.isClientSide && entity.isAlwaysTicking()) {
            if (stack.has(PooSMPItemComponents.FROM_CREATIVE)) {
                if (stack.getOrDefault(PooSMPItemComponents.FROM_CREATIVE, false)) {
                    MinecraftServer server = world.getServer();
                    CommandSourceStack source = server.createCommandSourceStack().withSuppressedOutput();
                    Commands commandManager = server.getCommands();
                    Component item_name = Component.translatable(stack.getTranslationKey());
                    String player_name = entity.getName().getString();
                    Component message = Component.literal(player_name).append(" took a ").append(item_name).append(" from Creative Mode or /give");
                    if (Arrays.asList(known_operators).contains(player_name) || player_name.startsWith("Player")) {
                        commandManager.executeWithPrefix(source, "tellraw @a \"" + message.getString() + "\"");
                    }
                }
                stack.set(PooSMPItemComponents.FROM_CREATIVE, null);
            }
        }
    }
}