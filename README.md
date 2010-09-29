Serverside sync (in Java / on appengine) for persistencejs
==========================================================

This a Java serverside for the pesistncejs.sync testsuite, on top of slim3 appengine framework.

Requirements / dependencies to run the demo: 
--------------------------------------------
* eclipse with appengine plugin

In same folder as this project:

* Annotation-extension:  http://github.com/rsaccon/persistencejs-gen
* Runtime-lib: http://github.com/rsaccon/persistencejs-sync

For custom projects the requirements are the same. Make sure you use the persistencejs-gen jar by adjusting the annotation factory path at the project java compiler settings.
SyncControllers contain a lot of boilerplate code. For now, copy and refactor them from the demo.
Models must have the sync annotation for synced fields. For now, you must manually add "dirty" attributes to models and dirty-setters inside synced attribute setters.

Hoe to run the demo
-------------------
To run the testsuite, import this project (and required dependencies) into eclipse, start the server 
and point your browser to the server address.

TODO:
-----
* Create ant task for generating synced model and controller
* Eliminate the need to insert manually dirty-setters inside synced attribute setters.
