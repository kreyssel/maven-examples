Example of how use <optional>-dependency from a dependency used in your 
project.

If a dependency in a project has a optional dependency defined,you has to 
define the optional dependency as a dependency in your project. But you has
also to set the version for this dependency.  

In this example is the optional dependency of javassist/cglib (bytecode 
manipulation library) in hibernate-core used, to show how you specify 
this dependency without to define the version. The example show how we can
reuse the version defined in the hibernate-parent artifact.