/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.entity.block;

import com.mclegoman.save.entity.SaveModBlockEntity;
import com.mclegoman.save.nbt.NbtCompound;
import com.mclegoman.save.util.SaveHelper;
import net.minecraft.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements SaveModBlockEntity {
	public int save$getX() {
		return this.save$x;
	}
	public int save$getY() {
		return this.save$y;
	}
	public int save$getZ() {
		return this.save$z;
	}
	public void save$setX(int x) {
		this.save$x = x;
	}
	public void save$setY(int y) {
		this.save$y = y;
	}
	public void save$setZ(int z) {
		this.save$z = z;
	}
	@Unique public int save$x;
	@Unique public int save$y;
	@Unique public int save$z;
	public void save$readNbt(NbtCompound nbtCompound) {
		save$setX(nbtCompound.getInt("x"));
		save$setY(nbtCompound.getInt("y"));
		save$setZ(nbtCompound.getInt("z"));
	}
	public void save$writeNbt(NbtCompound nbtCompound) {
		nbtCompound.putString("id", SaveHelper.getBlockEntityIdFromType(this.getClass()));
		nbtCompound.putInt("x", save$getX());
		nbtCompound.putInt("y", save$getY());
		nbtCompound.putInt("z", save$getZ());
	}
}
