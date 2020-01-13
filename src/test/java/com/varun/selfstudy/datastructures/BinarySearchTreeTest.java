package com.varun.selfstudy.datastructures;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by sharmava on 11/07/17.
 */
public class BinarySearchTreeTest {

	private BinarySearchTree theTree;

	@Before
	public void setUp() {
		theTree = new BinarySearchTreeImpl();
	}

	@Test
	public void testInsertNode() {
		theTree.insertNode(4);
		theTree.insertNode(5);
		theTree.insertNode(3);

		Assert.assertEquals(4, theTree.getRootNode().key);
		Assert.assertEquals(5, theTree.getRootNode().rightChild.key);
		Assert.assertEquals(3, theTree.getRootNode().leftChild.key);
	}

	@Test
	public void testFindNode() {
		theTree.insertNode(4);
		theTree.insertNode(5);
		theTree.insertNode(2);
		theTree.insertNode(3);
		theTree.insertNode(1);

		Node someNode = theTree.findNode(3);
		Assert.assertEquals(4, theTree.getRootNode().key);
		Assert.assertEquals(3, someNode.key);
		Assert.assertEquals(2, someNode.parent.key);
		Assert.assertEquals(null, someNode.leftChild);
		Assert.assertEquals(null, someNode.rightChild);
	}

	@Test
	public void testDeleteRootNode() {
		theTree.insertNode(4);
		theTree.insertNode(5);
		theTree.insertNode(3);
		theTree.insertNode(1);
		theTree.insertNode(2);
		theTree.insertNode(8);
		theTree.insertNode(10);
		theTree.insertNode(7);
		theTree.insertNode(6);
		theTree.insertNode(9);
		theTree.insertNode(11);

		Assert.assertEquals(4, theTree.getRootNode().key);
		Assert.assertEquals(" 4 3 1 2 5 8 7 6 10 9 11", theTree.preOrderTraverseTree(theTree.getRootNode()));
		Assert.assertEquals(" 1 2 3 4 5 6 7 8 9 10 11", theTree.inOrderTraverseTree(theTree.getRootNode()));

		theTree.removeNode(theTree.findNode(4));

		Assert.assertEquals(5, theTree.getRootNode().key);
		Assert.assertEquals(" 5 3 1 2 8 7 6 10 9 11", theTree.preOrderTraverseTree(theTree.getRootNode()));
		Assert.assertEquals(" 1 2 3 5 6 7 8 9 10 11", theTree.inOrderTraverseTree(theTree.getRootNode()));
	}

	@Test
	public void testDeleteNodeWithNoChildren() {
		theTree.insertNode(4);
		theTree.insertNode(5);
		theTree.insertNode(3);
		theTree.insertNode(1);

		Assert.assertEquals(4, theTree.getRootNode().key);
		Assert.assertEquals(" 4 3 1 5", theTree.preOrderTraverseTree(theTree.getRootNode()));
		Assert.assertEquals(" 1 3 4 5", theTree.inOrderTraverseTree(theTree.getRootNode()));

		theTree.removeNode(theTree.findNode(5));

		Assert.assertEquals(4, theTree.getRootNode().key);
		Assert.assertEquals(" 4 3 1", theTree.preOrderTraverseTree(theTree.getRootNode()));
		Assert.assertEquals(" 1 3 4", theTree.inOrderTraverseTree(theTree.getRootNode()));
	}

	@Test
	public void testDeleteNodeWithTwoChildren() {
		theTree.insertNode(4);
		theTree.insertNode(5);
		theTree.insertNode(3);
		theTree.insertNode(3);
		theTree.insertNode(1);

		Assert.assertEquals(4, theTree.getRootNode().key);
		Assert.assertEquals(" 4 3 1 3 5", theTree.preOrderTraverseTree(theTree.getRootNode()));
		Assert.assertEquals(" 1 3 3 4 5", theTree.inOrderTraverseTree(theTree.getRootNode()));

		theTree.removeNode(theTree.findNode(3));

		Assert.assertEquals(4, theTree.getRootNode().key);
		Assert.assertEquals(" 4 3 1 5", theTree.preOrderTraverseTree(theTree.getRootNode()));
		Assert.assertEquals(" 1 3 4 5", theTree.inOrderTraverseTree(theTree.getRootNode()));
	}
}
