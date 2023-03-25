package imagecontroller;

import imagelayer.ImageLayer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import model.Layers;
import model.MultiLayerImageModel;
import pixels.Pixels;
import view.ImageGuiView;
import view.PictureProcessingView;
import view.SimpleView;

/**
 * A multi-layer controller that can not only operates single image but also operates several images
 * in different layers.
 */
public class MultiLayerController implements ImageController {

  private Layers model;
  private ImageIOImage ioDelegate;
  private PictureProcessingView view;

  /**
   * A convinient constructor.
   */
  public MultiLayerController() {
    this.model = new MultiLayerImageModel();
    this.ioDelegate = new ImageIOImage();
    this.view = new SimpleView();
  }

  /**
   * A constructor with an input view object.
   *
   * @param view the input view object.
   */
  public MultiLayerController(PictureProcessingView view) {
    this.model = new MultiLayerImageModel();
    this.ioDelegate = new ImageIOImage();
    this.view = view;
  }

  public MultiLayerController(Layers model, ImageGuiView view) {
    this.model = model;
    this.ioDelegate = new ImageIOImage();
    this.view = view;
  }

  /**
   * The main method for jar file.
   * @param args user input
   */
  public static void main(String[] args) {
    PictureProcessingView view = new SimpleView(System.out);
    Readable in = new InputStreamReader(System.in);
    ImageController controller = new MultiLayerController(view);
    controller.acceptCommands(in);

  }

  @Override
  public void accepctCommandsInFile(String path) {
    view.renderMessage("Ready to read commands from file.\n");

    Scanner scan = new Scanner(path);

    acceptCommands(new StringReader(readFile(scan.nextLine())));

  }

  @Override
  public void acceptCommands(Readable commands) {

    Scanner scan = new Scanner(commands);

    while (scan.hasNextLine()) {
      try {
        commandProgress(scan.nextLine());
      } catch (IllegalArgumentException | IllegalStateException e) {
        view.renderMessage(e.getMessage() + "\n");
      }
    }

    try {
      view.renderLayer(model.getLayerList(), model.exportAll());
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderMessage(e.getMessage() + "\n");
    }
  }

  private void commandProgress(String command) {
    String save = view.peekOutput().toString();

    if (command.contains("#")) {
      command = command.substring(0, command.indexOf("#") - 1);

      while (command.charAt(command.length() - 1) == ' ') {
        command = command.substring(0, command.length() - 1);
      }
    }

    if (command.length() > 12) {
      if (command.startsWith("create layer")) {

        model.createLayer(command.substring(13));

        view.renderMessage("Create layer successfully, layer name: "
            + command.substring(13) + "\n");
        return;
      } else if (command.startsWith("delete layer")) {
        model.deleteLayer(command.substring(13));

        view.renderMessage("Delete layer successfully, layer name: "
            + command.substring(13) + "\n");
        return;
      }
    }

    if (command.length() > 7) {
      if (command.substring(0, 7).equals("current")) {
        String current = command.substring(8);
        model.setCurrent(current);

        view.renderMessage("The current layer is now: " + current + "\n");
        return;
      }
    }

    boolean tof = saveAndLoad(command)
        && imageProcessing(command) && setVisibility(command);

    if (tof) {
      throw new IllegalArgumentException("Unknown command. Please input again.");
    }
  }

  private boolean saveAndLoad(String command) {
    if (!command.contains(" ")) {
      if (command.equals("saveAll")) {
        saveAll();
        view.renderMessage("Save all the layers successfully. \n");
        return false;
      } else {
        return true;
      }
    }

    String[] strings = command.split(" ");
    String str = strings[0];
    String filePath = strings[1];

    if(strings.length>2){
      filePath+=" "+strings[2];
    }
    switch (str) {
      case "save":
        Pixels[][] image = model.getImage(model.getCurrent());
        ioDelegate.write(filePath, model.getImage(model.getCurrent()));

        view.renderMessage("Successfully save the current layer" +
            ". In path: " + filePath + ".\n");
        return false;
      case "load":
        model.loadImage(model.getCurrent(), ioDelegate.read(filePath));

        view.renderMessage("Load the image " + filePath + "at current layer.\n");
        return false;
      case "loadAll":
        loadAll(filePath);

        view.renderMessage("Successfully load all the image layers.");
        return false;
      default:
        return true;
    }
  }

  private void saveAll() {
    List<String> layers = model.getLayerList();
    Map<String, ImageLayer> exports = model.exportAll();
    String commands = "";
    String txtFile = "";

    for(String str:layers){
      commands = commands + "current " + str + "\n";
      commands = commands + "save " + str + ".ppm" + "\n";
      txtFile = txtFile + str + "\n";
      ImageLayer imageLayer = exports.get(str);
      ioDelegate.write("res/MultiLayerPackage/" + str + ".jpg", imageLayer.peekImage());
    }

    writeTxtFile(txtFile, "res/MultiLayerPackage/Layers.txt");
    acceptCommands(new StringReader(commands));
  }

  private void loadAll(String path) {
    String[] strings = readFile(path).toString().split("\n");
    for (String s : strings) {
      acceptCommands(new StringReader("create layer " + s + "\n"
          + "current " + s + "\n"
          + "load " + "res/MultiLayerPackage/" + s + ".jpg"));
    }
  }

  private boolean imageProcessing(String command) {
    switch (command) {
      case "blur":
        model.blurImage();
        view.renderMessage("Successfully " + command + "\n");
        return false;
      case "sharpening":
        model.sharpeningImage();
        view.renderMessage("Successfully " + command + "\n");
        return false;
      case "greyscale":
        model.monochromeImage();
        view.renderMessage("Successfully " + command + "\n");
        return false;
      case "sepia":
        model.sepiaImage();
        view.renderMessage("Successfully " + command + "\n");
        return false;
      default:
        return true;
    }

  }

  private boolean setVisibility(String command) {
    String[] strings = command.split(" ");
    switch (strings[0]) {
      case ("visible"):
        model.setVisibility(strings[1], true);
        view.renderMessage("Changed " + strings[1] + " to Visible" + "\n");
        return false;
      case ("invisible"):
        model.setVisibility(strings[1], false);
        view.renderMessage("Changed " + strings[1] + " to Invisible" + "\n");
        return false;
      default:
        return true;
    }
  }

  private String readFile(String path) {
    File file;
    Scanner scan = new Scanner(path);

    try {
      file = new File(scan.next());
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("The path should not be null!");
    }
    BufferedReader reader = null;
    StringBuffer sbf = new StringBuffer();
    try {
      reader = new BufferedReader(new FileReader(file));
      String tempStr;
      while ((tempStr = reader.readLine()) != null) {
        sbf.append(tempStr + "\n");
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return sbf.toString();
  }

  private void writeTxtFile(String content, String path) {
    try {
      File file = new File(path);
      file.createNewFile();
      BufferedWriter bw = new BufferedWriter(new FileWriter(path));
      bw.write(content);
      bw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
