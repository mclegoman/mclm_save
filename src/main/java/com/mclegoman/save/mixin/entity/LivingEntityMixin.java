/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.entity;

import com.mclegoman.save.entity.SaveModEntity;
import com.mclegoman.save.nbt.NbtCompound;
import net.minecraft.entity.living.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements SaveModEntity {
	@Shadow public int health;
	@Shadow public int hurtTime;
	@Shadow public int deathTime;
	@Shadow public int attackTime;
	public String save$id() {
		return "Mob";
	}
	public void save$readCustomNbt(NbtCompound nbtCompound) {
		this.health = nbtCompound.getShort("Health");
		if (!nbtCompound.containsKey("Health")) this.health = 10;
		this.hurtTime = nbtCompound.getShort("HurtTime");
		this.deathTime = nbtCompound.getShort("DeathTime");
		this.attackTime = nbtCompound.getShort("AttackTime");
	}
	public void save$writeCustomNbt(NbtCompound nbtCompound) {
		nbtCompound.putShort("Health", (short)this.health);
		nbtCompound.putShort("HurtTime", (short)this.hurtTime);
		nbtCompound.putShort("DeathTime", (short)this.deathTime);
		nbtCompound.putShort("AttackTime", (short)this.attackTime);
	}
}