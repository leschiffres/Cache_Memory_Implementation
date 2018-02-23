public class CacheImpl<K, V> implements Cache<K, V> {
	private long misses;
	private long hits;

	List<Data<K, V>> list;
	
	private ListNode<Data<K, V>>[] hashTable;
	private int size;

	public CacheImpl(int givenSize) {
		list = new List<Data<K, V>>();
		hashTable = new ListNode[givenSize];
		size = 0;
		misses = 0;
		hits = 0;
	}

	/*The lookUp method takes as argument the key of specific data and searches the corresponding element
	 * in the list. 
	 * 
	 * Initially, we need find the position of the item in the hash table. Then we parse the chain 
	 * until we find the requested element.
	 * 
	 * If the element is found, it is placed first in the list (Method moveAtFront of class list)
	 * 
	 * If the requested element is not found, the method returns null.
	 * */
	public V lookUp(K key) {
		
		
		// Find the position in the hashtable associated with the key value
		int hash = key.hashCode();
		int i = hash % hashTable.length;
		
		V data = null;
		/*
		 * If the position of the hashTable has elements, find which one corresponds
		 * to the given key and return the demanded value.
		 */
		if (hashTable[i] != null) {
			ListNode<Data<K, V>> tmp = hashTable[i]; // get the first node lying under the i-th position of the hashtable
			while (tmp != null) {
				if (key.equals(tmp.getObject().getKey())) { 
					// if the node in the hashtable has the same key with the one we are looking for return its data
					hits++;
					/* Since we have a "Cache hit" we need to move the node to the head of the list */
					list.moveAtFront(tmp);
					data = tmp.getObject().getValue();
					break;
				} 
				else {
					// get the next node of the chain lying under the i-th position of the hashtable until you reach its end
					tmp = tmp.nextHashNode;
				}
			}
		}
		
		misses++;
		return data;
	}
	
	public V store(K key, V value) {
		Data<K, V> evictedData = null;
		/* Allocate memory for the new data. */
		ListNode<Data<K, V>> addedData;
		// If the list is full delete the LRU node (lastNode)
		if (isFull()) {
			
			/* Remove the last(oldest) element from the list. */
			evictedData = list.removeFromBack();
			
			/* Remove the element from the hashtable */
			int hash = evictedData.getKey().hashCode();
			int i = hash % hashTable.length;
			ListNode<Data<K, V>> tmp = hashTable[i];
			/*
			 * If the evicted data is at the front of the hashtable delete the
			 * head node by making as new head the next of the current head.
			 */
			//if the node is first in the chain
			if (evictedData.getKey().equals(tmp.getObject().getKey())) {
				hashTable[i] = tmp.nextHashNode;
				tmp.nextHashNode = null;// the removed node does no longer point to an element of the chain.
				// This last command is not necessary, since the garbage collector will remove the element eventually.
				// No node is pointing to it. Not in the list, not in the corresponding chain in the hashtable
			}
			else{
				
				/*We parse the chain until we find the evicted element in the hashTable. This node is removed by making
				 * his previous node pointing to his next one.
				 * 
				 * We use the tmp variable to parse the list and prev variable points to the previous node of tmp.
				 * 
				 * Please notice that we start parsing from the second element of the chain. In this section*/
				ListNode<Data<K, V>> prev = tmp; 
				tmp = tmp.nextHashNode; // we continue the search starting by the second node
				while(tmp!= null){
					if (evictedData.getKey().equals(tmp.getObject().getKey())){
						// if data is found remove the node from the hashtable
						prev.nextHashNode = tmp.nextHashNode;
						tmp.nextHashNode = null;
					}
					prev = tmp;
					tmp = tmp.nextHashNode;
					
				}
			}
			/* Update the current size of the hashTable */
			size--;
		}
		/* Add the new data in the list. */
		list.insertAtFront(new Data<K, V>(key, value)); // the method insertAtFront will create a new ListNode
		addedData = list.getFirst(); // the created ListNode is retrieved
		
		/*Find the place of the added data in the hashtable*/
		int hash = key.hashCode();
		int i = hash % hashTable.length;
		/* When adding the new data in the hashtable in the chain of a specific position, we place the 
		 * new node in the head of the chain */
		addedData.nextHashNode = hashTable[i]; // if no element is in the chain then the nextHashNode is set to null
		hashTable[i] = addedData; // hashTable points to the new data
		size++;
		/*This if statement because we have to return the content of the specific data. 
		 * Not the whole corresponding instance of class Data. If no element was evicted
		 * (cache is not full) then variable evictedData is null*/
		if (evictedData != null){
			return evictedData.getValue();
		}
		else{
			return null;
		}
	}

	public double getHitRatio() {
		return (double) hits / (hits + misses);
	}

	public long getHits() {
		return hits;
	}

	public long getMisses() {
		return misses;
	}

	public long getNumberOfLookUps() {
		return hits + misses;
	}

	public boolean isFull() {
		return size == hashTable.length;
	}
}
