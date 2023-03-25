import imagecontroller.ImageController;
import imagecontroller.PPMImageController;
import java.io.StringReader;
import model.PPMImageModel;

/**
 * Holds the main method.
 */
public class MainMethod {

  /**
   * Main method for play.
   */
  public static void main(String[] args) {
    ImageController image = new PPMImageController(new PPMImageModel());
    image.acceptCommands(new StringReader("load res/Input.ppm"));
    image.acceptCommands(new StringReader("save res/Output.ppm"));
  }
}
