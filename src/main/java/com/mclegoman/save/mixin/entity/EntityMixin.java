/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.entity;

import com.mclegoman.save.entity.SaveModEntity;
import com.mclegoman.save.nbt.NbtCompound;
import com.mclegoman.save.nbt.NbtDouble;
import com.mclegoman.save.nbt.NbtFloat;
import com.mclegoman.save.nbt.NbtList;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin implements SaveModEntity {
	@Shadow public double x;
	@Shadow public double y;
	@Shadow public double z;
	@Shadow public double velocityX;
	@Shadow public double velocityY;
	@Shadow public double velocityZ;
	@Shadow public float yaw;
	@Shadow public float pitch;
	@Shadow private float fallDistance;
	@Shadow public int onFireTimer;
	@Shadow public int f_1297340;
	@Shadow public abstract void refreshPositionAndAngles(double d, double e, double f, float g, float h);
	@Shadow public boolean removed;
	public void save$readEntityNbt(NbtCompound nbtCompound) {
		NbtList var2 = nbtCompound.getList("Pos");
		NbtList var3 = nbtCompound.getList("Motion");
		NbtList var4 = nbtCompound.getList("Rotation");
		this.x = ((NbtDouble)var2.get(0)).value;
		this.y = ((NbtDouble)var2.get(1)).value;
		this.z = ((NbtDouble)var2.get(2)).value;
		this.velocityX = ((NbtDouble)var3.get(0)).value;
		this.velocityY = ((NbtDouble)var3.get(1)).value;
		this.velocityZ = ((NbtDouble)var3.get(2)).value;
		this.yaw = ((NbtFloat)var4.get(0)).value;
		this.pitch = ((NbtFloat)var4.get(1)).value;
		this.fallDistance = nbtCompound.m_3941822("FallDistance");
		this.onFireTimer = nbtCompound.getShort("Fire");
		this.f_1297340 = nbtCompound.getShort("Air");
		this.refreshPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
		this.save$readCustomNbt(nbtCompound);
	}
	public void save$writeEntityNbt(NbtCompound nbtCompound) {
		String var2 = this.save$id();
		if (!this.removed && var2 != null) {
			nbtCompound.putString("id", var2);
			nbtCompound.put("Pos", save$toNbtList(this.x, this.y, this.z));
			nbtCompound.put("Motion", save$toNbtList(this.velocityX, this.velocityY, this.velocityZ));
			NbtList rotation = new NbtList();
			rotation.add(new NbtFloat(this.pitch));
			rotation.add(new NbtFloat(this.yaw));

			nbtCompound.put("Rotation", rotation);
			nbtCompound.putFloat("FallDistance", this.fallDistance);
			nbtCompound.putShort("Fire", (short)this.onFireTimer);
			nbtCompound.putShort("Air", (short)this.f_1297340);
			this.save$writeCustomNbt(nbtCompound);
		}
	}
	public abstract void save$readCustomNbt(NbtCompound nbtCompound);
	public abstract void save$writeCustomNbt(NbtCompound nbtCompound);
	public NbtList save$toNbtList(double... ds) {
		NbtList nbtList = new NbtList();
		for (double value : ds) nbtList.add(new NbtDouble(value));
		return nbtList;
	}
	public abstract String save$id();
}
