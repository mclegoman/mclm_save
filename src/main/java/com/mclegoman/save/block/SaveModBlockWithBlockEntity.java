/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.block;

import net.minecraft.block.entity.BlockEntity;

public interface SaveModBlockWithBlockEntity {
	BlockEntity save$createBlockEntity(int x, int y, int z);
}
