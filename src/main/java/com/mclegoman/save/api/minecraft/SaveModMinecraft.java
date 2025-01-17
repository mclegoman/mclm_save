package com.mclegoman.save.api.minecraft;

import com.mclegoman.save.api.data.Resources;
import com.mclegoman.save.common.util.SaveHelper;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.client.player.input.GameInput;
import net.minecraft.util.math.MathHelper;

public class SaveModMinecraft {
	private static String world;
	public static void save$setWorld(String string) {
		save$setWorld(null, null);
		System.gc();
		save$setWorld(new SaveModWorld(SaveHelper.getSavesDir(), string), string);
	}
	public static void save$setWorld(SaveModWorld world, String name) {
		if (Resources.minecraft.f_5854988 != null) {
			// TODO: Save the world.
			//Resources.minecraft.f_5854988.waitIfSaving();

		}

		Resources.minecraft.f_5854988 = world;
		SaveModMinecraft.world = name;
		if (world != null) {
			Resources.minecraft.f_6058446 = null;
			world.f_6053391 = Resources.minecraft.f_6058446;
			if (Resources.minecraft.f_6058446 == null) {
				Resources.minecraft.f_6058446 = new InputPlayerEntity(Resources.minecraft, world, Resources.minecraft.f_2424468);
				Resources.minecraft.f_6058446.postSpawn();
				if (world != null) {
					world.addEntity(Resources.minecraft.f_6058446);
					world.f_6053391 = Resources.minecraft.f_6058446;
					//world.addPlayer();
				}
			}

			Resources.minecraft.f_6058446.input = new GameInput(Resources.minecraft.f_9967940);
			Resources.minecraft.f_1273243.spawn(Resources.minecraft.f_6058446);
			if (Resources.minecraft.f_4021716 != null) {
				Resources.minecraft.f_4021716.setWorld(world);
			}

			if (Resources.minecraft.f_9322491 != null) {
				Resources.minecraft.f_9322491.setWorld(world);
			}

			save$loadWorld();
		}

		System.gc();
	}
	public static void save$loadWorld() {
		Resources.minecraft.f_7424826.m_0983733("Loading level: " + world);

		for (int a = -256; a <= 256; a += 16) {
			Resources.minecraft.f_7424826.m_1154571((a + 256) * 100 / 512 + "%");
			int x = MathHelper.floor(Resources.minecraft.f_6058446.x);
			int z = MathHelper.floor(Resources.minecraft.f_6058446.z);

			for (int b = -256; b <= 256; b += 16) {
				Resources.minecraft.f_5854988.m_9893076(x + a, 64, z + b);
			}
		}

	}
}
