/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.entity;

import com.mclegoman.save.nbt.NbtCompound;
import com.mclegoman.save.nbt.NbtList;

public interface SaveModEntity {
	void save$readEntityNbt(NbtCompound nbtCompound);
	void save$writeEntityNbt(NbtCompound nbtCompound);
	void save$readCustomNbt(NbtCompound nbtCompound);
	void save$writeCustomNbt(NbtCompound nbtCompound);
	NbtList save$toNbtList(double... ds);
	String save$id();
}
