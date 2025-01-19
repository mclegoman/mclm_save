/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NbtInt extends NbtElement {
	public int value;

	public NbtInt() {
	}

	public NbtInt(int i) {
		this.value = i;
	}

	final void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.value);
	}

	final void read(DataInput dataInput) throws IOException {
		this.value = dataInput.readInt();
	}

	public final byte getType() {
		return 3;
	}

	public final String toString() {
		return "" + this.value;
	}
}
