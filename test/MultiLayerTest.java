import static org.junit.Assert.assertEquals;

import imagecontroller.ImageController;
import imagecontroller.MultiLayerController;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;
import view.PictureProcessingView;
import view.SimpleView;

/**
 * The test for multi layer.
 */
public class MultiLayerTest {

  PictureProcessingView view = new SimpleView();
  Readable rd;
  ImageController controller;

  @Before
  public void initial() {
    controller = new MultiLayerController(view);
  }


  @Test
  public void testLoadCommandsInTXT() {
    controller.accepctCommandsInFile("res/MultiLayerPackage/CommandsExample1.txt");
    assertEquals("Ready to read commands from file.\n"
        + "Create layer successfully, layer name: first\n"
        + "Create layer successfully, layer name: second\n"
        + "The current layer is now: first\n"
        + "Load the image res/a.jpgat current layer.\n"
        + "Successfully blur\n"
        + "Successfully save the current layer. In path: windows.ppm.\n"
        + "The current layer is now: second\n"
        + "Load the image res/b.jpgat current layer.\n"
        + "Successfully sepia\n"
        + "Successfully save the current layer. In path: Sepia.ppm.\n"
        + "Changed second to Invisible\n"
        + "Successfully greyscale\n"
        + "Successfully save the current layer. In path: aGreyScale.jpg.\n"
        + "----------------------------\n"
        + "second(invisible) occupy\n"
        + "first(visible) occupy\n"
        + "----------------------------\n",view.peekOutput().toString());
  }

  @Test
  public void testCreateLayer() {
    rd = new StringReader("create layer first\n" +
        "create layer second\n" +
        "create layer third\n" +
        "create layer fourth\n" +
        "create layer fifth\n");

    controller.acceptCommands(rd);
    assertEquals("Create layer successfully, layer name: first\n" +
        "Create layer successfully, layer name: second\n" +
        "Create layer successfully, layer name: third\n" +
        "Create layer successfully, layer name: fourth\n" +
        "Create layer successfully, layer name: fifth\n" +
        "----------------------------\n" +
        "fifth(invisible) empty\n" +
        "fourth(invisible) empty\n" +
        "third(invisible) empty\n" +
        "second(invisible) empty\n" +
        "first(invisible) empty\n" +
        "----------------------------\n", view.peekOutput().toString());
  }

  @Test
  public void testCreateLayerNoName() {
    rd = new StringReader("aaa\n");

    controller.acceptCommands(rd);
    assertEquals("Unknown command. Please input again.\n" +
        "Layers not imported\n", view.peekOutput().toString());
  }

  @Test
  public void testCreateLayerDuplicateName() {
    rd = new StringReader("create layer first\n" +
        "create layer first\n");

    controller.acceptCommands(rd);
    assertEquals("Create layer successfully, layer name: first\n" +
        "Name not found\n" +
        "----------------------------\n" +
        "first(invisible) empty\n" +
        "----------------------------\n", view.peekOutput().toString());
  }

  @Test
  public void testDeleteLayer() {
    rd = new StringReader("create layer first\n" +
        "create layer second\n" +
        "create layer third\n" +
        "delete layer second\n");
    controller.acceptCommands(rd);

    assertEquals("Create layer successfully, layer name: first\n"
        + "Create layer successfully, layer name: second\n"
        + "Create layer successfully, layer name: third\n"
        + "No Image Visible\n"
        + "----------------------------\n"
        + "third(invisible) empty\n"
        + "first(invisible) empty\n"
        + "----------------------------\n", view.peekOutput().toString());
  }

  @Test
  public void testDeleteLayerUnknownName() {
    rd = new StringReader("create layer first\n" +
        "delete layer second\n");
    controller.acceptCommands(rd);

    assertEquals("Create layer successfully, layer name: first\n" +
        "Layer not found.\n" +
        "----------------------------\n" +
        "first(invisible) empty\n" +
        "----------------------------\n", view.peekOutput().toString());
  }

  @Test
  public void testInvisibleResetTopMostLayer() {
    rd = new StringReader("create layer first\n" +
        "create layer second\n" +
        "current first\n" +
        "load res/a.jpg\n" +
        "blur\n" +
        "save windos.jpg\n"
        + "current second\n"
        + "load res/b.jpg\n"
        + "sepia\n"
        + "save bSepia.ppm\n"
        + "invisible second\n"
        + "greyscale\n"
        + "save bGreyScale.jpg");
    controller.acceptCommands(rd);
    assertEquals("Create layer successfully, layer name: first\n"
        + "Create layer successfully, layer name: second\n"
        + "The current layer is now: first\n"
        + "Load the image res/a.jpgat current layer.\n"
        + "Successfully blur\n"
        + "Successfully save the current layer. In path: windos.jpg.\n"
        + "The current layer is now: second\n"
        + "Load the image res/b.jpgat current layer.\n"
        + "Successfully sepia\n"
        + "Successfully save the current layer. In path: bSepia.ppm.\n"
        + "Changed second to Invisible\n"
        + "Successfully greyscale\n"
        + "Successfully save the current layer. In path: bGreyScale.jpg.\n"
        + "----------------------------\n"
        + "second(invisible) occupy\n"
        + "first(visible) occupy\n"
        + "----------------------------\n", view.peekOutput().toString());

  }

  @Test
  public void testSaveAllAndLoadAllFromFile() {
    ImageController controller2 = new MultiLayerController(view);
    rd = new StringReader("create layer first\n" +
        "create layer second\n" +
        "current first\n" +
        "load res/a.jpg\n"
        + "current second\n"
        + "load res/b.jpg\n"
        + "saveAll");
    controller.acceptCommands(rd);
    rd = new StringReader("loadAll res/MultiLayerPackage/Layers.txt\n"
        + "current second\n"
        + "greyscale\n"
        + "save ThisIsFirst.jpg");
    controller2.acceptCommands(rd);
    assertEquals("Create layer successfully, layer name: first\n"
        + "Create layer successfully, layer name: second\n"
        + "The current layer is now: first\n"
        + "Load the image res/a.jpgat current layer.\n"
        + "The current layer is now: second\n"
        + "Load the image res/b.jpgat current layer.\n"
        + "The current layer is now: second\n"
        + "Successfully save the current layer. In path: second.ppm.\n"
        + "The current layer is now: first\n"
        + "Successfully save the current layer. In path: first.ppm.\n"
        + "----------------------------\n"
        + "second(visible) occupy\n"
        + "first(visible) occupy\n"
        + "----------------------------\n"
        + "Save all the layers successfully. \n"
        + "----------------------------\n"
        + "second(visible) occupy\n"
        + "first(visible) occupy\n"
        + "----------------------------\n"
        + "Create layer successfully, layer name: second\n"
        + "The current layer is now: second\n"
        + "Load the image res/MultiLayerPackage/second.jpgat current layer.\n"
        + "----------------------------\n"
        + "second(visible) occupy\n"
        + "----------------------------\n"
        + "Create layer successfully, layer name: first\n"
        + "The current layer is now: first\n"
        + "Load the image res/MultiLayerPackage/first.jpgat current layer.\n"
        + "----------------------------\n"
        + "first(visible) occupy\n"
        + "second(visible) occupy\n"
        + "----------------------------\n"
        + "Successfully load all the image layers.The current layer is now: second\n"
        + "Successfully greyscale\n"
        + "Successfully save the current layer. In path: ThisIsFirst.jpg.\n"
        + "----------------------------\n"
        + "first(visible) occupy\n"
        + "second(visible) occupy\n"
        + "----------------------------\n", view.peekOutput().toString());
  }

  @Test
  public void testControllerReadingTXTCommand() {
    controller.accepctCommandsInFile("res/MultiLayerPackage/CommandsExample1.txt");
    assertEquals("Ready to read commands from file.\n"
        + "Create layer successfully, layer name: first\n"
        + "Create layer successfully, layer name: second\n"
        + "The current layer is now: first\n"
        + "Load the image res/a.jpgat current layer.\n"
        + "Successfully blur\n"
        + "Successfully save the current layer. In path: windows.ppm.\n"
        + "The current layer is now: second\n"
        + "Load the image res/b.jpgat current layer.\n"
        + "Successfully sepia\n"
        + "Successfully save the current layer. In path: Sepia.ppm.\n"
        + "Changed second to Invisible\n"
        + "Successfully greyscale\n"
        + "Successfully save the current layer. In path: aGreyScale.jpg.\n"
        + "----------------------------\n"
        + "second(invisible) occupy\n"
        + "first(visible) occupy\n"
        + "----------------------------\n", view.peekOutput().toString());
  }

  @Test
  public void testBlurNotExist() {
    rd = new StringReader("create layer first\n" +
        "create layer second\n"
        + "current first\n"
        + "blur\n"
        + "load res/a.jpg\n"
        + "blur");
    controller.acceptCommands(rd);

    assertEquals("Create layer successfully, layer name: first\n"
        + "Create layer successfully, layer name: second\n"
        + "The current layer is now: first\n"
        + "Nothing here\n"
        + "Load the image res/a.jpgat current layer.\n"
        + "Successfully blur\n"
        + "----------------------------\n"
        + "second(invisible) empty\n"
        + "first(visible) occupy\n"
        + "----------------------------\n", view.peekOutput().toString());
  }

  @Test
  public void testBigEnoughLayer() {
    rd = new StringReader("create layer first\n" +
        "create layer second\n" +
        "create layer third\n" +
        "create layer fourth\n" +
        "create layer fifth\n" + "current first\n" +
        "load res/a.jpg\n" +
        "blur\n" +
        "save windows.jpg\n"
        + "current second\n"
        + "load res/b.jpg\n"
        + "sepia\n"
        + "save bSepia.ppm\n"
        + "invisible second\n"
        + "greyscale\n"
        + "save bGreyScale.jpg\n"
        + "visible second\n"
        + "save second.ppm\n"
        + "current fourth\n"
        + "load res/b.jpg\n"
        + "sharpening\n"
        + "load res/CheckerBoard.ppm\n"
        + "remove fifth\n"
        + "delete layer fifth\n"
        + "sepia\n"
        + "saveAll res/MultiLayerPackage");
    controller.acceptCommands(rd);

    assertEquals("Create layer successfully, layer name: first\n"
        + "Create layer successfully, layer name: second\n"
        + "Create layer successfully, layer name: third\n"
        + "Create layer successfully, layer name: fourth\n"
        + "Create layer successfully, layer name: fifth\n"
        + "The current layer is now: first\n"
        + "Load the image res/a.jpgat current layer.\n"
        + "Successfully blur\n"
        + "Successfully save the current layer. In path: windows.jpg.\n"
        + "The current layer is now: second\n"
        + "Load the image res/b.jpgat current layer.\n"
        + "Successfully sepia\n"
        + "Successfully save the current layer. In path: bSepia.ppm.\n"
        + "Changed second to Invisible\n"
        + "Successfully greyscale\n"
        + "Successfully save the current layer. In path: bGreyScale.jpg.\n"
        + "Changed second to Visible\n"
        + "Successfully save the current layer. In path: second.ppm.\n"
        + "The current layer is now: fourth\n"
        + "Load the image res/b.jpgat current layer.\n"
        + "Successfully sharpening\n"
        + "Load the image res/CheckerBoard.ppmat current layer.\n"
        + "Unknown command. Please input again.\n"
        + "Delete layer successfully, layer name: fifth\n"
        + "Successfully sepia\n"
        + "Unknown command. Please input again.\n"
        + "----------------------------\n"
        + "fourth(visible) occupy\n"
        + "third(invisible) empty\n"
        + "second(visible) occupy\n"
        + "first(visible) occupy\n"
        + "----------------------------\n", view.peekOutput().toString());
  }

  @Test
  public void testCrossImageIO() {
    rd = new StringReader("create layer first\n" +
        "create layer second\n" +
        "create layer third\n" +
        "create layer fourth\n" +
        "create layer fifth\n" + "current first\n" +
        "load res/a.jpg\n" +
        "blur\n" +
        "save windows.jpg\n"
        + "current second\n"
        + "load res/b.jpg\n"
        + "sepia\n"
        + "save bSepia.png"
        + "current fourth\n"
        + "load res/d.png\n"
        + "save d.ppm\n"
        + "load d.ppm\n"
        + "save d.jpeg\n");
    controller.acceptCommands(rd);

    assertEquals("Create layer successfully, layer name: first\n"
        + "Create layer successfully, layer name: second\n"
        + "Create layer successfully, layer name: third\n"
        + "Create layer successfully, layer name: fourth\n"
        + "Create layer successfully, layer name: fifth\n"
        + "The current layer is now: first\n"
        + "Load the image res/a.jpgat current layer.\n"
        + "Successfully blur\n"
        + "Successfully save the current layer. In path: windows.jpg.\n"
        + "The current layer is now: second\n"
        + "Load the image res/b.jpgat current layer.\n"
        + "Successfully sepia\n"
        + "Successfully save the current layer. In path: bSepia.pngcurrent fourth.\n"
        + "Load the image res/d.pngat current layer.\n"
        + "Successfully save the current layer. In path: d.ppm.\n"
        + "Load the image d.ppmat current layer.\n"
        + "Successfully save the current layer. In path: d.jpeg.\n"
        + "----------------------------\n"
        + "fifth(invisible) empty\n"
        + "fourth(invisible) empty\n"
        + "third(invisible) empty\n"
        + "second(visible) occupy\n"
        + "first(visible) occupy\n"
        + "----------------------------\n", view.peekOutput().toString());
  }
}
