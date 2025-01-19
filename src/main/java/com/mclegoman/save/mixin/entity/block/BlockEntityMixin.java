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
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements SaveModBlockEntity {
	@Shadow public int x;
	@Shadow public int y;
	@Shadow public int z;
	public void save$readNbt(NbtCompound nbtCompound) {
		this.x = nbtCompound.getInt("x");
		this.y = nbtCompound.getInt("y");
		this.z = nbtCompound.getInt("z");
	}
	public void save$writeNbt(NbtCompound nbtCompound) {
		nbtCompound.putString("id", SaveHelper.getBlockEntityIdFromType(this.getClass()));
		nbtCompound.putInt("x", this.x);
		nbtCompound.putInt("y", this.y);
		nbtCompound.putInt("z", this.z);
	}
}
