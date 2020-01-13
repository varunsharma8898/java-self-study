package com.varun.data.structures;

/**
 * Created by sharmava on 11/07/17.
 */
public interface BinarySearchTree {

	public Node getRootNode();

	public void insertNode(int key);

	public void removeNode(Node nodeToRemove);

	public Node findNode(int key);

	public String inOrderTraverseTree(Node focusNode);

	public String preOrderTraverseTree(Node focusNode);

	public String postOrderTraverseTree(Node focusNode);

	public void printPretty();
}
