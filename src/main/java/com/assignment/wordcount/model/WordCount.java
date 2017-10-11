package com.assignment.wordcount.model;

public class WordCount {

	private String word;
	private long count;
	
	public WordCount(String word, long count) {
		this.setWord(word);
		this.setCount(count);
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	

}
