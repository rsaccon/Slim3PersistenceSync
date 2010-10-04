Serverside sync (in Java / on appengine) for persistencejs
==========================================================

This a Java serverside for the [persistencejs](http://github.com/zef/persistencejs).sync, for demo puropose just running the sync testsuite. I it is based on the [slim3](http://code.google.com/p/slim3) appengine framework.

Requirements / dependencies to run the demo: 
--------------------------------------------
* eclipse with appengine plugin

In same folder as this project:

* Annotation-processing-extension and ant-task code generator: [http://github.com/rsaccon/persistencejs-gen](http://github.com/rsaccon/persistencejs-gen)
* Runtime-lib: [http://github.com/rsaccon/persistencejs-sync](http://github.com/rsaccon/persistencejs-sync)

How to run the demo
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
* *IMPORTANT*: After generating setter/getter for new fields, replace the setter body with a meta setter, see Example below:

	public class MyModel implements Serializable {
		...
		@Sync
    	private String foo;
		...
		public void setFoo(String foo) {
        	MyModelMeta.get().setFoo(this, foo);   
    	}
    }

TODO:
-----
* Create sync-specific blank project
* Decrease the amount of boilerplate code needed to set by hand the dirty attribute inside setters for synced attributes.
