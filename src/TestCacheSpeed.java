import java.io.IOException;

public class TestCacheSpeed {

	// cacheSize contains an integer corresponding to the size of the cache
	
	static int cacheSize = 17; 
	
	//dataFile contains the path of the file with all the data (the data along with its corresponding key)
	
	static	String dataFile = "./Data/dataset-1000/data-1000.dat";
	
	//requestFile contains a sequence of keys, upon which we will test the implementation of the cache
	
	static	String requestsFile = "./Data/dataset-1000/requests-10000.dat";

	
	public static void main(String[] args) throws IOException {
		System.out.println("--------------- Program Started --------------------");
		
		dataFile = args[0];
		requestsFile = args[1];
		cacheSize = Integer.parseInt(args[2]);	
		

		/*Initialize the cache according to your preferences.
		 * In this case we consider that both the key and the value of the data are strings.*/
		Cache<String, String> cache = new CacheImpl<String,String>(cacheSize); // both key and data are set as String
		
		/* DataSource is a class representing the hard disk. Thus, when an element is not found in cache
		/ then we should use this class to look up its location.*/
		DataSource dataSource = new DataSource(dataFile);
		
		/*WorkloadReader is used to parse the requests one by one.*/
		WorkloadReader requestReader = new WorkloadReader(requestsFile);
		
		/*Set up initial values for two local variables*/
		String key = null;		
		long numberOfRequests = 0;
		
		/*start performance test*/
		long startTime = System.currentTimeMillis(); //track current time
		while ((key = requestReader.nextRequest()) != null) { // read next key request
			String data = cache.lookUp(key); // lookup the key into the cache
			if (data == null) {	
				/* if data was not found in the cache this means that 
				 * we need to track them in the disk, retrieve them 
				 * and place them into the cache*/
				data = dataSource.readItem(key);
				if (data == null) {
					throw new IllegalArgumentException("DID NOT FIND DATA WITH KEY " + key +". The disk "
							+ "does not contain the item with key " + key + ". The requests file contains a request (KEY) of a non existent item. "
									+ "Have you set up files properly?");
				}
				else{
					cache.store(key, data);
				}
			}
			else{
				// Element already in the cache. Cache Hit.;
			}
			numberOfRequests++;
		}

		/*speed test finished*/
		long duration = System.currentTimeMillis() - startTime;
		
		System.out.printf("Read %d items in %d ms\n", numberOfRequests,	duration);
		System.out.printf("Stats: lookups %d, hits %d, hit-ratio %f\n", cache.getNumberOfLookUps(), cache.getHits(), cache.getHitRatio());

		requestReader.close();
	}

}
