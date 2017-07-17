# tinyurl-rest
TinyURL (URL shortener) like REST web-service with Java (JAX-RS) using Jersey




[APIExample.md]( APIExample.md) 
[BuildAndRunInstr.md]( BuildAndRunInstr.md)


**Short URL generation logic:**
---
* URL hash can have any of the 0-9, a-z, A-Z 62 alpha-numeric chars
* The length of the hash is 7 which allows 62^7 unique URLs
* Logic to generate has is to generate 7 random numbers between 0-61 and convert them to alpha-numeric chars and append them ( if hash-string already present in db try by generating new hash – might need multiple call if overall table size increases)
 * Alternate logic 1:  Use DB auto increment counter and convert it to Base62 – Will always be unique but for every insertion 2 db call will be required
 * Alternate logic 2:  Generate Long(64 bit) hash of the longURL and convert it to Base62


**Design Notes:**
---
* JAX-RS jersey implementation is used for RESTful web service implementation
* There is no use of any ORM framework: could be extended
* Data is store in a sqlite db (it’s embedded so easier to pass a db file for this kind of small project: obviously not suitable for real life application)
* Could be extended to use RDBMS DB like MySQL for real concurrency.  Sharding (using consistent hashing) can be used for scaling out
* Could also be extended to use HBase(would also help in terms of redundancy) like NoSQL DB for large traffic
* Most probably the  system will be read heavy: should be extended to take benefit of Locality of Reference via caching (internal application caching or Redis like in-memory data store) – have to be careful when blacklisting URLs 
* Can add multiple application servers with some software/ hardware load balancer in front



