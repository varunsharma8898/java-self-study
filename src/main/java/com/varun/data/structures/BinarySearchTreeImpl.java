package com.varun.data.structures;

public class BinarySearchTreeImpl implements BinarySearchTree {

	private Node root;

	@Override
	public Node getRootNode() {
		return root;
	}

	@Override
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

	@Override
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

	@Override
	public void removeNode(Node nodeToRemove) {

		// if the node has no children, simply remove its reference from parent node.
		if (nodeToRemove.leftChild == null && nodeToRemove.rightChild == null) {

			if (nodeToRemove.parent == null) {
				// only node in the tree, just set everything to null
				nodeToRemove = null;
			} else {
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

			if (!replacementNode.parent.equals(nodeToRemove)) {
				replaceNode(replacementNode, replacementNode.rightChild);
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

		} else {
			if (isLeftChild(nodeToReplace)) {
				nodeToReplace.parent.leftChild = replacementNode;
			} else {
				nodeToReplace.parent.rightChild = replacementNode;
			}
		}
		if (replacementNode != null) {
			replacementNode.parent = nodeToReplace.parent;
		}
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

	@Override
	public String inOrderTraverseTree(Node focusNode) {
		StringBuilder returnStr = new StringBuilder();
		if (focusNode != null) {
			returnStr.append(inOrderTraverseTree(focusNode.leftChild));
			returnStr.append(focusNode.toString());
			returnStr.append(inOrderTraverseTree(focusNode.rightChild));
		}
		return returnStr.toString();
	}

	@Override
	public String preOrderTraverseTree(Node focusNode) {
		StringBuilder returnStr = new StringBuilder();
		if (focusNode != null) {
			returnStr.append(focusNode.toString());
			returnStr.append(preOrderTraverseTree(focusNode.leftChild));
			returnStr.append(preOrderTraverseTree(focusNode.rightChild));
		}
		return returnStr.toString();
	}

	@Override
	public String postOrderTraverseTree(Node focusNode) {
		StringBuilder returnStr = new StringBuilder();
		if (focusNode != null) {
			returnStr.append(preOrderTraverseTree(focusNode.leftChild));
			returnStr.append(preOrderTraverseTree(focusNode.rightChild));
			returnStr.append(focusNode.toString());
		}
		return returnStr.toString();
	}

	@Override
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