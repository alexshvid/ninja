package com.shvid.ninja;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class VToolsTest extends TestCase {

	public void testVLong() throws IOException {
		assertEquals(0L, serializeAndDeserialize(0L));
		assertEquals(123L, serializeAndDeserialize(123L));
		assertEquals(-123L, serializeAndDeserialize(-123L));
		assertEquals(Integer.MAX_VALUE, serializeAndDeserialize(Integer.MAX_VALUE));
		assertEquals(Integer.MIN_VALUE, serializeAndDeserialize(Integer.MIN_VALUE));
		assertEquals(Long.MAX_VALUE, serializeAndDeserialize(Long.MAX_VALUE));
		assertEquals(Long.MIN_VALUE, serializeAndDeserialize(Long.MIN_VALUE));
	}
	
	private long serializeAndDeserialize(long expectedValue) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		VTools.writeVLong(dout, expectedValue);
		
		byte[] serializedVLong = bout.toByteArray();
		//System.out.println("VLong " + Arrays.toString(serializedVLong));
		
		long actualValue = VTools.readVLong(new DataInputStream(new ByteArrayInputStream(serializedVLong)));
		return actualValue;
	}
	
}
