package com.mclegoman.save.api.entity;

import com.mclegoman.save.api.nbt.NbtCompound;

public interface SaveModBlockEntity {
	void save$readNbt(NbtCompound nbtCompound);
	void save$writeNbt(NbtCompound nbtCompound);
}
