/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.entity;

import com.mclegoman.save.entity.SaveModEntity;
import com.mclegoman.save.level.SaveModWorld;
import com.mclegoman.save.nbt.NbtCompound;
import com.mclegoman.save.nbt.NbtList;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(InputPlayerEntity.class)
public abstract class InputPlayerEntityMixin extends PlayerEntity implements SaveModEntity {
	public InputPlayerEntityMixin(World world) {
		super(world);
	}
	public void save$readCustomNbt(NbtCompound nbtCompound) {
		this.health = nbtCompound.getShort("Health");
		if (!nbtCompound.containsKey("Health")) this.health = 10;
		this.hurtTime = nbtCompound.getShort("HurtTime");
		this.deathTime = nbtCompound.getShort("DeathTime");
		this.attackTime = nbtCompound.getShort("AttackTime");
		this.playerScore = nbtCompound.getInt("Score");
		NbtList inventoryNbt = nbtCompound.getList("Inventory");
		this.inventory.inventorySlots = new ItemStack[36];
		this.inventory.armorSlots = new ItemStack[4];
		for (int index = 0; index < inventoryNbt.size(); ++index) {
			NbtCompound item = (NbtCompound) inventoryNbt.get(index);
			ItemStack stack = SaveModWorld.readItem((NbtCompound) inventoryNbt.get(index));
			int slot = item.getByte("Slot");
			if ((slot & 255) >= 100) this.inventory.armorSlots[slot - 100] = stack;
			else this.inventory.inventorySlots[slot] = stack;
		}
	}
	public void save$writeCustomNbt(NbtCompound nbtCompound) {
		nbtCompound.putShort("Health", (short)this.health);
		nbtCompound.putShort("HurtTime", (short)this.hurtTime);
		nbtCompound.putShort("DeathTime", (short)this.deathTime);
		nbtCompound.putShort("AttackTime", (short)this.attackTime);

		nbtCompound.putInt("Score", this.playerScore);
		NbtList inventory = new NbtList();
		for (int index = 0; index < this.inventory.inventorySlots.length; ++index) {
			if (this.inventory.inventorySlots[index] != null) {
				NbtCompound item = new NbtCompound();
				item.putByte("Slot", (byte)index);
				SaveModWorld.saveItem(this.inventory.inventorySlots[index], item);
				inventory.add(item);
			}
		}
		for (int index = 0; index < this.inventory.armorSlots.length; ++index) {
			if (this.inventory.armorSlots[index] != null) {
				NbtCompound item = new NbtCompound();
				item.putByte("Slot", (byte)(index + 100));
				SaveModWorld.saveItem(this.inventory.armorSlots[index], item);
				inventory.add(item);
			}
		}
		nbtCompound.put("Inventory", inventory);
	}
	public String save$id() {
		return "LocalPlayer";
	}
}
