package ui;

import java.util.List;

public class Node{
	
	public Node(String value, List<Subtree> subtrees) {
		this.value = value;
		this.subtrees = subtrees;
	}
	String value;
	List<Subtree> subtrees;
}

class Subtree{
	String znacajka;
	Node node;
	public Subtree(String znacajka, Node node) {
		this.znacajka = znacajka;
		this.node = node;
	}
}