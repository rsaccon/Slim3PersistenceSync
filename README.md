Serverside sync (in Java / on appengine) for persistencejs
==========================================================

This a Java serverside for the [persistencejs](http://github.com/zef/persistencejs).sync testsuite, on top of slim3 appengine framework.

Requirements / dependencies to run the demo: 
--------------------------------------------
* eclipse with appengine plugin

In same folder as this project:

* [Annotation-processing-extension and ant-task code generator](http://github.com/rsaccon/persistencejs-gen)
* [Runtime-lib](http://github.com/rsaccon/persistencejs-sync)

Hoe to run the demo
-------------------
Import this project (and required dependencies) into eclipse, start the server 
and point your browser to the server address.

Custom projects
---------------
If started from a blank-project, the following additional setup steps are necessary

* Set annotation factory path (at the project java compiler settings) to persistencejs-gen jar (built by [persistencejs-gen](http://github.com/rsaccon/persistencejs-gen)).
* Use the build.xml provided by this project.
* Use the gen-persistencejs-sync task to create synced model and associated controller.
* Add fields to models as usual, use Sync annotation to mark fields for sync
* IMPORTANT: You still have to insert manually code to set dirty attributes inside the setters for synced attributes, see demo sourcecode for examples.

TODO:
-----
* Create sync-specific blank project
* Eliminate the need to insert manually code to set dirty attributes inside the setters for synced attributes.
