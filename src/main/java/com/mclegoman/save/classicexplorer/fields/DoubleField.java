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

/** Field for a double primitive */
public class DoubleField extends Field {
	double fieldValue;
	
	public DoubleField(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public Object getField() {
		return (Double) fieldValue;
	}
	
	@Override
	public void read() throws IOException {
		fieldValue = Reader.din.readDouble();
	}

	@Override
	public DoubleField clone() {
		return new DoubleField(fieldName);
	}
}
