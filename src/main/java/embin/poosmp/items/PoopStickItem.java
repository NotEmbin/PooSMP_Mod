package embin.poosmp.items;

import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PoopStickItem extends Item {
    public PoopStickItem(Properties settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(Level world, Player user, InteractionHand hand) {
        double player_x = user.getPos().getX();
        double player_y = user.getPos().getY();
        double player_z = user.getPos().getZ();
        if (Math.random() > 0.5) {
            world.playSound(null, player_x, player_y + 5, player_z, SoundEvents.HORSE_DEATH, SoundSource.PLAYERS);
            world.explode(null, player_x, player_y, player_z, 5.0F, Level.ExplosionInteraction.NONE);
        } else {
            world.playSound(null, player_x, player_y, player_z, SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(3).value(), SoundSource.PLAYERS);
        }
        user.awardStat(Stats.ITEM_USED.get(this));
        return TypedActionResult.success(user.getItemInHand(hand));
    }
}
