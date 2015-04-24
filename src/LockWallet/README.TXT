==============
LOCKWALLET 0.1
==============
LockWallet is a java console password manager that stores passwords in a sqlite
database protected by 128bit AES encryption.

LockWallet makes the user able to select an account and store the password in 
the clipboard, so you can paste into a browser avoiding also keyloggers.

Clear text passwords are maintained in main memory, so avoid to use it on 
computers where ram may be hard read.

Q: Why 128bit encryption? NSA can easly broke it!!!!
A: Really??? Man, this is meant to be used with online services... do you really
think that NSA try to crack your password to access these databases? Good boy!

Q: Jawawawa is heavy as hell?
A: If you are using firefox now... java is not so heavy at all. :)
On the contrary, it's portable and maybe you can put a virtual machine on your
pendrive to run it in systems that does not provide one.

Q: It's not so intuitive to use...
A: Yes it is!
Q: No it isn't...
A: GTFO

Q: Why is it so bugged now?
A: Yes it is!
Q: No it isn't...
A: Ok!

========================
BUILD OUTPUT DESCRIPTION
========================

When you build an Java application project that has a main class, the IDE
automatically copies all of the JAR
files on the projects classpath to your projects dist/lib folder. The IDE
also adds each of the JAR files to the Class-Path element in the application
JAR files manifest file (MANIFEST.MF).

To run the project from the command line, go to the dist folder and
type the following:

java -jar "LockWallet.jar" 

To distribute this project, zip up the dist folder (including the lib folder)
and distribute the ZIP file.

Notes:

* If two JAR files on the project classpath have the same name, only the first
JAR file is copied to the lib folder.
* Only JAR files are copied to the lib folder.
If the classpath contains other types of files or folders, these files (folders)
are not copied.
* If a library on the projects classpath also has a Class-Path element
specified in the manifest,the content of the Class-Path element has to be on
the projects runtime path.
* To set a main class in a standard Java project, right-click the project node
in the Projects window and choose Properties. Then click Run and enter the
class name in the Main Class field. Alternatively, you can manually type the
class name in the manifest Main-Class element.
