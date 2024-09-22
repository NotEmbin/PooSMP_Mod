package embin.poosmp.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PoopStickItem extends Item {
    public PoopStickItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        double player_x = user.getPos().getX();
        double player_y = user.getPos().getY();
        double player_z = user.getPos().getZ();
        if (Math.random() > 0.5) {
            world.playSound(null, player_x, player_y + 5, player_z, SoundEvents.ENTITY_HORSE_DEATH, SoundCategory.PLAYERS);
            world.createExplosion(null, player_x, player_y, player_z, 5.0F, World.ExplosionSourceType.NONE);
        } else {
            world.playSound(null, player_x, player_y, player_z, SoundEvents.GOAT_HORN_SOUNDS.get(3).value(), SoundCategory.PLAYERS);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
