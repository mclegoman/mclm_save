/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.fun;

import com.mclegoman.save.api.event.render.RenderEvents;
import com.mclegoman.save.api.event.tick.TickEvents;
import com.mclegoman.save.config.SaveConfig;
import com.mclegoman.save.data.Data;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.render.Window;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class BossMode {
	private static boolean enabled;
	public static void init() {
		TickEvents.register(TickEvents.Tick.START, Data.getVersion().getID() + "_boss_mode", (client) -> {
			if (SaveConfig.instance.allowBossMode.value()) {
				if (Keyboard.getEventKeyState()) enabled = (Keyboard.getEventKey() == 48);
				else enabled = false;
			} else enabled = false;
		});
		RenderEvents.register(RenderEvents.Render.END, Data.getVersion().getID() + "_boss_mode", (client) -> {
			if (enabled) {
				Window window = new Window(client.f_0545414, client.f_5990000);
				int width = window.m_2112110();
				int height = window.m_3634999();
				GL11.glBindTexture(3553, client.f_9413506.load("/assets/save/boss_mode.png"));
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				BufferBuilder builder = BufferBuilder.INSTANCE;
				builder.start();
				builder.vertex(0, height, 0, 0.0F, 1.0F);
				builder.vertex(width, height, 0, 1.0F, 1.0F);
				builder.vertex(width, 0, 0, 1.0F, 0.0F);
				builder.vertex(0, 0, 0, 0.0F, 0.0F);
				builder.end();
			}
		});
	}
}
