/*
    ClassicExplorer
    Author: bluecrab2
    Github: https://github.com/bluecrab2/ClassicExplorer
    Licence: All Rights Reserved

    ClassicExplorer is included with Save (mclm_save) with permission from bluecrab2.
*/

package com.mclegoman.save.classicexplorer.fields;

import com.mclegoman.save.classicexplorer.io.Reader;

import java.io.IOException;

/** Field for an int primitive */
public class IntField extends Field {
	int fieldValue;
	
	public IntField(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public Object getField() {
		return (Integer) fieldValue;
	}
	
	@Override
	public void read() throws IOException {
		fieldValue = Reader.din.readInt();
	}

	@Override
	public IntField clone() {
		return new IntField(fieldName);
	}
}
