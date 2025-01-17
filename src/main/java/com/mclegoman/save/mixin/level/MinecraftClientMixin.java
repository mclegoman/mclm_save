/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.level;

import com.mclegoman.save.api.gui.screen.InfoScreen;
import com.mclegoman.save.api.level.SaveModMinecraft;
import com.mclegoman.save.api.level.SaveModWorld;
import com.mclegoman.save.client.gui.screen.SaveInfoScreen;
import com.mclegoman.save.common.util.SaveHelper;
import net.minecraft.client.*;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.interaction.ClientPlayerInteractionManager;
import net.minecraft.client.player.input.GameInput;
import net.minecraft.client.render.world.WorldRenderer;
import net.minecraft.entity.particle.ParticleManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@ClientOnly
@Mixin(C_5664496.class)
public abstract class MinecraftClientMixin implements SaveModMinecraft {
	@Shadow public World f_5854988;
	@Shadow public abstract void m_6408915(Screen screen);
	@Shadow public InputPlayerEntity f_6058446;
	@Shadow public ClientPlayerInteractionManager f_1273243;
	@Shadow public WorldRenderer f_4021716;
	@Shadow public ParticleManager f_9322491;
	@Shadow public C_0877775 f_7424826;
	@Shadow public C_1331819 f_9967940;
	@Shadow public C_8730536 f_2424468;
	public C_5664496 save$getInstance() {
		return (C_5664496) (Object) this;
	}
	public void save$set(String string) {
		save$exit();
		save$set(new SaveModWorld(SaveHelper.getSavesDir(), string));
	}
	public void save$exit() {
		save$exit(null);
	}
	public void save$exit(Screen screen) {
		if (this.f_5854988 != null) save$save();
		this.f_5854988 = null;
		System.gc();
		this.m_6408915(screen);
	}
	public void save$save() {
		// TODO: Save the world.
		//this.f_5854988.waitIfSaving();
	}
	public void save$set(SaveModWorld world) {
		try {
			if (this.f_5854988 != null) save$save();
			this.f_5854988 = world;
			if (world != null) {
				this.f_6058446 = new InputPlayerEntity(save$getInstance(), world, this.f_2424468);
				this.f_6058446.postSpawn();
				if (world != null) {
					world.addEntity(this.f_6058446);
					world.f_6053391 = this.f_6058446;
					world.addPlayer();
				}
				this.f_6058446.input = new GameInput(this.f_9967940);
				this.f_1273243.spawn(this.f_6058446);
				if (this.f_4021716 != null) this.f_4021716.setWorld(world);
				if (this.f_9322491 != null) this.f_9322491.setWorld(world);
				save$load();
			}
			System.gc();
		} catch (Exception error) {
			save$exit(new SaveInfoScreen(null, "404", error.getLocalizedMessage(), InfoScreen.Type.ERROR, true));
		}
	}
	public void save$load() {
		this.f_7424826.m_0983733("Loading level");
		this.f_7424826.m_1154571("Loading chunks");
		for (int chunk = -256; chunk <= 256; chunk += 16) {
			int x = MathHelper.floor(this.f_6058446.x);
			int z = MathHelper.floor(this.f_6058446.z);
			for (int b = -256; b <= 256; b += 16) this.f_5854988.m_9893076(x + chunk, 64, z + b);
		}
	}
}
