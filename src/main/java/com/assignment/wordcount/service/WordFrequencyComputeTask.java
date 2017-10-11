package com.assignment.wordcount.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assignment.wordcount.model.Key;

public class WordFrequencyComputeTask extends RecursiveTask<Map<Key, Long>> {
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 1L;
	private File file;
	private List<ForkJoinTask<Map<Key, Long>>> subTasks = new ArrayList<ForkJoinTask<Map<Key, Long>>>();

	public WordFrequencyComputeTask(File file) {
		super();
		this.file = file;
	}

	/**
	 * This method runs when a thread is forked through Fork/Join framework
	 * This method calculates word count from file .
	 * it divides operation into sub tasks recursively so that a task only handles single file computation 
	 * After completing subtasks, accumulate final output and return it
	 *    
	 */
	protected Map<Key, Long> compute() {
		
		_logger.info("Start compute processing");
		
		Map<Key, Long> result = new HashMap<Key, Long>();
		if(file.isFile() && file.getName().endsWith(".txt")) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

				String line = null;
				while ((line = reader.readLine()) != null) {
					String[] words = line.split("[^a-zA-Z0-9]+");
					for (String word : words) {
						if ("".equals(word)) {
							continue;
						}
						Key key = new Key(word);
						Long count = result.get(key);
						if (count == null) {
							result.put(key, 1l);
						} else {
							result.put(key,count+1l);
						}

					}
				}
			} catch (IOException e) {
				_logger.error("Error in reading file " + file.getName() +  " :"+ e.getMessage());
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					_logger.error("Error in closing buffer reader : " + e.getMessage());
				}
			}
		} else {
			final File[] children = file.listFiles();
			if (children != null) {
				for (final File child : children) {
					subTasks.add(new WordFrequencyComputeTask(child).fork());
				}
			}
		}
		for(ForkJoinTask<Map<Key, Long>> subtask :subTasks){
			Map<Key, Long> taskResult = subtask.join();
			
			for (Map.Entry<Key, Long> entry : taskResult.entrySet())
			{      
				Long count = result.get(entry.getKey());
				if(count == null ){
					count = 0l;
				}
				result.put(entry.getKey(), count + entry.getValue());
			}
		}
		return result;

	}
}
