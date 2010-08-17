Serverside sync (in Java / on appengine) for persistencejs
==========================================================

This is a proof-of-concept, on top of slim3 appengine java framework.
It currently just runs the presistencejs sync testsuite.

Requirements: 
-------------
- eclipse with appengine plugin

Testsuite:
----------
- To run the testsuite, start the server and point your browser to its address 

Howto:
-----
to make your own models syncable, they need to be modified as follows (see models sourcecode):
- add `_lastChange` property (Long) and getter / setter
- add `dirty` property and put method 
- add Jackson JSON annotations at several places

TODO:
---- 
- add ANT task to generate syncable models and syncService
