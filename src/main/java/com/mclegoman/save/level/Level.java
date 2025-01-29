/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.level;

import com.mclegoman.save.data.Data;
import com.mclegoman.save.nbt.*;
import com.mclegoman.save.rtu.util.Couple;
import com.mclegoman.save.rtu.util.LogType;

import java.io.*;
import java.time.Instant;
import java.util.zip.GZIPOutputStream;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.unmapped.C_2411117;
import net.minecraft.world.World;

public abstract class Level {
	private final C_2411117 f_1154694;

	public Level(C_2411117 c_2411117) {
		this.f_1154694 = c_2411117;
	}

	public final World load(InputStream inputStream) throws IOException {
		if (this.f_1154694 != null) {
			this.f_1154694.m_0983733("Loading level");
		}

		if (this.f_1154694 != null) {
			this.f_1154694.m_1154571("Reading..");
		}

		NbtCompound inputStream1 = NbtCompoundStream.toNbtCompound(inputStream);
		NbtCompound map = inputStream1.getNbt("Map");
		NbtCompound environment = inputStream1.getNbt("Environment");
		NbtList entities = inputStream1.getList("Entities");
		int width = map.getShort("Width");
		short length = map.getShort("Length");
		short height = map.getShort("Height");
		World world = new World();
		if (this.f_1154694 != null) {
			this.f_1154694.m_1154571("Preparing level..");
		}

		NbtList var10 = map.getList("Spawn");
		world.f_3926541 = ((NbtShort)var10.getNbt(0)).tag;
		world.f_2923303 = ((NbtShort)var10.getNbt(1)).tag;
		world.f_8500813 = ((NbtShort)var10.getNbt(2)).tag;
		world.f_1709243 = environment.getInt("CloudColor");
		world.f_3766825 = environment.getInt("SkyColor");
		world.f_2946178 = environment.getInt("FogColor");
		world.f_6732352 = (float)environment.getByte("SkyBrightness") / 100.0F;
		world.f_4971921 = environment.getShort("CloudHeight");
		world.f_0183464 = environment.getShort("SurroundingGroundHeight");
		world.f_8873427 = environment.getShort("SurroundingWaterHeight");
		world.f_3241378 = environment.getByte("SurroundingWaterType");
		byte[] blocks = map.m_5601145("Blocks");
		world.m_2817546(width, height, length, blocks);
		if (this.f_1154694 != null) {
			this.f_1154694.m_1154571("Preparing entities..");
		}
		for(int var13 = 0; var13 < entities.index(); ++var13) {
			String var16 = (map = (NbtCompound)entities.getNbt(var13)).getString("id");
			Entity var19;
			if ((var19 = this.getEntity(world, var16)) != null) {
				this.loadEntityData(map, var19);
			}
		}

		return world;
	}

	protected Entity getEntity(World world, String string) {
		return null;
	}

	public final void save(World world, File file) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(file);
		NbtCompound level = LevelFile.createLevel("Player", Instant.now().getEpochSecond(), "A Nice World", world.f_1709243, (short) world.f_4971921, world.f_2946178, (byte) (world.f_6732352 * 100.0F), world.f_3766825, (short) world.f_0183464, (byte) Block.GRASS.id, (short) world.f_8873427, (byte) world.f_3241378, (short) world.f_3926541, (short) world.f_2923303, (short) world.f_8500813, (short) world.f_4184003, (short) world.f_8212213, (short) world.f_3061106, world.f_4249554);
		NbtList entities = new NbtList();
		for (Object entity : world.f_7148360.f_6899876) {
			Entity currentEntity = (Entity)entity;
			currentEntity.m_2914294(currentEntity);
			NbtCompound entityData = saveEntityData(currentEntity);
			if (!entityData.isEmpty()) {
				if (entity instanceof LivingEntity) {
					if ((((LivingEntity) entity).health > 0) || ((LivingEntity) entity).deathTime <= 0) entities.addNbt(entityData);
				} else entities.addNbt(entityData);
			}
		}
		level.addNbt("Entities", entities);
		try {
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
			NbtElement.serialize(level, new DataOutputStream(gzipOutputStream));
			gzipOutputStream.close();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.WARN, error.getLocalizedMessage());
		} finally {
			outputStream.close();
		}
	}
	public final void loadEntityData(NbtCompound nbtCompound, Entity entity) {
		NbtList var2 = nbtCompound.getList("Pos");
		NbtList var3 = nbtCompound.getList("Motion");
		NbtList var4 = nbtCompound.getList("Rotation");
		entity.x = ((NbtFloat)var2.getNbt(0)).tag;
		entity.y = ((NbtFloat)var2.getNbt(1)).tag;
		entity.z = ((NbtFloat)var2.getNbt(2)).tag;
		entity.velocityX = ((NbtFloat)var3.getNbt(0)).tag;
		entity.velocityY = ((NbtFloat)var3.getNbt(1)).tag;
		entity.velocityZ = ((NbtFloat)var3.getNbt(2)).tag;
		entity.yaw = ((NbtFloat)var4.getNbt(0)).tag;
		entity.pitch = ((NbtFloat)var4.getNbt(1)).tag;
		entity.fallDistance = nbtCompound.m_0000382("FallDistance");
		entity.onFireTimer = nbtCompound.getShort("Fire");
		if (entity instanceof LivingEntity) ((LivingEntity) entity).f_0554371 = nbtCompound.getInt("Air");
		entity.refreshPositionAndAngles(entity.x, entity.y, entity.z, entity.yaw, entity.pitch);
		if (entity instanceof PlayerEntity) {
			NbtList inventory = nbtCompound.getList("Inventory");
			PlayerInventory playerInventory = new PlayerInventory();
			for(int item = 0; item < inventory.index(); ++item) {
				NbtCompound index = (NbtCompound)inventory.getNbt(item);
				int count = index.getByte("Count");
				int slot = index.getByte("Slot");
				// We check for `itemId` as an older version of mclm_save accidentally saved items as `itemId`.
				// We check for `blockId` without the config option as it's only needed on save.
				Couple[] idTypes = new Couple[]{new Couple("id", false), new Couple("itemId", false), new Couple("blockId", true)};
				for (Couple type : idTypes) prepStack((String)type.getFirst(), playerInventory, count, slot, index, (boolean)type.getSecond());
			}
			((PlayerEntity)entity).inventory = playerInventory;
			((PlayerEntity)entity).playerScore = nbtCompound.getInt("Score");
		}
	}
	public final NbtCompound saveEntityData(Entity entity) {
		NbtCompound nbtCompound = new NbtCompound();
		NbtList motion = new NbtList();
		motion.addNbt(new NbtFloat(entity.velocityX));
		motion.addNbt(new NbtFloat(entity.velocityY));
		motion.addNbt(new NbtFloat(entity.velocityZ));
		nbtCompound.addNbt("Motion", motion);
		NbtList pos = new NbtList();
		pos.addNbt(new NbtFloat(entity.x));
		pos.addNbt(new NbtFloat(entity.y));
		pos.addNbt(new NbtFloat(entity.z));
		nbtCompound.addNbt("Pos", pos);
		NbtList rotation = new NbtList();
		rotation.addNbt(new NbtFloat(entity.yaw));
		rotation.addNbt(new NbtFloat(entity.pitch));
		nbtCompound.addNbt("Rotation", rotation);
		nbtCompound.addNbt("FallDistance", new NbtFloat(entity.fallDistance));
		nbtCompound.addNbt("Fire", new NbtShort((short) entity.onFireTimer));
		if (entity instanceof PlayerEntity) {
			nbtCompound.addNbt("id", new NbtString("LocalPlayer"));
			NbtList inventory = new NbtList();
			for(int invSlot = 0; invSlot < ((PlayerEntity)entity).inventory.inventorySlots.length; ++invSlot) {
				NbtCompound inventorySlot = new NbtCompound();
				ItemStack stack = ((PlayerEntity)entity).inventory.inventorySlots[invSlot];
				if (stack != null && Item.BY_ID[stack.itemId] != null) {
					inventorySlot.addNbt("Count", new NbtByte((byte) stack.size));
					inventorySlot.addNbt("id", new NbtShort((short) stack.itemId));
					inventorySlot.addNbt("Slot", new NbtByte((byte) invSlot));
					inventory.addNbt(inventorySlot);
				}
			}
			nbtCompound.addNbt("Inventory", inventory);
			nbtCompound.addNbt("Score", new NbtInt(((PlayerEntity)entity).playerScore));
		} else if (entity instanceof LivingEntity) {
			nbtCompound.addNbt("id", new NbtString("Mob"));
			nbtCompound.addNbt("Air", new NbtInt(((LivingEntity)entity).f_0554371));
		} else if (entity instanceof ItemEntity) {
			nbtCompound.addNbt("id", new NbtString("Item"));
		}
		return nbtCompound;
	}
	public final void setStack(ItemStack[] inventorySlots, int slot, ItemStack stack) {
		if (stack != null && stack.itemId != -1) inventorySlots[slot] = stack;
	}
	public final void prepStack(String type, PlayerInventory playerInventory, int count, int slot, NbtCompound index) {
		if (index.getElements().containsKey(type)) {
			int id = index.getShort(type);
			if (id != -1 && Item.BY_ID[id] != null) setStack(playerInventory.inventorySlots, slot, new ItemStack(id, count));
		}
	}
	// We keep a version with isBlock variable for backwards compatibility with other mods.
	public final void prepStack(String type, PlayerInventory playerInventory, int count, int slot, NbtCompound index, boolean isBlock) {
		prepStack(type, playerInventory, count, slot, index);
	}
}
