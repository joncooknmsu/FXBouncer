//
// The Bounce program without FXML
//

import javafx.application.Application;
import javafx.scene.Scene;
//import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class Bounce extends Application
{

BorderPane pane = null;
Scene scene = null;
BounceController controller;
Pane gamePane;
Slider speedSlider;
static Bounce app;

// set app to last object created; this is currently not used but the JavaFX runtime
// creates TWO Bounce objects: on the first it runs init() and then start(), and then
// the second object is the one that actually exists when your program is running. So
// if you had stuff in this class that you needed, a static reference to this second
// object would be handy.
public Bounce()
{
    app = this;
}

@Override
public void init()
{
    // API docs say "NOTE: This method is not called on the JavaFX
    // Application Thread. An application must not construct a Scene
    // or a Stage in this method. An application may construct other
    // JavaFX objects in this method.
    pane = new BorderPane();
    controller = new BounceController(pane);
}

@Override
public void start(Stage primaryStage)
{
    scene = new Scene(pane);
    controller.initialize();
    // If you need the controller object, the line below works
    //BounceController controller = (BounceController) loader.getController();
    // I haven't tried using CSS yet, but the below might work
    // scene.getStylesheets().add("style.css");
    primaryStage.setTitle("Bouncer");
    primaryStage.setScene(scene);
    primaryStage.show();
    // System.out.println("FXApp:"+this);
}

public static void main(String[] args)
{
    Application.launch(args);
}

} // end class

// helpful sites for loading an FXML file "by hand":
// https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/doc-files/introduction_to_fxml.html
// https://jenkov.com/tutorials/javafx/fxml.html#specifiying-controller-class-in-fxml
// https://docs.oracle.com/javafx/2/api/javafx/fxml/doc-files/introduction_to_fxml.html#controllers
// https://docs.oracle.com/javafx/2/api/javafx/fxml/FXMLLoader.html
// https://docs.oracle.com/javafx/2/api/javafx/application/Application.html
//
// Good JavaFX Resources:
// * http://fxexperience.com/
// * https://martinfowler.com/eaaDev/PresentationModel.html (I'm not convinced
//          this is a good design pattern, but need to look at it more closely)
// * https://stackoverflow.com/questions/32739199/javafx-software-design
// * https://edencoding.com/mvc-in-javafx/ (some things I like, some I don't)
// * https://docs.oracle.com/javafx/2/best_practices/jfxpub-best_practices.htm
//
