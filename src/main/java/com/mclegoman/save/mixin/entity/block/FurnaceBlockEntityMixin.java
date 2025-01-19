/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.entity.block;

import com.mclegoman.save.entity.SaveModBlockEntity;
import com.mclegoman.save.level.SaveModWorld;
import com.mclegoman.save.nbt.NbtCompound;
import com.mclegoman.save.nbt.NbtList;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FurnaceBlockEntity.class)
public abstract class FurnaceBlockEntityMixin extends BlockEntity implements SaveModBlockEntity {
	@Shadow private int fuelTime;
	@Shadow private int cookTime;
	@Shadow private ItemStack[] inventory;
	@Shadow private int totalFuelTime;
	public void save$readNbt(NbtCompound nbtCompound) {
		this.x = nbtCompound.getInt("x");
		this.y = nbtCompound.getInt("y");
		this.z = nbtCompound.getInt("z");
		NbtList inv = nbtCompound.getList("Items");
		this.inventory = new ItemStack[this.inventory.length];
		for (int index = 0; index < inv.size(); ++index)
			this.inventory[((NbtCompound) inv.get(index)).getByte("Slot")] = SaveModWorld.readItem((NbtCompound) inv.get(index));
		this.fuelTime = nbtCompound.getShort("BurnTime");
		this.cookTime = nbtCompound.getShort("CookTime");
		this.totalFuelTime = save$getFuelTime(this.inventory[1]);
	}
	public void save$writeNbt(NbtCompound nbtCompound) {
		nbtCompound.putString("id", "Furnace");
		nbtCompound.putInt("x", this.x);
		nbtCompound.putInt("y", this.y);
		nbtCompound.putInt("z", this.z);
		nbtCompound.putShort("BurnTime", (short)this.fuelTime);
		nbtCompound.putShort("CookTime", (short)this.cookTime);
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
	@Unique
	private static int save$getFuelTime(ItemStack itemStack) {
		if (itemStack == null) {
			return 0;
		} else {
			int itemId = itemStack.getItem().id;
			if (Block.BY_ID[itemId].material == Material.WOOD) return 300;
			else if (itemId == Item.STICK.id) return 100;
			else return itemId == Item.COAL.id ? 1600 : 0;
		}
	}
}
