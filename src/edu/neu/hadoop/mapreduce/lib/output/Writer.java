package edu.neu.hadoop.mapreduce.lib.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystem;
import java.util.List;

import edu.neu.hadoop.io.Writable;
import edu.neu.hadoop.mapreduce.Constants;
import edu.neu.hadoop.mapreduce.Counters;
import edu.neu.hadoop.mapreduce.KeyValue;

/**
 * Class to handle write operations to {@link FileSystem}
 * 
 * 
 */
@SuppressWarnings("rawtypes")
public class Writer {
	
	/**
	 * Write the {@link KeyValue} pairs to the {@link FileSystem} in the
	 * following structure: ./partition/numPartition/fileName
	 * 
	 * @param pairs List of {@link KeyValue} pairs
	 * @param numPartition Partition number
	 * @param fileName Name of the file
	 */
	public void write(List<KeyValue> pairs, int numPartition, String fileName) {
		String folderUri = Constants.PARTITION_FOLDER_NAME + String.valueOf(numPartition) + "/";
		File folder = new File(folderUri);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		String uri = folderUri + fileName;
		File file = new File(uri);
		
		try (
			ObjectOutputStream outputStream = 
				new ObjectOutputStream(new FileOutputStream(file));
		) {
			for (KeyValue pair : pairs) {
				Writable key = (Writable) pair.getKey();
				Writable value = (Writable) pair.getValue();
				key.write(outputStream);
				value.write(outputStream);
				Counters.MAP_OUTPUT_RECORDS++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
