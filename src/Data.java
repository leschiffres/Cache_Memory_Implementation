public class Data <K,V>{
	/*Every instance of this class is associated with a single element of our data. 
	 * Concretely, it contains two variables representing the key of every element 
	 * (private K key) and the actual data of every element (private V value) equivalently.
	 *  Please take into consideration that we use generics through the entire project such 
	 *  that those two variables can be set as any type (String, Integer or any other class).
	 *   In our implementation both key and value are set as String.
	 */
	private K key;
	private V value;
	public Data(){
		this(null, null);
	}
	public Data(K key, V value){
		this.key = key;
		this.value = value;
	}
	public K getKey(){
		return key;
	}
	public V getValue(){
		return value;
	}
	
	@Override
	public String toString(){
		//return String.format("<%s, %s>", key, value); 
		return key.toString();
	}
}