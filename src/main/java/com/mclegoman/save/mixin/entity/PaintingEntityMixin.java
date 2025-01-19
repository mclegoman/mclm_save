/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.entity;

import com.mclegoman.save.entity.SaveModEntity;
import com.mclegoman.save.nbt.NbtCompound;
import net.minecraft.entity.decoration.PaintingEntity;
import net.minecraft.entity.decoration.PaintingEntity__Motive;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PaintingEntity.class)
public abstract class PaintingEntityMixin implements SaveModEntity {
	@Shadow public int dir;
	@Shadow public PaintingEntity__Motive motive;
	@Shadow private int xPos;
	@Shadow private int yPos;
	@Shadow private int zPos;
	@Shadow protected abstract void setDirection(int i);
	public String save$id() {
		return "Painting";
	}
	public final void save$writeCustomNbt(NbtCompound nbtCompound) {
		nbtCompound.putByte("Dir", (byte)this.dir);
		nbtCompound.putString("Motive", this.motive.name());
		nbtCompound.putInt("TileX", this.xPos);
		nbtCompound.putInt("TileY", this.yPos);
		nbtCompound.putInt("TileZ", this.zPos);
	}
	public final void save$readCustomNbt(NbtCompound nbtCompound) {
		this.dir = nbtCompound.getByte("Dir");
		this.xPos = nbtCompound.getInt("TileX");
		this.yPos = nbtCompound.getInt("TileY");
		this.zPos = nbtCompound.getInt("TileZ");
		this.motive = PaintingEntity__Motive.valueOf(nbtCompound.getString("Motive"));
		this.setDirection(this.dir);
	}
}
