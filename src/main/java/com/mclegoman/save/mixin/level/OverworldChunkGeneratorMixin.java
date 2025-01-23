/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.level;

import com.mclegoman.save.config.SaveConfig;
import net.minecraft.block.Block;
import net.minecraft.unmapped.C_3376882;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.feature.VeinFeature;
import net.minecraft.world.gen.noise.OctaveNoiseGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(OverworldChunkGenerator.class)
public abstract class OverworldChunkGeneratorMixin {
	@Shadow public Random random;
	@Shadow public World world;
	@Shadow public OctaveNoiseGenerator forestNoise;
	@Shadow public abstract boolean hasChunk(int i, int j);
	@Inject(method = "populateChunk", at = @At("HEAD"), cancellable = true)
	private void save$populateChunk(ChunkSource chunkSource, int i, int j, CallbackInfo ci) {
		if (SaveConfig.instance.disableCaves.value() && this.hasChunk(i, j)) {
			this.random.setSeed((long)i * 318279123L + (long)j * 919871212L);
			int a = i << 4;
			i = j << 4;
			int var4;
			int var5;
			int var6;
			for(j = 0; j < 20; ++j) {
				var4 = a + this.random.nextInt(16);
				var5 = this.random.nextInt(128);
				var6 = i + this.random.nextInt(16);
				(new VeinFeature(Block.COAL_ORE.id)).m_5378046(this.world, this.random, var4, var5, var6);
			}
			for(j = 0; j < 10; ++j) {
				var4 = a + this.random.nextInt(16);
				var5 = this.random.nextInt(64);
				var6 = i + this.random.nextInt(16);
				(new VeinFeature(Block.IRON_ORE.id)).m_5378046(this.world, this.random, var4, var5, var6);
			}
			if (this.random.nextInt(2) == 0) {
				j = a + this.random.nextInt(16);
				var4 = this.random.nextInt(32);
				var5 = i + this.random.nextInt(16);
				(new VeinFeature(Block.GOLD_ORE.id)).m_5378046(this.world, this.random, j, var4, var5);
			}
			if (this.random.nextInt(8) == 0) {
				j = a + this.random.nextInt(16);
				var4 = this.random.nextInt(16);
				var5 = i + this.random.nextInt(16);
				(new VeinFeature(Block.DIAMOND_ORE.id)).m_5378046(this.world, this.random, j, var4, var5);
			}
			j = (int)this.forestNoise.m_1189144((double)a * 0.25, (double)i * 0.25) << 3;
			for (var4 = 0; var4 < j; ++var4) {
				var5 = a + this.random.nextInt(16);
				var6 = i + this.random.nextInt(16);
				new C_3376882();
				World var10000 = this.world;
				Random random = this.random;
				int var8 = this.world.getHeight(var5, var6);
				int var7 = var5 + 2;
				int var9 = var6 + 2;
				int var10 = random.nextInt(3) + 4;
				boolean var11 = true;
				if (var8 > 0 && var8 + var10 + 1 <= 128) {
					int var12;
					int var13;
					int var14;
					int var15;
					for(var12 = var8; var12 <= var8 + 1 + var10; ++var12) {
						var13 = 1;
						if (var12 == var8) {
							var13 = 0;
						}
						if (var12 >= var8 + 1 + var10 - 2) {
							var13 = 2;
						}
						for (var14 = var7 - var13; var14 <= var7 + var13 && var11; ++var14) {
							for (var15 = var9 - var13; var15 <= var9 + var13 && var11; ++var15) {
								if (var12 >= 0 && var12 < 128) {
									if (var10000.m_9893076(var14, var12, var15) != 0) {
										var11 = false;
									}
								} else {
									var11 = false;
								}
							}
						}
					}
					if (((var12 = var10000.m_9893076(var7, var8 - 1, var9)) == Block.GRASS.id || var12 == Block.DIRT.id) && var8 < 128 - var10 - 1) {
						var10000.setBlockQuietly(var7, var8 - 1, var9, Block.DIRT.id);
						for (var13 = var8 - 3 + var10; var13 <= var8 + var10; ++var13) {
							var14 = var13 - (var8 + var10);
							var15 = 1 - var14 / 2;
							for (int var16 = var7 - var15; var16 <= var7 + var15; ++var16) {
								int var21 = var16 - var7;
								for (var12 = var9 - var15; var12 <= var9 + var15; ++var12) {
									int var17 = var12 - var9;
									if ((Math.abs(var21) != var15 || Math.abs(var17) != var15 || random.nextInt(2) != 0 && var14 != 0) && !Block.IS_OPAQUE[var10000.m_9893076(var16, var13, var12)]) {
										var10000.setBlockQuietly(var16, var13, var12, Block.LEAVES.id);
									}
								}
							}
						}
						for (var13 = 0; var13 < var10; ++var13) {
							if (!Block.IS_OPAQUE[var10000.m_9893076(var7, var8 + var13, var9)]) {
								var10000.setBlockQuietly(var7, var8 + var13, var9, Block.LOG.id);
							}
						}
					}
				}
			}
			ci.cancel();
		}
	}
}
