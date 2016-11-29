# rudimentary-database-engine-
The goal of this project is to implement a (very) rudimentary database engine that is loosely based on a hybrid between MySQL and SQLite, which I call DavisBase. Your implementation should operate entirely from the command line and API calls (no GUI). Your database will only need to support actions on a single table at a time, no joins or nested queries. Like MySQL's InnoDB data engine (SDL), your program will use file-per-table approach to physical storage. Each database table will be physcially stored as a separate file. Each table file will be subdivided into logical sections of fixed equal size call pages. Therefore, each table file size will be exact increments of the global page_size attribute, i.e. all data files must share the same page_size attribute. You may make page_size be a configurable attribute, but you must support a page size of 512 Bytes. The test scenarios for grading will be based on a page_size of 512B. Once a database is initialized, your are not required to support a reformat change to its page_size (but you may implement such a feature if you choose). You may use any programming language you like, but all examples will be in Java.

Read Me:

Default page size: 512
Degree of B tree: 4


Leaf header: 
1 		00X00  	Page type
1 		00X01  	No of cells
2		00X02  	Start of cell pointer
4   		00X04  	Right pointer
2 * n   	00X08   	Record pointer

Interior page header:
1 	00X00	Page type
4 	00X01	Left pointer
4 	00X05	Index
4 	00X09	Right pointer
4 	00X0D	Right most pointer
4 	00X11	Parent pointer

Supported Commands:
All commands below are case insensitive

CREATE database database_name;					 
	Create new database.

CREATE table table_name(id int, name varchar);   
	Create new table under respective database.

USE database_name;                               
	Switched to new database.

DESCRIBE table_name;                             
	Display all the columns of table_name.

SHOW tables;                                     
	Display all tables under respective database.

SHOW databases;                                  
	Display all the databases present in the system.

SELECT * FROM table_name;                        
	Display all records in the table.

SELECT * FROM table_name WHERE rowid = <value>;  
	Display records whose rowid is <id>.

DELETE * FROM table_name WHERE rowid = <value>;  
	Delete records whose rowid is <id>.

DROP TABLE table_name;                           
	Remove table data and its schema.

DROP Database database_name;                     
	Remove database and its table.

VERSION;                                         
	Show the program version.

HELP;                                            
	Show this help information.

EXIT;/QUIT;                                      
	Exit the program.
