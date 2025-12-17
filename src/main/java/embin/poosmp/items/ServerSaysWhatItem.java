package embin.poosmp.items;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ServerSaysWhatItem extends Item {
    public ServerSaysWhatItem(Properties settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(Level world, Player user, InteractionHand hand) {
        String message = "What?";
        ItemStack stack = user.getItemInHand(hand);
        Component customItemName = stack.get(DataComponents.CUSTOM_NAME);
        if (customItemName != null) {
            message = customItemName.getString();
        }
        if (message.contains("testicle") || message.contains("rubbing") || message.contains("Rubbing")) {
            message = "cubey smells";
        }
        if (!world.isClientSide) {
            MinecraftServer server = user.getServer();
            Commands commandManager = server.getCommands();
            CommandSourceStack commandSource = server.createCommandSourceStack();
            commandManager.executeWithPrefix(commandSource, "say " + message);
        }
        return TypedActionResult.success(user.getItemInHand(hand));
    }
}
