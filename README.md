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
* After generating setter/getter for new fields, insert at the setter body a meta function (which checks/sets a dirty field), see example below:
<code>
	public class MyModel implements Serializable {
		
		// ... cutted out
		
		@Sync
    	private String foo;
		
		// ... cutted out
		
		public void setFoo(String foo) {
        	MyModelMeta.get().syncFoo(this, foo);  // <<< add this !!!
        	this.foo = foo;
    	}
    }
</code>

TODO:
-----
* Create sync-specific blank project
* Add junit-tests which test for models wit synced fields whether all setters are replaced with meta setters.
