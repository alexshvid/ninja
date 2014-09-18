package com.shvid.ninja;

import java.math.BigDecimal;

/**
 * In-memory each PacketObject represents a data structure that fits in single byte array
 * 
 * [ metadata, fields ]
 * 
 * All fields are stored as ByteArrays, some fields can be fixed size fields, some of them can be a
 * floating size.
 * 
 * Fields
 * 
 * !!!!!!!!!!!!!!!
 * 
 *   THIS CLASS IS FULLY UN-SAFE, WHEN YOU CALL getInt(field) IT DOES NOT CHECK IS IT INT OR NOT
 *   METADATA STOES ONLY INFORMATION ABOUNT FIELD_NUM and byte[] associated with it, no field type.
 *   
 *   YOUR METADATA IS RESPONSIBLE TO KNOW fieldNum -> FieldName+Type AND type->PacketObject
 *   
 *   
 *   IT IS POSSIBLE TO DUMP PACKET OBJECT, getByteArray works for all fields
 *   
 *     System.out.println("type = " + getType());
 *     for (int field : getFields()) {
 *        System.out.println("Field " + field + ", value = " + Arrays.toString(getByteArray(field)));
 *     }
 *   
 * 
 * !!!!!!!!!!!!!!!
 * 
 * 
 * Immutable objects are best to transfer by network and store catalogs in memory,
 * Mutable objects are best to maintain state in memory
 * 
 * setField in immutable object can invoke memory overflow 
 * 
 * all fields are ordered from 0 to Max Number, Null fields are escaped from this list
 * 
 * metadata + field[]
 * 
 * Max Value size is 2Gb
 * metadata VInt, VInt
 * VInt - Type
 * VInt - Version
 * VInt - FieldsMax
 * VInt[FieldsMax] - offset_minus_prev_offset
 * 
 * getField(int) -> 
 * 
 * int header_add, int[] field_adds, int footer_add
 * 
 * @author ashvid
 *
 *  Immutable, Mutable, PartiallyMutable
 *
 */

public interface PacketObject {

	/*
	 * Stores type, associated with this PacketObject in caller system.
	 * It is the responsibility of the caller system to ask right fields with right types
	 */
	
	int getType();
	
	void setType(int type);
	
	/*
	 * Version matter only for caller system
	 */
	
	int getVersion();
	
	void setVersion(int version);
	
	/*
	 * Retrieve information of all stored fields, if field is null, then it is not stored at all,
	 * like not exists field
	 */
	
	int[] getFields();
	
	/*
	 * If field not exists, that it is null
	 */
	
	boolean hasField(int field);
	
	/*
	 * Same as setNull to the field
	 */
	
	void removeField(int field);

	/*
	 * Immutable objects can be represented in compact memory structure
	 * Changes in immutable objects are only possible when copy object
	 */
	
	boolean isImmutable();
	
	/*
	 * Snappy compression for the selected fields
	 */
	
	boolean isCompressed(int field);
	
	void setCompressed(int field, boolean enable);
	
	

	
	boolean getBoolean(int field);
	
	void setBoolean(int field, boolean value);
	
	boolean[] getBooleanArray(int field);
	
	void setBooleanArray(int field, boolean[] value);
	
	char getChar(int field);
	
	void setChar(int field, char value);
	
	char[] getCharArray(int field);
	
	void setCharArray(int field, char[] value);
	
	short getShort(int field);
	
	void setShort(int field, short value);
	
	short[] getShortArray(int field);
	
	void setShortArray(int field, short[] value);
	
	int getInt(int field);
	
	void setInt(int field, int value);

	int[] getIntArray(int field);
	
	void setIntArray(int field, int[] value);
	
	long getLong(int field);
	
	void setLong(int field, long value);
	
	long[] getLongArrayy(int field);
	
	void setLongArray(int field, long[] value);
	
	float getFloat(int field);
	
	void setFloat(int field, float value);
	
	float[] getFloatArray(int field);
	
	void setFloatArray(int field, float[] value);
	
	double getDouble(int field);
	
	void setDouble(int field, double value);
	
	double[] getDoubleArray(int field);
	
	void setDoubleArray(int field, double[] value);
	
	String getString(int field);

	void setString(int field, String value);
	
	BigDecimal getBigDecimal(int field);
	
	void setBigDecimal(int field, BigDecimal value);
	
	PacketObject getObject(int field);
	
	void setObject(int field, PacketObject obj);
	
	
}
