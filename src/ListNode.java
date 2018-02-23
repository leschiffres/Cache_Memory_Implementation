/**
 * ListNode represents a signe-link list node
 * Each node contains an T reference to data and a reference to the nextNode in the list. 
 */

class ListNode<T> {
	/** 
	 * package access members; List can access these directly 
	 */
	T data; // The data contaned in this node.
	ListNode<T> nextNode; // The next pointer of a node in a linked list
	ListNode<T> prevNode; // The previous pointer of a node in a linked list
	ListNode<T> nextHashNode;

	/**
	 * Constructor. It initializes data and sets next node to null 
	 * @param object a reference to node's data
	 */
	ListNode(T data) {
		this(data, null, null,null);
	}

	/**
	 * constructor creates ListNode with passed data and next node
	 * @param object the reference to node's data
	 * @param node the next node in the list
	 */
	ListNode(T data, ListNode<T> nextNode, ListNode<T> prevNode, ListNode<T> cacheNode) {
		this.data = data;
		this.nextNode = nextNode;
		this.prevNode = prevNode;
		this.nextHashNode = cacheNode;
	} 

	/**
	 * Returns this node's data
	 * @return the reference to node's data
	 */
	T getObject() {
		return data; 
	} 

	

	@Override
	public String toString() {
		return "(" + data.toString() + ")";
	}
	
	
	
} 