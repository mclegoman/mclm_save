package com.mclegoman.save.api.entity;

import com.mclegoman.save.api.nbt.NbtCompound;
import com.mclegoman.save.api.nbt.NbtList;

public interface SaveModEntity {
	void save$readEntityNbt(NbtCompound nbtCompound);
	void save$writeEntityNbt(NbtCompound nbtCompound);
	void save$readCustomNbt(NbtCompound nbtCompound);
	void save$writeCustomNbt(NbtCompound nbtCompound);
	NbtList save$toNbtList(double... ds);
	String save$id();
}
