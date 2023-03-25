package imagecontroller;

import java.io.InputStreamReader;
import java.util.Scanner;
import view.PictureProcessingView;
import view.SimpleView;

/**
 * The driver class for running jar file.
 */
public class Driver {

   //The main class invoked by cmd.
  public static void main(String[] args) {
    System.out.println("Please choose the way you want to operates the image:\n" +
        "1. interactive\n" +
        "2. text\n" +
        "3. script\n" +
        "Press the number 1-3 to choose.");

    Scanner input = new Scanner(System.in);

    String get = input.next();

    if (get.equals("1")) {
      GUIController gui = new GUIController();
    }
    if (get.equals("2")) {
      System.out.println("You choose to operate the program in text.");
      PictureProcessingView view = new SimpleView(System.out);
      Readable in = new InputStreamReader(System.in);
      ImageController controller = new MultiLayerController(view);
      controller.acceptCommands(in);
    }
    if (get.equals("3")) {
      System.out.println("please input the text path.");
      String path = input.next();
      PictureProcessingView view = new SimpleView(System.out);
      ImageController controller = new MultiLayerController(view);
      controller.accepctCommandsInFile(path);
    }
  }
}
