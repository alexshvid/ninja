package com.shvid.ninja;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * VTools Helper class
 * 
 * This class implements logic for fast serialization/deserialization of the variable data types
 * 
 * 
 * @author Alex Shvid
 *
 */


public final class VTools {

	/**
	 * Serialize Long to VLong form
	 * 
	 * [d][d][d][d][d][s][N][n] [d][d][d][d][d][d][d][n] [d][d][d][d][d][d][d][n] [d][d][d][d][d][d][d][n] ...
	 * 
	 * where:
	 * d - data bit
	 * s - sign bit
	 * n - next byte
	 * N - is null
	 * 
	 * @param out - serialization stream
	 * @param lvalue
	 * @throws IOException
	 */
	
	public static final void writeVLongObject(DataOutput out, Long lvalueRef) throws IOException {
		if (lvalueRef == null) {
			out.writeByte(0x40);
			return;
		}
		long lvalue = lvalueRef.longValue();
		int unsignedByte = 0;
		if (lvalue < 0L) {
			unsignedByte = 0x20; lvalue ^= -1L; 
		}
	    unsignedByte |= (byte) ((int) lvalue & 0x1F);
	    lvalue >>>= 5;
	    while(lvalue != 0L) {
	    	unsignedByte |= 0x80;
	    	out.writeByte(unsignedByte);
	    	unsignedByte = (int) lvalue & 0x7F;
	    	lvalue >>>= 7;
	    }
	    out.writeByte(unsignedByte);
	}	
	
	/**
	 * Deserialize VLong to Long
	 * 
	 * @param in - deserialization stream
	 * @return long
	 * @throws IOException
	 */
	
	public static final Long readVLongObject(DataInput in) throws IOException {
		int unsignedByte = in.readUnsignedByte();
		if ((unsignedByte & 0x40) != 0) {
			return null;
		}
		long lvalue = unsignedByte & 0x1F;
		int numBits = 5;
		boolean isNegative = (unsignedByte & 0x20) != 0;
		while((unsignedByte & 0x80) != 0) {
			unsignedByte = in.readUnsignedByte();
			lvalue |= (long)(unsignedByte & 0x7F) << numBits;
			numBits += 7;
		}
		return isNegative ? lvalue ^ -1L : lvalue;
	}

	
	/**
	 * Serialize long in VLong form
	 * 
	 * [d][d][d][d][d][d][s][n] [d][d][d][d][d][d][d][n] [d][d][d][d][d][d][d][n] [d][d][d][d][d][d][d][n] ...
	 * 
	 * where:
	 * d - data bit
	 * s - sign bit
	 * n - next byte
	 * 
	 * @param out - serialization stream
	 * @param lvalue
	 * @throws IOException
	 */
	
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
	
	/**
	 * Deserialize VLong to long
	 * 
	 * @param in - deserialization stream
	 * @return long
	 * @throws IOException
	 */
	
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
