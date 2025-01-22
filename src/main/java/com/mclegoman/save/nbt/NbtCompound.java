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
import java.util.HashMap;
import java.util.Map;

public class NbtCompound extends NbtElement {
	private final Map<String, NbtElement> elements = new HashMap<>();

	public NbtCompound() {
	}

	final void write(DataOutput dataOutput) throws IOException {
		for (NbtElement element : this.elements.values()) NbtElement.serialize(element, dataOutput);
		dataOutput.writeByte(0);
	}

	final void read(DataInput dataInput) throws IOException {
		this.elements.clear();
		NbtElement element = NbtElement.deserialize(dataInput);
		while (element.getType() != 0) this.elements.put(element.getName(), element);
	}

	public final byte getType() {
		return 10;
	}

	public final void put(String string, NbtElement nbtElement) {
		this.elements.put(string, nbtElement.m_4457344(string));
	}

	public final void putByte(String string, byte b) {
		this.elements.put(string, (new NbtByte(b)).m_4457344(string));
	}

	public final void putShort(String string, short s) {
		this.elements.put(string, (new NbtShort(s)).m_4457344(string));
	}

	public final void putInt(String string, int i) {
		this.elements.put(string, (new NbtInt(i)).m_4457344(string));
	}

	public final void putLong(String string, long l) {
		this.elements.put(string, (new NbtLong(l)).m_4457344(string));
	}

	public final void putFloat(String string, float f) {
		this.elements.put(string, (new NbtFloat(f)).m_4457344(string));
	}

	public final void putString(String string, String string2) {
		this.elements.put(string, (new NbtString(string2)).m_4457344(string));
	}

	public final void putByteArray(String string, byte[] bs) {
		this.elements.put(string, (new NbtByteArray(bs)).m_4457344(string));
	}

	public final void putCompound(String string, NbtCompound nbtCompound) {
		this.elements.put(string, nbtCompound.m_4457344(string));
	}

	public final void putBoolean(String string, boolean bl) {
		this.putByte(string, (byte)(bl ? 1 : 0));
	}

	public final boolean containsKey(String string) {
		return this.elements.containsKey(string);
	}

	public final byte getByte(String string) {
		return !this.elements.containsKey(string) ? 0 : ((NbtByte)this.elements.get(string)).value;
	}

	public final short getShort(String string) {
		return !this.elements.containsKey(string) ? 0 : ((NbtShort)this.elements.get(string)).value;
	}

	public final int getInt(String string) {
		return !this.elements.containsKey(string) ? 0 : ((NbtInt)this.elements.get(string)).value;
	}

	public final long getLong(String string) {
		return !this.elements.containsKey(string) ? 0L : ((NbtLong)this.elements.get(string)).value;
	}

	public final float m_3941822(String string) {
		return !this.elements.containsKey(string) ? 0.0F : ((NbtFloat)this.elements.get(string)).value;
	}

	public final String getString(String string) {
		return !this.elements.containsKey(string) ? "" : ((NbtString)this.elements.get(string)).value;
	}

	public final byte[] getByteArray(String string) {
		return !this.elements.containsKey(string) ? new byte[0] : ((NbtByteArray)this.elements.get(string)).value;
	}

	public final NbtCompound getCompound(String string) {
		return !this.elements.containsKey(string) ? new NbtCompound() : (NbtCompound)this.elements.get(string);
	}

	public final NbtList getList(String string) {
		return !this.elements.containsKey(string) ? new NbtList() : (NbtList)this.elements.get(string);
	}

	public final boolean getBoolean(String string) {
		return this.getByte(string) != 0;
	}

	public final String toString() {
		return "" + this.elements.size() + " entries";
	}
	public final Map<String, NbtElement> get() {
		return this.elements;
	}
}
