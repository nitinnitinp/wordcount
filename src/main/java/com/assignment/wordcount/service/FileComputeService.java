package com.assignment.wordcount.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.wordcount.model.Key;
import com.assignment.wordcount.model.WordCount;
import com.assignment.wordcount.util.ConfigProperties;

@Service
public class FileComputeService {
	
	@Autowired
	ConfigProperties properties;
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * This method compute top 10 words count.
	 * it forks asyn recursive task , that task fork another sub task  per file recursively
	 * @param dirLocation
	 * @return
	 * @throws IOException
	 */
	public List<WordCount> computeWordCount(String dirLocation) throws IOException {
		
	    _logger.info("Start computeWordCount processing");
		
		ForkJoinPool pool = new ForkJoinPool(properties.getForkJoinThreadPoolSize());
		ForkJoinTask<Map<Key, Long>> task =  pool.submit(new WordFrequencyComputeTask(new File(dirLocation)));
		Map<Key, Long> wordCount =  task.join();

		List<Map.Entry<Key, Long>> entries =
				new LinkedList<Map.Entry<Key, Long>>( wordCount.entrySet() );
		Collections.sort(entries,
				new Comparator<Map.Entry<Key, Long>>() {
			public int compare( Map.Entry<Key, Long> obj1, Map.Entry<Key, Long> obj2 )
			{
				return (obj2.getValue()).compareTo( obj1.getValue() );
			}
		} );
		int count = 1;
		List<WordCount> results = new ArrayList<WordCount>();
		for (Map.Entry<Key, Long> entry : entries) {
			if (count > 10) {
				break;
			}
			results.add(new WordCount(entry.getKey().getWord(),entry.getValue()));
			count++;
		}

		return results;
	}
	
	public void deleteUploadedFile(String path) {
		
	}


}

