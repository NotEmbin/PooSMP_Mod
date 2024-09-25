package embin.poosmp.items;

import embin.poosmp.PooSMPItemComponents;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Arrays;

public class CreativeSnitchItem extends Item {
    public static final String[] known_operators = {"_thecubic_", "lukitagame5", "Embin", "whentheapple"};
    public CreativeSnitchItem(Settings settings) {
        super(settings.component(PooSMPItemComponents.FROM_CREATIVE, true));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!world.isClient && entity.isPlayer()) {
            if (stack.contains(PooSMPItemComponents.FROM_CREATIVE)) {
                if (stack.getOrDefault(PooSMPItemComponents.FROM_CREATIVE, false)) {
                    MinecraftServer server = world.getServer();
                    ServerCommandSource source = server.getCommandSource().withSilent();
                    CommandManager commandManager = server.getCommandManager();
                    Text item_name = Text.translatable(stack.getTranslationKey());
                    String player_name = entity.getName().getString();
                    Text message = Text.literal(player_name).append(" took a ").append(item_name).append(" from Creative Mode or /give");
                    if (Arrays.asList(known_operators).contains(player_name) || player_name.startsWith("Player")) {
                        commandManager.executeWithPrefix(source, "tellraw @a \"" + message.getString() + "\"");
                    }
                }
                stack.set(PooSMPItemComponents.FROM_CREATIVE, null);
            }
        }
    }
}