//
// Controller for Bounce app
//

import javafx.scene.layout.*;
import javafx.event.*;
import javafx.scene.shape.Circle;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.stream.IntStream;
//import java.util.PrimitiveIterator.OfInt;

public class BounceController extends AnimationTimer
{

private Slider speedSlider;
private Circle redBall;
private Pane gamePane;
private ArrayList<FlyingBox> boxes;

// Constructor cannot really do much since the FXML elements are not
// yet initialized (try printing them; they're null)
public BounceController(BorderPane appPane)
{
    MenuBar menuBar = new MenuBar();
    Menu fileMenu = new Menu("File");
    MenuItem fileClose = new MenuItem("Close");
    menuBar.getMenus().add(fileMenu);
    fileMenu.getItems().add(fileClose);
    fileClose.setOnAction(new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e)
    {
        menuFileClose(e);
    }
    });
    Menu helpMenu = new Menu("Help");
    MenuItem helpAbout = new MenuItem("About");
    menuBar.getMenus().add(helpMenu);
    helpMenu.getItems().add(helpAbout);
    helpAbout.setOnAction(new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e)
    {
        menuHelpAbout(e);
    }
    });
    // helpAbout.addEventHandler(null, controller);
    appPane.setTop(menuBar);
    gamePane = new Pane();
    gamePane.setMinSize(200, 200);
    appPane.setCenter(gamePane);
    speedSlider = new Slider();
    appPane.setBottom(speedSlider);
    speedSlider.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<Event>() {
    public void handle(Event e)
    {
        speedChanged(e);
    }
    });
}

// This is where we can actually do some "constructor" work when using FXML
public void initialize()
{
    // System.out.println("BCI: FXApp:" + Bounce.app + " scene:" + Bounce.app.scene
    // +
    // " pane:" + Bounce.app.pane);
    // System.out.println("BCI: slider:"+speedSlider+ " gpane:"+gamePane+ "
    // circle:"+redBall);
    //
    // I have the red ball circle in my FXML but for some reason it is not
    // resolving, so I explicitly look it up here (and this works). I think it
    // is because it is nested in my gamePane, and maybe I need to do something
    // different in my FXML to get it to auto-resolve.
    // redBall = (Circle) gamePane.lookup("#redBall");
    redBall = new Circle(20, 30, 45);
    gamePane.getChildren().add(redBall);
    // make the boxes
    makeBoxes();
    // The start line below is for the AnimationTimer part of this class;
    // we just let it run for the duration of the application
    this.start();
}

// Make a number of bouncing rectangles
public void makeBoxes()
{
    IntStream randStream = (new Random()).ints(0, 1000001);
    PrimitiveIterator.OfInt randInts = randStream.iterator();
    boxes = new ArrayList<FlyingBox>();
    for (int i = 0; i < 30; i++) {
        boxes.add(new FlyingBox(gamePane, randInts.next() % 200 + 20, randInts.next() % 200 + 20,
                randInts.next() % 40 + 2, randInts.next() % 40 + 2, randInts.next()));
    }
    gamePane.getChildren().addAll(boxes);
}

// Animation data for timer
private long moveRate = 10000000; // animation timer is in nanoseconds!
private long prevTime = 0;
// animation data for red ball
private int direction = 2;
private int dirRate = 2;
private int posAdjust;

// This handle() method is where the timer is used, this is called every
// time JavaFX thinks we should update the animation -- i.e., the view on
// our application. We do not control how often or how fast this happens,
// JavaFX just adapts it to whatever hardware or platform we are running on
@Override
public void handle(long now)
{
    if (prevTime == 0) {
        // just set starting time on first call and skip animation
        prevTime = now;
        return;
    }
    // calculate time since last call
    long elapsed = now - prevTime;
    if (elapsed < moveRate) // don't do anything, just wait for more time
        return;
    // take out of elapsed time as many moveRates as possible
    int numRates = (int) (elapsed / moveRate); // integer division
    if (redBall.getCenterX() + redBall.getRadius() > gamePane.getWidth())
        direction = -dirRate;
    if (redBall.getCenterX() + redBall.getRadius() < 0)
        direction = dirRate;
    posAdjust = direction * numRates;
    prevTime += moveRate * numRates;
    // best practice is to update any viewable thing by letting Java/JavaFX
    // to decide to fit it into the proper thread, with Platform.runLater()
    // - the easiest syntax to use is a "lambda expression", as below
    Platform.runLater(() -> {
        redBall.setCenterX(redBall.getCenterX() + posAdjust);
    });
    // tell the bouncing rectangles to update their own positions
    for (FlyingBox box : boxes) {
        box.move();
    }
    return;
}

// This is our handler for when the slider is moved
public void speedChanged(Event e)
{
    System.out.println("Speed changed! (" + e + ") " + redBall);
    dirRate = (int) speedSlider.getValue();
    if (direction < 0)
        direction = -dirRate;
    else
        direction = dirRate;
}

// Handler for menu File->Close selection
public void menuFileClose(Event e)
{
    System.out.println("File->Close! (" + e + ")");
    javafx.application.Platform.exit();
}

// Handler for menu Help->About selection
public void menuHelpAbout(Event e)
{
    System.out.println("Help->About! (" + e + ")");
    System.out.println("Controller:" + this);
}

} // end class
