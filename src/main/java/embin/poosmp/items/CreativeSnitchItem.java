package embin.poosmp.items;

import embin.poosmp.items.component.PooSMPItemComponents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.util.Arrays;

public class CreativeSnitchItem extends Item {
    public static final String[] known_operators = {"_thecubic_", "ddededodediamant", "Embin", "whentheapple"};
    public CreativeSnitchItem(Properties settings, boolean snitch) {
        super(settings.component(PooSMPItemComponents.FROM_CREATIVE, snitch));
    }
    public CreativeSnitchItem(Properties settings) {
        super(settings.component(PooSMPItemComponents.FROM_CREATIVE, true));
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