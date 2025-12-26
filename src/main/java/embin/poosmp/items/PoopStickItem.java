package embin.poosmp.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class PoopStickItem extends Item {
    public PoopStickItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        double player_x = user.getX();
        double player_y = user.getY();
        double player_z = user.getZ();
        if (Math.random() > 0.5) {
            world.playSound(null, player_x, player_y + 5, player_z, SoundEvents.HORSE_DEATH, SoundSource.PLAYERS);
            world.explode(null, player_x, player_y, player_z, 5.0F, Level.ExplosionInteraction.NONE);
        } else {
            world.playSound(null, player_x, player_y, player_z, SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(3).value(), SoundSource.PLAYERS);
        }
        user.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResult.SUCCESS;
    }
}
