package com.shvid.ninja;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class VTools {

	public static final void writeVLong(DataOutput out, long lvalue) throws IOException {
		int unsignedByte = 0;
		if (lvalue < 0L) {
			unsignedByte = 0x40; lvalue ^= -1L; 
		}
	    unsignedByte |= (byte) ((int) lvalue & 0x3F);
	    lvalue >>>= 6;
	    while(lvalue != 0L) {
	    	unsignedByte |= 0x80;
	    	out.writeByte(unsignedByte);
	    	unsignedByte = (int) lvalue & 0x7F;
	    	lvalue >>>= 7;
	    }
	    out.writeByte(unsignedByte);
	}
	
	public static final long readVLong(DataInput in) throws IOException {
		int unsignedByte = in.readUnsignedByte();
		long lvalue = unsignedByte & 0x3F;
		int numBits = 6;
		boolean isNegative = (unsignedByte & 0x40) != 0;
		while((unsignedByte & 0x80) != 0) {
			unsignedByte = in.readUnsignedByte();
			lvalue |= (long)(unsignedByte & 0x7F) << numBits;
			numBits += 7;
		}
		return isNegative ? lvalue ^ -1L : lvalue;
	}
	
}
