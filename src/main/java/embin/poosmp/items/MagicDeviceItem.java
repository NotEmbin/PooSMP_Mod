package embin.poosmp.items;

import embin.poosmp.PooSMPMod;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MagicDeviceItem extends Item {
    public static final float EXPLOSION_POWER = 3.25f;

    public MagicDeviceItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            HitResult hitResult = user.raycast(20.0D, 0.0F, false);
            DamageSources damageSources = new DamageSources(world.getRegistryManager());
            ExplosionBehavior eb = new ExplosionBehavior();
            Vec3d size = new Vec3d(1, 1, 1);
            Vec3d size2 = new Vec3d(20, 20, 20);
            EntityHitResult entityHitResult = ProjectileUtil.raycast(user, size, size2, user.getBoundingBox(), e -> !e.isSpectator() && e.canHit(), 20);
            if (itemStack.contains(DataComponentTypes.CUSTOM_NAME)) {
                String victim_name = itemStack.get(DataComponentTypes.CUSTOM_NAME).getString();
                PlayerEntity victim = getPlayerByName(victim_name, world);
                if (victim != null) {
                    world.createExplosion(null, damageSources.explosion(null, null), eb, victim.getPos(), EXPLOSION_POWER, false, World.ExplosionSourceType.TRIGGER);
                } else {
                    user.sendMessage(Text.literal("No player with such name exists!").formatted(Formatting.YELLOW), true);
                }
            } else if (entityHitResult != null && entityHitResult.getEntity() != null) {
                Vec3d pos = entityHitResult.getEntity().getPos();
                world.createExplosion(null, damageSources.explosion(null, null), eb, pos, EXPLOSION_POWER, false, World.ExplosionSourceType.TRIGGER);
            } else if (hitResult.getType() == HitResult.Type.BLOCK) {
                explodeBlock(hitResult, world);
            } else {
                Vec3d pos = hitResult.getPos();
                world.createExplosion(null, damageSources.explosion(null, null), eb, pos, EXPLOSION_POWER, false, World.ExplosionSourceType.TRIGGER);
            }
            itemStack.damage(1, user, LivingEntity.getSlotForHand(hand));
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(itemStack);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getWorld();
        if (!world.isClient()) {
            Vec3d pos = entity.getPos();
            DamageSources damageSources = new DamageSources(world.getRegistryManager());
            ExplosionBehavior eb = new ExplosionBehavior();
            world.createExplosion(null, damageSources.explosion(null, null), eb, pos, EXPLOSION_POWER, false, World.ExplosionSourceType.TRIGGER);
            stack.damage(1, user, LivingEntity.getSlotForHand(hand));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Nullable
    public static PlayerEntity getPlayerByName(String name, World world) {
        for (PlayerEntity player : world.getPlayers()) {
            if (Objects.equals(name, player.getNameForScoreboard())) {
                return player;
            }
        }
        return null;
    }

    private static void explodeBlock(HitResult hitResult, World world) {
        BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
        double x = blockPos.getX();
        double y = blockPos.getY() + 1.0D;
        double z = blockPos.getZ();
        world.createExplosion(null, x, y, z, EXPLOSION_POWER, World.ExplosionSourceType.TRIGGER);
    }
}
