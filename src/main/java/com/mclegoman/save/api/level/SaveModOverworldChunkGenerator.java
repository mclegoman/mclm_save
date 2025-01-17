package com.mclegoman.save.api.level;

import net.minecraft.world.World;
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
}
