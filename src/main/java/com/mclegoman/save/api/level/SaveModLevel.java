package com.mclegoman.save.api.level;

import com.mclegoman.save.api.nbt.NbtCompound;
import com.mclegoman.save.api.nbt.NbtElement;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SaveModLevel {
	public static NbtCompound load(InputStream inputStream) throws IOException {
		DataInputStream dataInputStream = new DataInputStream(new GZIPInputStream(inputStream));
		NbtCompound var5;
		try {
			NbtElement var1;
			if (!((var1 = NbtElement.deserialize(dataInputStream)) instanceof NbtCompound)) throw new IOException("Root tag must be a named compound tag");
			var5 = (NbtCompound)var1;
		} finally {
			inputStream.close();
		}
		return var5;
	}
	public static void save(NbtCompound nbtCompound, OutputStream outputStream) throws IOException {
		try (DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(outputStream))) {
			NbtElement.serialize(nbtCompound, dataOutputStream);
		}
	}
}
