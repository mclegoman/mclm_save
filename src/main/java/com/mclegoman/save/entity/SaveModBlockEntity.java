/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.entity;

import com.mclegoman.save.nbt.NbtCompound;

public interface SaveModBlockEntity {
	int save$getX();
	int save$getY();
	int save$getZ();
	void save$setX(int x);
	void save$setY(int y);
	void save$setZ(int z);
	void save$readNbt(NbtCompound nbtCompound);
	void save$writeNbt(NbtCompound nbtCompound);
}
