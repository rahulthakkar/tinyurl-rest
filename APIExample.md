**Add Long URL**
----
  Returns JSON data about the added longURL along with short URL hash 

* **URL**

  /tinyurl/api/url

* **Method:**

  `POST`
  
*  **URL Params**

   None 

* **Data Params**

  None

* **JSON Payload Example**

  `{ "longURL": "www.linkedin.com" }`

* **Success Response:**

  * **Code:** 201 CREATED
    **Content:** `{ "longURL": "http://www.linkedin.com", "shortURLHash": "MSrowH9" }`
 
* **Error Response:**

  * **Code:** 400 BAD REQUEST 
    **Content:** `{ "errorCode": 400, "errorMessage": "LongURL length should be greater than zero and less than 2048" }`

  OR

  * **Code:** 400 BAD REQUEST 
    **Content:** `{ "errorCode": 400, "errorMessage": "xyz is not a valid URL " }`









**Get Long URL from tinyURL hash**
----
  Returns JSON data about the longURL along with short URL hash 

* **URL**

  /tinyurl/api/url/:hash

* **Method:**

  `GET`
  
*  **URL Params**

  hash=[string]

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 OK
    **Content:** `{ "longURL": "http://www.linkedin.com", "shortURLHash": "MSrowH9" }`
 
* **Error Response:**

  * **Code:** 404 NOT FOUND 
    **Content:** ` { "errorCode": 404, "errorMessage": "URL with hash xyxz not found on server" }`














**Blocklist tinyURL hash – even if not present in DB**
----
  Returns status code 

* **URL**

  /tinyurl/api/url/:hash

* **Method:**

  `DELETE`
  
*  **URL Params**

  hash=[string]

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 OK


