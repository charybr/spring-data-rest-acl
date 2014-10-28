spring-data-rest-acl
====================

# Spring Security ACL with Spring Data REST

This is a sample project to illustrate how to use ACL-based Authorization for entities exposed by Spring Data REST.
This is referred by:
http://stackoverflow.com/questions/26546072/using-spring-security-acl-with-spring-data-rest

## Steps to deploy and run Bookstore sample:

Note that "conf" directory contains config files that needs to be placed at /etc/bookstore/.
	- bookstore.properties for configuring database, hibernate, connection-pooling
	- logback.xml for setting log levels. Set log file location - /var/log/bookstore/bookstore.log

1. create db user book with password book:
	createuser --createdb --no-createrole -P book

2. create db:
	createdb bookstore -O book -E 'UTF8'

3. Run Spring Security ACL schema -  bookstore/src/scripts/sql/createAclSchemaPostgres.sql

4. Do a maven build and copy bookstore.war under tomcat/webapps.

5. Run tomcat

6. Run bookstore config sql - bookstore/src/scripts/sql/bookstoreConfig.sql

7. Go over the curl samples in bookstore/src/test/curl/bookstorecurl.txt to see how it works.



