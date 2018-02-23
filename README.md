# Cache_Memory_Implementation
This repository is about an efficient cache memory implementation. It was implemented as an optional project of the course Data Structures, during my bachelor studies.

How to Run the Code:

The code can be found in the src folder. 

Compile Command : javac *.java

Run Command : java TestCacheSpeed dataFile requestsFile cacheSize

dataFile is a File containing all the entries (like the hard disk) with a specific Key and their content.

requestsFile is a File containing the requests of the entries, i.e. a sequence of Keys.

cacheSize should be set as an integer corresponding to the actual size of the cache.

for example : java TestCacheSpeed data-1000.dat  requests-5000.dat 23

Implements a cache with 23 memory slots, where hard disk contains 1000 entries and 5000 requests.

Sample input files can be found in the folder Datasets. 

For more information about the implementation of the data structure and the code itself, please read the pdf "Cache Implementation README".
