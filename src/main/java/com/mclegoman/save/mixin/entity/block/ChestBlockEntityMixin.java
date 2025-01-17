/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.entity.block;

import com.mclegoman.save.api.entity.SaveModBlockEntity;
import com.mclegoman.save.api.level.SaveModWorld;
import com.mclegoman.save.api.nbt.NbtCompound;
import com.mclegoman.save.api.nbt.NbtList;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityMixin extends BlockEntity implements SaveModBlockEntity {
	@Shadow private ItemStack[] inventory;

	public void save$readNbt(NbtCompound nbtCompound) {
		this.x = nbtCompound.getInt("x");
		this.y = nbtCompound.getInt("y");
		this.z = nbtCompound.getInt("z");

		NbtList inv = nbtCompound.getList("Items");
		this.inventory = new ItemStack[27];
		for (int index = 0; index < inv.size(); ++index)
			this.inventory[((NbtCompound) inv.get(index)).getByte("Slot")] = SaveModWorld.readItem((NbtCompound) inv.get(index));
	}
	public void save$writeNbt(NbtCompound nbtCompound) {
		nbtCompound.putString("id", "Chest");
		nbtCompound.putInt("x", this.x);
		nbtCompound.putInt("y", this.y);
		nbtCompound.putInt("z", this.z);
		NbtList inv = new NbtList();
		for (int index = 0; index < this.inventory.length; ++index) {
			if (this.inventory[index] != null) {
				NbtCompound item = new NbtCompound();
				item.putByte("Slot", (byte)index);
				SaveModWorld.saveItem(this.inventory[index], item);
				inv.add(item);
			}
		}
		nbtCompound.put("Items", inv);
	}
}
