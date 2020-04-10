package com.practice.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Trie {

	static class TrieNode{
		Map<Character,TrieNode> children;
		boolean endOfWord;
		
		public TrieNode() {
			children = new HashMap<>();
			endOfWord = false;
		}
	}
	
	static TrieNode root;
	public Trie() {
		root = new TrieNode();
	}
	
	public static void insert(String word) {
		TrieNode current = root;
		for(int i=0;i<word.length();i++) {
			char ch = word.charAt(i);
			TrieNode node = current.children.get(ch);
			if(node==null) {
				node = new TrieNode();
				current.children.put(ch,node);
				System.out.println("Inserted key: " + ch);
				System.out.println("Inserted trie node: " + node.endOfWord + " " + node.children);
			}
			current = node;
		}
		current.endOfWord = true;
		System.out.println("Updating endOWord to true for current: " + current.endOfWord + " " + current.children);
	}
	
	public static boolean search(String word) {  //this search method will return true only for whole words - not prefixes
		TrieNode currentNode = root;
		
		for(int i=0;i<word.length();i++) {
			char ch = word.charAt(i);
			TrieNode node = currentNode.children.get(ch);
			if(node==null) {
				return false;
			}
			currentNode = node;
		}
		return currentNode.endOfWord;
	}
	
	public static void delete(String word) {
		delete(root,word,0);
	}

	private static boolean delete(TrieNode current, String word, int index) {
		
		//if we have reached end of word, then only delete if current.endOfWord is true
		if(index == word.length()) {
			
			if(current.endOfWord==false) {  //means we don't have exact string in our trie
				return false;
			}
			current.endOfWord = false;	
			return current.children.size()==0;
		}
		
		//check if we have got character in trie
		char ch = word.charAt(index);
		TrieNode node = current.children.get(ch);
		if(node == null) {
			return false;
		}
		
		boolean shouldDeleteCurrentNode = delete(node,word,index+1);
		System.out.println("Should Delete current node: " + shouldDeleteCurrentNode);
		
		if(shouldDeleteCurrentNode) {
			System.out.println("Deleting node: " + current.children.get(ch) + " " + current.children.size());
			current.children.remove(ch);
			return current.children.size()==0;
		}
		return false;
	}
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String args[]) throws IOException {
		Trie trie = new Trie();
		
		System.out.println("Enter strings to insert: ");
		String str = br.readLine(); //abc
		insert(str); 
		str = br.readLine();   //abgl
		insert(str);
		str = br.readLine();   //cdf
		insert(str);
		str = br.readLine();   //abcd
		insert(str);
		
		System.out.println("Enter strings to search: ");
		String search = br.readLine();   //ab - prefix
		boolean hasString = search(search);
		System.out.println(hasString);
		
		search = br.readLine();   //abcd
		hasString = search(search);
		System.out.println(hasString);
		
		search = br.readLine();   //abgl
		hasString = search(search);
		System.out.println(hasString);
		
		System.out.println("Enter strings to delete: ");
		String delete = br.readLine();
		delete(delete);
		delete = br.readLine();
		delete(delete);
		
	}
}
