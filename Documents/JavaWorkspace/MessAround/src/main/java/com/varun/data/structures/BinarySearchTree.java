package com.varun.data.structures;

public class BinarySearchTree {

	Node root;

	public static void main(String[] arg) {
		BinarySearchTree theTree = new BinarySearchTree();
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

		theTree.printPretty();

//		System.out.println(theTree.findNode(11));
		Node min = theTree.findMinimum(theTree.root);

		theTree.preOrderTraverseTree(theTree.root);
		System.out.println();

		theTree.removeNode(theTree.findNode(4));
		theTree.printPretty();
		theTree.preOrderTraverseTree(theTree.root);
	}

	public void removeNode(Node nodeToRemove) {

		// if the node has no children, simply remove its reference from parent node.
		if (nodeToRemove.leftChild == null && nodeToRemove.rightChild == null) {

			if (nodeToRemove.parent == null) {
				// only node in the tree, just set everything to null
				nodeToRemove = null;
			}
			else {
				if (isLeftChild(nodeToRemove)) {
					nodeToRemove.parent.leftChild = null;
				} else {
					nodeToRemove.parent.rightChild = null;
				}
			}
		}

		// if the node has only one child, replace its reference
		// from the parent node with its child.
		if (nodeToRemove.leftChild != null && nodeToRemove.rightChild == null) {
			replaceNode(nodeToRemove, nodeToRemove.leftChild);
		} else if (nodeToRemove.leftChild == null && nodeToRemove.rightChild != null) {
			replaceNode(nodeToRemove, nodeToRemove.rightChild);
		}

		// if the node has both the children present, find the closest node for replacement.
		// Closest node should be leftmost node from the right child tree.
		if (nodeToRemove.leftChild != null && nodeToRemove.rightChild != null) {
			Node replacementNode = findMinimum(nodeToRemove.rightChild);

			System.out.println("Replacement node = " + replacementNode);

			if (!replacementNode.parent.equals(nodeToRemove)) {
//				if (replacementNode.rightChild != null) {
					replaceNode(replacementNode, replacementNode.rightChild);
//				}
				replacementNode.rightChild = nodeToRemove.rightChild;
				replacementNode.rightChild.parent = replacementNode;
			}

			replaceNode(nodeToRemove, replacementNode);
			replacementNode.leftChild = nodeToRemove.leftChild;
			replacementNode.leftChild.parent = replacementNode;
		}
	}

	private void replaceNode(Node nodeToReplace, Node replacementNode) {
		if (nodeToReplace.parent == null) {
			// no parent means root node
			root = replacementNode;

		}
		else {
			if (isLeftChild(nodeToReplace)) {
				nodeToReplace.parent.leftChild = replacementNode;
			}
			else {
				nodeToReplace.parent.rightChild = replacementNode;
			}
		}
		if (replacementNode != null) {
			replacementNode.parent = nodeToReplace.parent;
		}
//		replacementNode.leftChild = nodeToReplace.leftChild;
//		replacementNode.rightChild = nodeToReplace.rightChild;
//		nodeToReplace.parent = null;
//		nodeToReplace.leftChild = null;
//		nodeToReplace.rightChild = null;
	}

	private boolean isLeftChild(Node node) {
		return node.equals(node.parent.leftChild);
	}

	private Node findMinimum(Node root) {
		Node focusNode = root;
		Node minimum = root;
		int key = root.key;
		if (focusNode.leftChild != null) {
			minimum = findMinimum(focusNode.leftChild);
		} else {
			minimum = focusNode;
		}
		return minimum;
	}

	public void insertNode(int key) {
		Node newNode = new Node(key);
		if (root == null) {
			root = newNode;
		} else {
			Node parentNode = root;
			Node focusNode = root;
			while (true) {
				parentNode = focusNode;
				if (key < focusNode.key) {
					focusNode = focusNode.leftChild;
					if (focusNode == null) {
						parentNode.leftChild = newNode;
						newNode.parent = parentNode;
						return;
					}
				} else {
					focusNode = focusNode.rightChild;
					if (focusNode == null) {
						parentNode.rightChild = newNode;
						newNode.parent = parentNode;
						return;
					}
				}
			}
		}
	}

	public Node findNode(int key) {
		Node focusNode = root;
		while (focusNode.key != key) {
			if (key < focusNode.key) {
				focusNode = focusNode.leftChild;
			} else {
				focusNode = focusNode.rightChild;
			}

			if (focusNode == null)
				return focusNode;
		}

		return focusNode;
	}

	public void inOrderTraverseTree(Node focusNode) {
		if (focusNode != null) {
			inOrderTraverseTree(focusNode.leftChild);
			System.out.print(focusNode);
			inOrderTraverseTree(focusNode.rightChild);
		}
	}

	public void preOrderTraverseTree(Node focusNode) {
		if (focusNode != null) {
			System.out.print(focusNode);
			preOrderTraverseTree(focusNode.leftChild);
			preOrderTraverseTree(focusNode.rightChild);
		}
	}

	public void postOrderTraverseTree(Node focusNode) {
		if (focusNode != null) {
			preOrderTraverseTree(focusNode.leftChild);
			preOrderTraverseTree(focusNode.rightChild);
			System.out.print(focusNode);
		}
	}

	public void printPretty() {
		printPretty(root, "");
	}

	private void printPretty(Node focusNode, String prefix) {
		if (focusNode != null) {
			System.out.println(prefix + "├── " + focusNode.key);
			printPretty(focusNode.leftChild, prefix + "│  (L) ");
			printPretty(focusNode.rightChild, prefix + "│  (R) ");
		}
	}
}

class Node {
	int key;
	Node leftChild = null;
	Node rightChild = null;
	Node parent = null;

	Node(int key) {
		this.key = key;
	}

	public String toString() {
		return " " + key;
	}
}