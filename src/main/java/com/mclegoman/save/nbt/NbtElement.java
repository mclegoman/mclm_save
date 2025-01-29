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
import java.nio.charset.StandardCharsets;

public abstract class NbtElement {
	private String name = null;

	public NbtElement() {
	}

	abstract void serialize(DataOutput dataOutput) throws IOException;

	abstract void deserialize(DataInput dataInput) throws IOException;

	public abstract byte getType();

	public final String getName() {
		return this.name == null ? "" : this.name;
	}

	public final NbtElement setName(String string) {
		this.name = string;
		return this;
	}

	public static NbtElement input(DataInput dataInput) throws IOException {
		byte typeByte = dataInput.readByte();
		if (typeByte == 0) return new NbtEnd();
		else {
			NbtElement element = get(typeByte);
			byte[] data = new byte[dataInput.readShort()];
			dataInput.readFully(data);
			element.name = new String(data, StandardCharsets.UTF_8);
			element.deserialize(dataInput);
			return element;
		}
	}

	public static void serialize(NbtElement nbtElement, DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(nbtElement.getType());
		if (nbtElement.getType() != 0) {
			byte[] var2 = nbtElement.getName().getBytes(StandardCharsets.UTF_8);
			dataOutput.writeShort(var2.length);
			dataOutput.write(var2);
			nbtElement.serialize(dataOutput);
		}
	}

	public static NbtElement get(byte b) {
		switch (b) {
			case 0:
				return new NbtEnd();
			case 1:
				return new NbtByte();
			case 2:
				return new NbtShort();
			case 3:
				return new NbtInt();
			case 4:
				return new NbtLong();
			case 5:
				return new NbtFloat();
			case 6:
				return new NbtDouble();
			case 7:
				return new NbtByteArray();
			case 8:
				return new NbtString();
			case 9:
				return new NbtList();
			case 10:
				return new NbtCompound();
			default:
				return null;
		}
	}
}
