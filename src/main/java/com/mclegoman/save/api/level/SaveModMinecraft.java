package com.mclegoman.save.api.level;

import net.minecraft.client.C_5664496;
import net.minecraft.client.gui.screen.Screen;

public interface SaveModMinecraft {
	C_5664496 save$getInstance();
	void save$set(String string);
	void save$exit();
	void save$exit(Screen screen);
	void save$save();
	void save$set(SaveModWorld world);
	void save$load();
	void save$respawn();
}
