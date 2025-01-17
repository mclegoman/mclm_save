/*
    mclm_save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.entrypoint;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public interface SaveModInit {
	String key = "save_init";
	void init(ModContainer mod);
}
