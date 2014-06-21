b14h.app
=============================

Install maven 3
http://maven.apache.org/download.cgi

Build
-----

    cd app
    mvn clean install


Run dev server
--------------

    cd app/app-ear
    mvn appengine:devserver


Run dev server on different port (default 8080)

    mvn appengine:devserver -Dappengine.port=5000
    
    
    
Mobile Testing
--------------

accessing dev server from you phone

    http://yourlocalip:8080



Prepopulate data
----------------

    http://127.0.0.1:8080/api/populate
    
 
Fetch all tasks
---------------

    http://127.0.0.1:8080/api/task


HTML Consumers (clients)
------------------------
 
    http://127.0.0.1:8080/html/parent
    http://127.0.0.1:8080/html/child
    http://127.0.0.1:8080/html/store
    

Admin console
-------------
    
    http://localhost:8080/_ah/admin
    

Unit testing
------------

    cd app/app-war
    mvn test
    
Skip testing in any maven task (quick reloading)

    -Dmaven.test.skip=true
   
    
Deploy to Google App Engine
---------------------------

clean install needed?

    cd app/app-ear
    mvn appengine:update
    

live url:
https://b14hgappid.appspot.com


    
    
