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
import net.minecraft.entity.living.mob.passive.animal.SheepEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends LivingEntity implements SaveModEntity {
	@Shadow public boolean f_4976759;
	public SheepEntityMixin(World world) {
		super(world);
	}
	public String save$id() {
		return "Sheep";
	}
	public final void save$writeCustomNbt(NbtCompound nbtCompound) {
		this.health = nbtCompound.getShort("Health");
		if (!nbtCompound.containsKey("Health")) this.health = 10;
		this.hurtTime = nbtCompound.getShort("HurtTime");
		this.deathTime = nbtCompound.getShort("DeathTime");
		this.attackTime = nbtCompound.getShort("AttackTime");
		nbtCompound.putBoolean("Sheared", this.f_4976759);
	}

	public final void save$readCustomNbt(NbtCompound nbtCompound) {
		nbtCompound.putShort("Health", (short)this.health);
		nbtCompound.putShort("HurtTime", (short)this.hurtTime);
		nbtCompound.putShort("DeathTime", (short)this.deathTime);
		nbtCompound.putShort("AttackTime", (short)this.attackTime);
		this.f_4976759 = nbtCompound.getBoolean("Sheared");
	}
}
