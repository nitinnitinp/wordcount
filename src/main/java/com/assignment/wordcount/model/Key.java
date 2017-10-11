package com.assignment.wordcount.model;

public class Key {

	private String word;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Key(String word) {
		this.word = word;
	}

	public int hashCode()
	{
		return word.length();
	}
	public boolean equals(Object obj)
	{
		return word.equalsIgnoreCase(((Key)obj).getWord());
	}

}
