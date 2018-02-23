/**
 * Generic version of a single-link List . Uses {@link ListNode} for list nodes.
 */
public class List<T> {
	private ListNode<T> firstNode;
	private ListNode<T> lastNode;
	private String name; // Name of the list. This field is optional

	/**
	 * constructor creates empty List with "list" as the name
	 */
	public List() {
		this("list");
	}

	/**
	 * constructor creates an empty List with a name.
	 * @param listName the list name
	 */
	public List(String listName) {
		name = listName;
		firstNode = lastNode = null;
	}

	/**
	 * Inserts an element at the front of the list
	 * @param insertItem the inserted data
	 */
	public void insertAtFront(T insertItem) {
		ListNode<T> node = new ListNode<T>(insertItem);
		
		/* if the list is empty then both firstNode and lastNode should refer to same object*/
		if (isEmpty())
			firstNode = lastNode = node;
		else {
			node.nextNode = firstNode; //new first node points to previously first
			firstNode.prevNode = node; // previously first points to new first
			firstNode = node;
			
			
		}
	} // end method insertAtFront

	
	/**
	 * Inserts an element at the end of the list
	 * @param insertItem the inserted item
	 */
	public void insertAtBack(T insertItem) {
		ListNode<T> node = new ListNode<T>(insertItem);
		

		/* if the list is empty then both firstNode and lastNode should refer to same object*/
		if (isEmpty())
			firstNode = lastNode = node;
		else { 
			node.prevNode = lastNode;// lastNode's nextNode refers to new node
			lastNode.nextNode = node;
			lastNode = node;
		}
	} // end method insertAtBack

	/**
	 * Returns and removes the data from the list head
	 * @return the data contained in the list head
	 * @throws EmptyListException if the list is empty
	 */
	public T removeFromFront() throws EmptyListException {
		if (isEmpty()) // throw exception if List is empty
			throw new EmptyListException(name);

		T removedItem = firstNode.data; // retrieve data being removed

		// update references firstNode and lastNode
		if (firstNode == lastNode){
			firstNode = lastNode = null;
		}
		else{
			firstNode = firstNode.nextNode;
			firstNode.prevNode = null;
		}

		return removedItem; // return removed node data
	} // end method removeFromFront

	/**
	 * Returns and removes the data from the list tail
	 * @return the data contained in the list tail
	 * @throws EmptyListException if the list is empty
	 */
	public T removeFromBack() throws EmptyListException {
		if (isEmpty()) // throw exception if List is empty
			throw new EmptyListException(name);

		T removedItem = lastNode.data; // retrieve data being removed

		// update references firstNode and lastNode
		if (firstNode == lastNode){
			firstNode = lastNode = null;
		}
		else 
		{
			lastNode = lastNode.prevNode; // locate new last node			
			lastNode.nextNode = null;
		} 

		return removedItem; // return removed node data
	} // end method removeFromBack

	/**
	 * Determine whether list is empty
	 * @return true if list is empty
	 */
	public boolean isEmpty() {
		return firstNode == null && lastNode == null; // return true if List is empty
	} // end method isEmpty

	/**
	 * Prints the list's contents to System.out
	 */
	public void print() {
		if (isEmpty()) {
			System.out.printf("Empty %s\n", name);
			return;
		} // end if

		System.out.printf("The %s is: ", name);
		ListNode<T> current = firstNode;

		// while not at end of list, output current node's data
		while (current != null) {
			System.out.printf("%s ", current.data);
			current = current.nextNode;
		} // end while

		System.out.println("\n");
	} // end method print
	
	//Move the indicated Node to the start of the list
	void moveAtFront(ListNode<T> newTop){
		/* if the node we are trying to place first in the list is already in the head, then there is no point 
		 of doing anything*/
		if(newTop == firstNode){
			return;
		}
		else if(newTop == lastNode){
			//Suppose that we have the list n1-->n2-->n3
			//								n1<--n2<--n3
			newTop.prevNode.nextNode = null;// n2-->null the element currently preceding newTop should not point to it anymore, otherwise we have a circular list
			lastNode = newTop.prevNode; // lastnode = n2 Update the new lastNode
			newTop.prevNode = null; // null<--n3 Since newTop is first he should not have a previous node
			newTop.nextNode = firstNode; // n3 --> n1 The newTop node points to the previously first 
			firstNode.prevNode = newTop; // n3 <--n1 The previously firstnode becomes second and points backwards to the new top
			firstNode = newTop; 
		}
		else{

			// We consider the nextNode of the node that we want to place in the head of the list
			ListNode<T> nextNode = newTop.nextNode;
			if(nextNode == null){
				System.out.printf("%s doesnt have next\n", newTop); // Since the node is neither first or last, 
				//it should have a nextNode. Otherwise there is a bug. This code is unreachable if program works ok.
			}
			/*Suppose that we have the list n1-->n2-->n3-->n4
			 * 								n1<--n2<--n3<--n4
			 * and we want to place n3 at front */
			newTop.nextNode.prevNode = newTop.prevNode; // n2<--n4
			newTop.prevNode.nextNode = newTop.nextNode; // n2-->n4
			/*Move the node to the head of the list*/
			newTop.nextNode = firstNode; // n3--> n1 
			newTop.prevNode = null; 
			firstNode.prevNode = newTop; // n3<--n1
			firstNode = newTop;
			
		}
		
	}
	public ListNode<T> getFirst(){
		return firstNode;
	}
	
	
	
	
	
} // end class List
