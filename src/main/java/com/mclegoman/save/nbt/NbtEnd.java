/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.nbt;

import java.io.DataInput;
import java.io.DataOutput;

public class NbtEnd extends NbtElement {
	public NbtEnd() {
	}

	final void read(DataInput dataInput) {
	}

	final void write(DataOutput dataOutput) {
	}

	public final byte getType() {
		return 0;
	}

	public final String toString() {
		return "END";
	}
}
