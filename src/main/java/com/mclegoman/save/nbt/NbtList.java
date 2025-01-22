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
import java.util.ArrayList;
import java.util.List;

public class NbtList extends NbtElement {
	private List<NbtElement> elements = new ArrayList<>();
	private byte type;

	public NbtList() {
	}

	final void write(DataOutput dataOutput) throws IOException {
		if (!this.elements.isEmpty()) this.type = (this.elements.get(0)).getType();
		else this.type = 1;
		dataOutput.writeByte(this.type);
		dataOutput.writeInt(this.elements.size());
		for (NbtElement element : this.elements) element.write(dataOutput);
	}

	final void read(DataInput dataInput) throws IOException {
		this.type = dataInput.readByte();
		int total = dataInput.readInt();
		this.elements = new ArrayList<>();
		for (int index = 0; index < total; ++index) {
			NbtElement element = NbtElement.create(this.type);
			if (element != null) element.read(dataInput);
			this.elements.add(element);
		}
	}

	public final byte getType() {
		return 9;
	}

	public final String toString() {
		StringBuilder stringBuilder = new StringBuilder().append(this.elements.size()).append(" entries of type ");
		String type;
		switch (this.type) {
			case 0:
				type = "TAG_End";
				break;
			case 1:
				type = "TAG_Byte";
				break;
			case 2:
				type = "TAG_Short";
				break;
			case 3:
				type = "TAG_Int";
				break;
			case 4:
				type = "TAG_Long";
				break;
			case 5:
				type = "TAG_Float";
				break;
			case 6:
				type = "TAG_Double";
				break;
			case 7:
				type = "TAG_Byte_Array";
				break;
			case 8:
				type = "TAG_String";
				break;
			case 9:
				type = "TAG_List";
				break;
			case 10:
				type = "TAG_Compound";
				break;
			default:
				type = "UNKNOWN";
		}
		return stringBuilder.append(type).toString();
	}

	public final void add(NbtElement nbtElement) {
		this.type = nbtElement.getType();
		this.elements.add(nbtElement);
	}

	public final NbtElement get(int i) {
		return this.elements.get(i);
	}

	public final List<NbtElement> get() {
		return this.elements;
	}

	public final int size() {
		return this.elements.size();
	}
}