package edu.neu.hadoop.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A serializable String object which implements a simple, efficient,
 * serialization protocol, based on {@link DataInput} and {@link DataOutput}
 * 
 * 
 * 
 */
public class Text implements Writable, Cloneable, Comparable<Text> {

	private String value;
	
	public Text() { value = ""; }

	public Text(String value) {
		this.value = value;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		byte[] data = value.getBytes("UTF-8");
		out.writeInt(data.length);
		out.write(data);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		int length = in.readInt();
		byte[] data = new byte[length];
		in.readFully(data);
		value = new String(data,"UTF-8");
	}

	@Override
	public int compareTo(Text o) {
		return this.value.compareTo(o.get());
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Writable) {
			Text o = (Text) obj;
			return o.value.equals(this.value);
		}
		return super.equals(obj);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Text(value);
	}
	
	public void set(String value) {
		this.value = value;
	}

	public String get() {
		return value;
	}
}
