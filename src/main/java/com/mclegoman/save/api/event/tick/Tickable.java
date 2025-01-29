/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event.tick;

import net.minecraft.client.C_5664496;

public class Tickable {
    @FunctionalInterface
    public interface World {
        void run(C_5664496 minecraft, net.minecraft.world.World world);
    }
}
