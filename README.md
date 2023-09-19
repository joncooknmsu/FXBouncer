## FXBouncer

This program is a simple demonstration of JavaFX animation, namely bouncing shapes.

It uses an FXML window description that was created by using SceneBuilder. 

It also includes both referencing a shape (red ball) that is in the FXML 
description, and also dynamically creating a bunch of shapes (rectangles)

### To Compile and Run

The cmds.txt file has some example command-line commands that should work, but
the build.xml also works with Ant. Use "ant run" to compile and run the program,
and "ant runjar" to package up a jar and run it from a jar.

You may need to edit the path to the JavaFX modules in the build.xml file.

### VSCode support

The ".vscode" directory has a "settings.xml" file that does two things. One,
it points the code formatter to the local formatting specification, and two,
it includes the JavaFX libraries in the references path so that the editor
won't red-underline all of the JavaFX stuff.
