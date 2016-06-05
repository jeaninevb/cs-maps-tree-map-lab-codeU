/**
 * 
 */
package com.flatironschool.javacs;

import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a Map using a binary search tree.
 * 
 * @param <K>
 * @param <V>
 *
 */
public class MyTreeMap<K, V> implements Map<K, V> {

	private int size = 0;
	private Node root = null;

	/**
	 * Represents a node in the tree.
	 *
	 */
	protected class Node {
		public K key;
		public V value;
		public Node left = null;
		public Node right = null;
		
		/**
		 * @param key
		 * @param value
		 * @param left
		 * @param right
		 */
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
		
	@Override
	public void clear() {
		size = 0;
		root = null;
	}

	@Override
	public boolean containsKey(Object target) {
		return findNode(target) != null;
	}

	/**
	 * Returns the entry that contains the target key, or null if there is none. 
	 * 
	 * @param target
	 */
	private Node findNode(Object target) {
		// some implementations can handle null as a key, but not this one
		if (target == null) {
            throw new NullPointerException();
	    }
		
		// something to make the compiler happy
		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) target;
		
		//call recursive helper function
		
		return getHelper(root, target);
		
        //return null;
	}

	/**
	 * Compares two keys or two values, handling null correctly.
	 * 
	 * @param target
	 * @param obj
	 * @return
	 */
	private boolean equals(Object target, Object obj) {
		if (target == null) {
			return obj == null;
		}
		return target.equals(obj);
	}

	@Override
	public boolean containsValue(Object target) {
		
		//get set of values and use Set.contains(Object obj) method 		
		Collection<V> valueSet = values();
		return valueSet.contains(target);
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(Object key) {
		Node node = findNode(key);
		if (node == null) {
			return null;
		}
		return node.value;
	}
	
	//Helper function
	//----------------------------------------------
	
	public Node getHelper(Node curr, Object target){
		
		if(curr == null){
			return null;
		}
		// something to make the compiler happy
		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) target;
		
		// the actual search
		int cmp = k.compareTo(curr.key);
		
		if(cmp < 0){
			
			return getHelper(curr.left, target);
		}
		else if(cmp > 0){
		
			return getHelper(curr.right, target);
		}
		else{
			return curr;
		}
	}

	//----------------------------------------------


	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new LinkedHashSet<K>();
		
		inOrder(root, set);
		
		for(K key: set){
			
			System.out.print(key + " . ");
		}
		return set;
	}
	
	private void inOrder(Node curr, Set<K> set){
		
		if(curr == null){
			return;
		}
				
		//in-order traversal
		//recursively recurse left subtree
		inOrder(curr.left, set);

		set.add(curr.key);
		//recursively recurse right subtree
		inOrder(curr.right, set);
	}

	@Override
	public V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}
		
		if (root == null) {
			root = new Node(key, value);
			size++;
			return null;
		}
		
		Node res = putHelper(root, key, value);
		
		return res.value;
		
		//return putHelper(root, key, value);
	}

	private Node putHelper(Node node, K key, V value) {
        
        if(node == null){
        	
        	size++;
        	Node newNode = new Node(key, value);
        	return newNode;
        	
        }   
        
        // something to make the compiler happy
		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) key;
		
		// the actual search
		int cmp = k.compareTo(node.key);
		if(cmp < 0){
			//System.out.println("LEFT");
			//System.out.println("LEFT VALUE:  " + node.left.value);

			node.left = putHelper(node.left, key, value);			
		}
		else if(cmp > 0){
		    //System.out.println("RIGHT");

			node.right = putHelper(node.right, key, value);			
		}
		else{
			
			node.value = value;
			//size++;
		}
		
		return node;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Collection<V> values() {
		Set<V> set = new HashSet<V>();
		Deque<Node> stack = new LinkedList<Node>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			if (node == null) continue;
			set.add(node.value);
			stack.push(node.left);
			stack.push(node.right);
		}
		return set;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyTreeMap<String, Integer>();
		map.put("Word1", 1);
		map.put("Word2", 2);
		Integer value = map.get("Word1");
		System.out.println(value);
		
		for (String key: map.keySet()) {
			System.out.println(key + ", " + map.get(key));
		}
	}

	/**
	 * Makes a node.
	 * 
	 * This is only here for testing purposes.  Should not be used otherwise.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public MyTreeMap<K, V>.Node makeNode(K key, V value) {
		return new Node(key, value);
	}

	/**
	 * Sets the instance variables.
	 * 
	 * This is only here for testing purposes.  Should not be used otherwise.
	 * 
	 * @param node
	 * @param size
	 */
	public void setTree(Node node, int size ) {
		this.root = node;
		this.size = size;
	}

	/**
	 * Returns the height of the tree.
	 * 
	 * This is only here for testing purposes.  Should not be used otherwise.
	 * 
	 * @return
	 */
	public int height() {
		return heightHelper(root);
	}

	private int heightHelper(Node node) {
		if (node == null) {
			return 0;
		}
		int left = heightHelper(node.left);
		int right = heightHelper(node.right);
		return Math.max(left, right) + 1;
	}
}
