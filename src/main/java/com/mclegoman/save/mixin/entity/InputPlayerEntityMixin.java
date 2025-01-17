/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.entity;

import com.mclegoman.save.api.entity.SaveModEntity;
import com.mclegoman.save.api.nbt.NbtCompound;
import com.mclegoman.save.api.nbt.NbtList;
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
			ItemStack stack = new ItemStack(item.getShort("id"), item.getByte("Count"));
			stack.metadata = item.getShort("Damage");
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
		for (int i = 0; i < this.inventory.inventorySlots.length; i++) {
			NbtCompound slot = new NbtCompound();
			slot.putByte("Count", (byte) this.inventory.inventorySlots[i].size);
			slot.putShort("Damage", (byte) this.inventory.inventorySlots[i].metadata);
			slot.putShort("id", (byte) this.inventory.inventorySlots[i].itemId);
			slot.putByte("Slot", (byte) i);
			inventory.add(slot);
		}
		for (int i = 0; i < this.inventory.armorSlots.length; i++) {
			NbtCompound slot = new NbtCompound();
			slot.putByte("Count", (byte) this.inventory.armorSlots[i].size);
			slot.putShort("Damage", (byte) this.inventory.armorSlots[i].metadata);
			slot.putShort("id", (byte) this.inventory.armorSlots[i].itemId);
			slot.putByte("Slot", (byte) (i + 100));
			inventory.add(slot);
		}
		nbtCompound.put("Inventory", inventory);
	}
	public String save$id() {
		return "LocalPlayer";
	}
}
