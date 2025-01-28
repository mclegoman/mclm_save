/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.gui;

import com.mclegoman.save.fun.BossMode;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.C_5664496;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Window;
import org.lwjgl.opengl.GL11;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	@Shadow private C_5664496 minecraft;
	@Inject(method = "m_8576613", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;yield()V"))
	private void save$bossMode(float f, CallbackInfo ci) {
		if (BossMode.enabled) {
			Window window = new Window(this.minecraft.f_0545414, this.minecraft.f_5990000);
			int width = window.m_2112110();
			int height = window.m_3634999();
			GL11.glBindTexture(3553, this.minecraft.f_9413506.load("/assets/save/boss_mode.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			BufferBuilder builder = BufferBuilder.INSTANCE;
			builder.start();
			builder.vertex(0, height, 0, 0.0F, 1.0F);
			builder.vertex(width, height, 0, 1.0F, 1.0F);
			builder.vertex(width, 0, 0, 1.0F, 0.0F);
			builder.vertex(0, 0, 0, 0.0F, 0.0F);
			builder.end();
		}
	}
}
