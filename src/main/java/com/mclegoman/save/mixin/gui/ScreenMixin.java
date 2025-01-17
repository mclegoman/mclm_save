/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.gui;

import com.mclegoman.save.api.gui.screen.SaveModScreen;
import net.minecraft.client.gui.screen.Screen;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;

@ClientOnly
@Mixin(Screen.class)
public abstract class ScreenMixin implements SaveModScreen {
	public void save$confirmResult(boolean value, int id) {
	}
}
