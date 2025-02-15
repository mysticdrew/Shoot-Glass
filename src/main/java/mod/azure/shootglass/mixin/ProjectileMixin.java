package mod.azure.shootglass.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.shootglass.ShootGlassMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

@Mixin(AbstractArrow.class)
public abstract class ProjectileMixin extends Projectile {

	public ProjectileMixin(EntityType<? extends Projectile> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(at = @At("HEAD"), method = "onHitBlock")
	private void breakglass(BlockHitResult blockHitResult, CallbackInfo info) {
		if (level().getBlockState(blockHitResult.getBlockPos()).is(ShootGlassMod.BREAKABLE_BLOCKS)) {
			level().destroyBlock(blockHitResult.getBlockPos(), true);
			if (!this.level().isClientSide)
				this.remove(Entity.RemovalReason.DISCARDED);
		}
	}
}