/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.gui;

import com.mclegoman.save.data.Data;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.C_4434943;
import net.minecraft.client.render.Window;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class InfoOverlay {
	private String title;
	private String description;
	private long progress;
	public InfoOverlay() {
		this.title = "";
		this.description = "";
		this.progress = -1;
	}
	public final void setTitle(String string) {
		if (!Data.Resources.minecraft.f_4309051) {
			throw new C_4434943();
		} else {
			this.title = string;
			GL11.glClear(256);
			GL11.glMatrixMode(5889);
			GL11.glLoadIdentity();
			Window window = new Window(Data.Resources.minecraft.f_0545414, Data.Resources.minecraft.f_5990000);
			GL11.glOrtho(0.0, window.m_2112110(), window.m_3634999(), 0.0, 100.0, 300.0);
			GL11.glMatrixMode(5888);
			GL11.glLoadIdentity();
			GL11.glTranslatef(0.0F, 0.0F, -200.0F);
		}
	}
	public final void setDescription(String string) {
		if (!Data.Resources.minecraft.f_4309051) {
			throw new C_4434943();
		} else {
			this.progress = 0L;
			this.description = string;
			this.setLoading(-1);
			this.progress = 0L;
		}
	}
	public final void setLoading(int value) {
		if (!Data.Resources.minecraft.f_4309051) {
			throw new C_4434943();
		} else {
			long time = System.currentTimeMillis();
			if (time - this.progress >= 20L) {
				this.progress = time;
				Window window = new Window(Data.Resources.minecraft.f_0545414, Data.Resources.minecraft.f_5990000);
				int width = window.m_2112110();
				int height = window.m_3634999();
				GL11.glClear(256);
				GL11.glMatrixMode(5889);
				GL11.glLoadIdentity();
				GL11.glOrtho(0.0, width, height, 0.0, 100.0, 300.0);
				GL11.glMatrixMode(5888);
				GL11.glLoadIdentity();
				GL11.glTranslatef(0.0F, 0.0F, -200.0F);
				GL11.glClear(16640);
				BufferBuilder bufferBuilder = BufferBuilder.INSTANCE;
				GL11.glBindTexture(3553, Data.Resources.minecraft.f_9413506.load("/dirt.png"));
				bufferBuilder.start();
				bufferBuilder.color(4210752);
				bufferBuilder.vertex(0.0, height, 0.0, 0.0, (float)height / 32.0F);
				bufferBuilder.vertex(width, height, 0.0, (float)width / 32.0F, (float)height / 32.0F);
				bufferBuilder.vertex(width, 0.0, 0.0, (float)width / 32.0F, 0.0);
				bufferBuilder.vertex(0.0, 0.0, 0.0, 0.0, 0.0);
				bufferBuilder.end();
				if (value >= 0) {
					int x = width / 2 - 50;
					int y = height / 2 + 16;
					GL11.glDisable(3553);
					bufferBuilder.start();
					bufferBuilder.color(8421504);
					bufferBuilder.vertex(x, y, 0.0);
					bufferBuilder.vertex(x, y + 2, 0.0);
					bufferBuilder.vertex(x + 100, y + 2, 0.0);
					bufferBuilder.vertex(x + 100, y, 0.0);
					for (int v = 0; v < value; v++) {
						bufferBuilder.color(Color.getHSBColor(v / 100.0F, 1.0f, 1.0f).getRGB());
						bufferBuilder.vertex(x + v, y, 0.0);
						bufferBuilder.vertex(x + v, y + 2, 0.0);
						bufferBuilder.vertex(x + v + 1, y + 2, 0.0);
						bufferBuilder.vertex(x + v + 1, y, 0.0);
					}
					bufferBuilder.end();
					GL11.glEnable(3553);
				}
				Data.Resources.minecraft.f_0426313.drawWithShadow(this.title, (width - Data.Resources.minecraft.f_0426313.getWidth(this.title)) / 2, height / 2 - 4 - 16, 16777215);
				Data.Resources.minecraft.f_0426313.drawWithShadow(this.description, (width - Data.Resources.minecraft.f_0426313.getWidth(this.description)) / 2, height / 2 - 4 + 8, 16777215);
				Display.update();
				Thread.yield();
			}
		}
	}
}
