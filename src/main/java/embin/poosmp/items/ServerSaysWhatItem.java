package embin.poosmp.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ServerSaysWhatItem extends Item {
    public ServerSaysWhatItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        String message = "What?";
        ItemStack stack = user.getStackInHand(hand);
        Text customItemName = stack.get(DataComponentTypes.CUSTOM_NAME);
        if (customItemName != null) {
            message = customItemName.getString();
        }
        if (!world.isClient) {
            MinecraftServer server = user.getServer();
            CommandManager commandManager = server.getCommandManager();
            ServerCommandSource commandSource = server.getCommandSource();
            commandManager.executeWithPrefix(commandSource, "say " + message);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
