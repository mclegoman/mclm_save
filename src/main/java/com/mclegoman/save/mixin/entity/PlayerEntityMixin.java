/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.entity;

import com.mclegoman.save.entity.SaveModEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements SaveModEntity {
	@Shadow public PlayerInventory inventory;
	public PlayerEntityMixin(World world) {
		super(world);
	}
	@Redirect(method = "onKilled", at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z"))
	private boolean save$isNotch(String instance, Object o) {
		return instance.equals(o) || instance.equals("Phantazap") || instance.equals("legotaylor");
	}
	@Inject(method = "onKilled", at = @At("HEAD"))
	private void save$onKilled(Entity player, CallbackInfo ci) {
		for (int i = 0; i < this.inventory.inventorySlots.length; i++) {
			if (this.inventory.inventorySlots[i] != null) {
				save$dropItem(this.inventory.inventorySlots[i], true);
				this.inventory.inventorySlots[i] = null;
			}
		}
		for (int i = 0; i < this.inventory.armorSlots.length; i++) {
			if (this.inventory.armorSlots[i] != null) {
				save$dropItem(this.inventory.armorSlots[i], true);
				this.inventory.armorSlots[i] = null;
			}
		}
	}
	public String save$id() {
		return null;
	}
	@Unique
	public void save$dropItem(ItemStack itemStack, boolean bl) {
		if (itemStack != null) {
			ItemEntity itemEntity = new ItemEntity(this.world, this.x, this.y - 0.30000001192092896, this.z, itemStack);
			itemEntity.pickUpDelay = 40;
			float x;
			float y;
			if (bl) {
				x = this.random.nextFloat() * 0.5F;
				y = this.random.nextFloat() * 3.1415927F * 2.0F;
				itemEntity.velocityX = -MathHelper.sin(y) * x;
				itemEntity.velocityZ = MathHelper.cos(y) * x;
				itemEntity.velocityY = 0.20000000298023224;
			} else {
				itemEntity.velocityX = -MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * 0.3F;
				itemEntity.velocityZ = MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * 0.3F;
				itemEntity.velocityY = -MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * 0.3F + 0.1F;
				x = this.random.nextFloat() * 3.1415927F * 2.0F;
				y = 0.02F * this.random.nextFloat();
				itemEntity.velocityX += Math.cos(x) * (double)y;
				itemEntity.velocityY += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
				itemEntity.velocityZ += Math.sin(x) * (double)y;
			}
			this.world.addEntity(itemEntity);
		}
	}
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;refreshPositionAndAngles(DDDFF)V"))
	public void save$fixRespawn(PlayerEntity player, double d, double e, double f, float g, float h) {
		player.refreshPositionAndAngles(world.spawnpointX, world.spawnpointY, world.spawnpointZ, g, h);
	}
}
