/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.block;

import com.mclegoman.save.block.SaveModBlockWithBlockEntity;
import com.mclegoman.save.entity.SaveModBlockEntity;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChestBlock.class)
public abstract class ChestBlockMixin implements SaveModBlockWithBlockEntity {
	public BlockEntity save$createBlockEntity(int x, int y, int z) {
		SaveModBlockEntity blockEntity = ((SaveModBlockEntity)new ChestBlockEntity());
		blockEntity.save$setX(x);
		blockEntity.save$setY(y);
		blockEntity.save$setZ(z);
		return (BlockEntity) blockEntity;
	}
}