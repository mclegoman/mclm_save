/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NbtInt extends NbtElement {
	public int tag;

	public NbtInt() {
	}

	public NbtInt(int i) {
		this.tag = i;
	}

	void serialize(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.tag);
	}

	void deserialize(DataInput dataInput) throws IOException {
		this.tag = dataInput.readInt();
	}

	public byte getType() {
		return 3;
	}

	public String toString() {
		return String.valueOf(this.tag);
	}
}
