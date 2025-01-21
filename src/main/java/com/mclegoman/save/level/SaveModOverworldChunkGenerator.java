/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.level;

import com.mclegoman.save.config.SaveConfig;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.noise.OctaveNoiseGenerator;

import java.util.Random;

public class SaveModOverworldChunkGenerator extends OverworldChunkGenerator {
	public SaveModOverworldChunkGenerator(World world, long seed) {
		super(world);
		this.random = new Random(seed);
		this.f_3952515 = new Random(seed);
		this.minLimitPerlinNoise = new OctaveNoiseGenerator(this.random, 16);
		this.maxLimitPerlinNoise = new OctaveNoiseGenerator(this.random, 16);
		this.mainPerlinNoise = new OctaveNoiseGenerator(this.random, 8);
		this.f_4682337 = new OctaveNoiseGenerator(this.random, 4);
		this.f_8421429 = new OctaveNoiseGenerator(this.random, 4);
		this.f_1600301 = new OctaveNoiseGenerator(this.random, 5);
		this.forestNoise = new OctaveNoiseGenerator(this.random, 5);
	}
	public WorldChunk getChunk(int i, int j) {
		if (!SaveConfig.instance.fixFlowerGen.value()) return super.getChunk(i, j);
		else {
			this.random.setSeed((long)i * 341873128712L + (long)j * 132897987541L);
			byte[] var3 = new byte['耀'];
			WorldChunk var4 = new WorldChunk(this.world, var3, i, j);
			i <<= 4;
			j <<= 4;
			int var5 = 0;

			for(int var6 = i; var6 < i + 16; ++var6) {
				for(int var7 = j; var7 < j + 16; ++var7) {
					int var8 = var6 / 1024;
					int var9 = var7 / 1024;
					float var10 = (float)(this.minLimitPerlinNoise.m_1502652((float)var6 / 0.03125F, 0.0, (float)var7 / 0.03125F) - this.maxLimitPerlinNoise.m_1502652((float)var6 / 0.015625F, 0.0, (float)var7 / 0.015625F)) / 512.0F / 4.0F;
					float var11 = (float)this.f_8421429.m_1189144((float)var6 / 4.0F, (float)var7 / 4.0F);
					float var12 = (float)this.f_1600301.m_1189144((float)var6 / 8.0F, (float)var7 / 8.0F) / 8.0F;
					var11 = var11 > 0.0F ? (float)(this.mainPerlinNoise.m_1189144((float)var6 * 0.25714284F * 2.0F, (float)var7 * 0.25714284F * 2.0F) * (double)var12 / 4.0) : (float)(this.f_4682337.m_1189144((float)var6 * 0.25714284F, (float)var7 * 0.25714284F) * (double)var12);
					int var15 = (int)(var10 + 64.0F + var11);
					if ((float)this.f_8421429.m_1189144(var6, var7) < 0.0F) {
						var15 = var15 / 2 << 1;
						if ((float)this.f_8421429.m_1189144(var6 / 5, var7 / 5) < 0.0F) {
							++var15;
						}
					}

					for (int var16 = 0; var16 < 128; ++var16) {
						int var17 = 0;
						if (var16 == var15 + 1 && var15 >= 64 && Math.random() < 0.02) {
							// We limit the generation of flower to be on a block it can survive on.
							int blockId = var3[var8 << 11 | var9 << 7 | (var16 - 1)];
							if (blockId == Block.GRASS.id || blockId == Block.DIRT.id || blockId == Block.FARMLAND.id) var17 = Block.YELLOW_FLOWER.id;
						} else if (var16 == var15 && var15 >= 64) {
							var17 = Block.GRASS.id;
						} else if (var16 <= var15 - 2) {
							var17 = Block.STONE.id;
						} else if (var16 <= var15) {
							var17 = Block.DIRT.id;
						} else if (var16 <= 64) {
							var17 = Block.WATER.id;
						}

						this.f_3952515.setSeed(var8 + var9 * 13871L);
						int var13 = (var8 << 10) + 128 + this.f_3952515.nextInt(512);
						int var14 = (var9 << 10) + 128 + this.f_3952515.nextInt(512);
						var13 = var6 - var13;
						var14 = var7 - var14;
						if (var13 < 0) {
							var13 = -var13;
						}

						if (var14 < 0) {
							var14 = -var14;
						}

						if (var14 > var13) {
							var13 = var14;
						}

						if ((var13 = 127 - var13) == 255) {
							var13 = 1;
						}

						if (var13 < var15) {
							var13 = var15;
						}

						if (var16 <= var13 && (var17 == 0 || var17 == Block.WATER.id)) {
							var17 = Block.BRICKS.id;
						}

						if (var17 < 0) {
							var17 = 0;
						}

						var3[var5++] = (byte)var17;
					}
				}
			}

			var4.populateSkylight();
			return var4;
		}
	}
}
