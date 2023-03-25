package imagecontroller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import model.ImageModel;
import pixels.Pixels;

/**
 * A ppm image controller that can do simple operations for the image.
 */
public class PPMImageController implements ImageController {

  private final ImageModel<Pixels> model;
  private final Image ioDelegate;

  /**
   * A simple constructor with one parameter.
   *
   * @param model the input model object.
   */
  public PPMImageController(ImageModel<Pixels> model) {
    this.model = model;
    this.ioDelegate = new PPMImage();
  }

  @Override
  public void accepctCommandsInFile(String path) {
    Scanner scan = new Scanner(path);

    File file = new File(scan.next());

    BufferedReader reader = null;
    StringBuffer sbf = new StringBuffer();
    try {
      reader = new BufferedReader(new FileReader(file));
      String tempStr;
      while ((tempStr = reader.readLine()) != null) {
        sbf.append(tempStr);
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    acceptCommandsString(sbf.toString());
  }

  @Override
  public void acceptCommands(Readable command) {
    Scanner scan = new Scanner(command);

    acceptCommandsString(scan.nextLine());
  }

  private void acceptCommandsString(String command) {

    switch (command) {
      case "blur":
        model.blurImage();
        break;
      case "sharpening":
        model.sharpeningImage();
        break;
      case "greyscale":
        model.monochromeImage();
        break;
      case "sepia":
        model.sepiaImage();
        break;
      default:
        saveAndLoad(command);
    }
  }


  private void saveAndLoad(String command) {
    if (!command.contains(" ")) {
      throw new IllegalArgumentException("Unknown save/load command!");
    }

    String[] strings = command.split(" ");
    String str = strings[0];
    String filePath = strings[1];

    if (str.equals("save")) {
      ioDelegate.write(filePath, model.getPixels());
    } else if (str.equals("load")) {
      model.setImage(ioDelegate.read(filePath));
    }
  }
}
