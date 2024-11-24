/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.api.entrypoint;

import org.quiltmc.loader.api.ModContainer;

public interface SaveModInit {
	String key = "mclm_save_init";
	void init(ModContainer mod);
}
