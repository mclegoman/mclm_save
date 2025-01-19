/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.entity;

import com.mclegoman.save.nbt.NbtCompound;

public interface SaveModBlockEntity {
	void save$readNbt(NbtCompound nbtCompound);
	void save$writeNbt(NbtCompound nbtCompound);
}
