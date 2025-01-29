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

public final class NbtShort extends NbtElement {
	public short tag;

	public NbtShort() {
	}

	public NbtShort(short s) {
		this.tag = s;
	}

	void serialize(DataOutput dataOutput) throws IOException {
		dataOutput.writeShort(this.tag);
	}

	void deserialize(DataInput dataInput) throws IOException {
		this.tag = dataInput.readShort();
	}

	public byte getType() {
		return 2;
	}

	public String toString() {
		return String.valueOf(this.tag);
	}
}
