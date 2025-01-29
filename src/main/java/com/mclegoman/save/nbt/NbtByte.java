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

public final class NbtByte extends NbtElement {
	public byte tag;

	public NbtByte() {
	}

	public NbtByte(byte b) {
		this.tag = b;
	}

	void serialize(DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(this.tag);
	}

	void deserialize(DataInput dataInput) throws IOException {
		this.tag = dataInput.readByte();
	}

	public byte getType() {
		return 1;
	}

	public String toString() {
		return String.valueOf(this.tag);
	}
}
