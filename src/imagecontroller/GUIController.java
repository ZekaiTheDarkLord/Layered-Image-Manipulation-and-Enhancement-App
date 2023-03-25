package imagecontroller;

import extra.Downscaling;
import extra.ImageDownscaling;
import extra.ImageMosaicing;
import extra.Mosaicing;
import imagelayer.ImageLayer;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import model.Layers;
import model.MultiLayerImageModel;
import pixels.Pixels;
import view.Features;
import view.GUIView;
import view.ImageGuiView;

/**
 * The controller that implements the Features to support buttons in GUI and by itself is a
 * controller that connect model and manipulating on it.
 */
public class GUIController implements Features, ImageController {

  private File file;
  private Layers model;
  private MultiLayerController delegate;
  private ImageGuiView view;
  private Pixels[][] image;

  /**
   * A default GUI controller that supports
   */
  public GUIController() {
    this.model = new MultiLayerImageModel();
    this.view = new GUIView();
    this.delegate = new MultiLayerController(model, view);
    view.addFeatures(this);
    view.setVisible();
    file = new File("res/cache");
    if (!file.exists()) {
      file.mkdir();
    }
    image = null;
  }

  public GUIController(ImageGuiView view) {
    this.model = new MultiLayerImageModel();
    this.view = view;
    this.delegate = new MultiLayerController(model, view);
    view.addFeatures(this);
    view.setVisible();
    file = new File("res/cache");
    if (!file.exists()) {
      file.mkdir();
    }
    image = null;
  }

  public static void main(String[] args) {
    Layers model = new MultiLayerImageModel();
    ImageGuiView view = new GUIView();
    ImageController controller = new GUIController();

  }

  public void accepctCommandsInFile(String path) {
    this.delegate.accepctCommandsInFile(path);
  }

  public void acceptCommands(Readable commands) {
    this.delegate.acceptCommands(commands);
  }

  private void updateImage() {
    if (!emptyCurrent()) {

      image = model.getImage(model.getCurrent());
      view.renderImage(writeImageFromPixels(image));
      if (allInvisible()) {
        view.renderImage(null);
      }
      if (!model.exportAll().get(model.getCurrent()).getVisibility()) {
        view.renderImage(null);
      }

      view.refresh();
    }
  }

  private BufferedImage writeImageFromPixels(Pixels[][] image) {
    int width = image[0].length;
    int height = image.length;

    int[] data = new int[width * height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixels p = image[i][j];
        Color c = new Color(p.getR(), p.getG(), p.getB());
        data[i * width + j] = c.getRGB();
        data[i * width + j] = data[i * width + j];
      }
    }

    // 将数据写入BufferedImage
    BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    bf.setRGB(0, 0, width, height, data, 0, width);
    return bf;
  }

  private boolean emptyCurrent() {
    try {
      if (model.getLayerList() == null || model.exportAll().get(model.getCurrent()) == null) {
        view.renderMessage(
            "Current Layer: " + model.getCurrent() + " is empty, waiting for import.");
        view.renderImage(null);
        return true;
      }
    } catch (IllegalStateException e) {
      view.renderWarning("Please create a layer first!");
      return true;
    }
    return false;
  }

  private boolean allInvisible() {
    try {
      String[] layers = getLayers();
      Map<String, ImageLayer> images = model.exportAll();
      for (String str : layers) {
        if (images.get(str).getVisibility()) {
          return false;
        }
      }
      return true;
    } catch (IllegalStateException e) {
      view.renderWarning("Please create a layer first!");
      return true;
    }
  }


  @Override
  public void blur() {
    if (!emptyCurrent()) {
      acceptCommands(new StringReader("blur"));
      updateImage();
    }
  }

  @Override
  public void sharpening() {
    if (!emptyCurrent()) {
      acceptCommands(new StringReader("sharpening"));
      updateImage();
    }
  }

  @Override
  public void sepia() {
    if (!emptyCurrent()) {
      acceptCommands(new StringReader("sepia"));
      updateImage();
    }
  }

  @Override
  public void greyScale() {
    if (!emptyCurrent()) {
      acceptCommands(new StringReader("greyscale"));
      updateImage();
    }
  }

  @Override
  public void mosaic(String seeds) {
    int seed;
    try {
      if (!emptyCurrent()) {
        seed = Integer.valueOf(seeds);
        image = model.getImage(model.getCurrent());
        Mosaicing mosaic = new ImageMosaicing();
        mosaic.apply(image, seed);
        model.loadImage(model.getCurrent(), image);
        view.renderMessage("Successfully mosaic with " + seeds + " seeds");
        updateImage();
      }
    } catch (NumberFormatException e) {
      view.renderMessage("Please enter seed as number");
    } catch (IllegalStateException e) {
      view.renderMessage("Layers not created");
    }

  }

  @Override
  public void downScaling(String width, String height) {
    int w;
    int h;
    try {
      if (!emptyCurrent()) {
        w = Integer.valueOf(width);
        h = Integer.valueOf(height);
        image = model.getImage(model.getCurrent());
        String[] layers = getLayers();
        Pixels[][] temp = image;
        Downscaling downscaling = new ImageDownscaling();
        image = downscaling.apply(image, w, h);
        for (String name : layers) {
          if (sameImage(model.getImage(name), temp)) {
            model.loadImage(name, image);
          }
        }
        model.loadImage(model.getCurrent(), image);
        view.renderMessage("Successfully downscaling to" + width + " " + height);
        updateImage();
      }
    } catch (NumberFormatException e) {
      view.renderMessage("Please enter valie width and height \n"
          + "width and height cannot go over original image");
    } catch (IllegalStateException e) {
      view.renderMessage("Layers not created");
    } catch (IllegalArgumentException e) {
      view.renderMessage("The new width and height cannot exceed original image.");
    }
  }

  private boolean sameImage(Pixels[][] image1, Pixels[][] image2) {
    for (int i = 0; i < image1.length; i++) {
      for (int j = 0; j < image1[i].length; j++) {
        if (!image1[i][j].equals(image2[i][j])) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public void load(String path) {
    if (path != "") {
      acceptCommands(new StringReader("load " + path));
      updateImage();
    }
  }

  @Override
  public void save(String path, String format) {
    if (path != "" && !emptyCurrent()) {
      acceptCommands(new StringReader("save " + path + "/" + model.getCurrent() + format));
      updateImage();
    }
  }

  @Override
  public void loadAll(String path) {
    if (path != "") {
      accepctCommandsInFile(path);
      updateImage();
    }
  }

  @Override
  public void saveAll() {
    if (goOrStop()) {
      acceptCommands(new StringReader("saveAll"));
      model.clear();
      view.clear();
      view.renderMessage("Save all the layers successfully in res/MultiLayerPackage/Layers.txt.");
    }
  }

  private boolean goOrStop() {
    try {
      List<String> layers = model.getLayerList();
      Map<String, ImageLayer> exports = model.exportAll();

      for (String str : layers) {
        if (exports.get(str) == null) {
          view.renderWarning("No image found, please load/move empty layers!");
          return false;
        }
      }
      return true;
    } catch (IllegalStateException e) {
      view.renderWarning("Please create a layer first!");
      return false;
    }
  }

  //defaultly set current as this layer so that no need to choose it repeately.
  @Override
  public void create(String name) {
    if (name == null || name.equals("")) {
      view.renderMessage("Please enter valid layer name.");
      return;
    } else {
      acceptCommands(new StringReader("create layer " + name));
      acceptCommands(new StringReader("current " + name));
      updateImage();
    }

  }

  @Override
  public void delete(String name) {
    if (model.getLayerList().size() == 0) {
      view.renderWarning("Please create a layer first!");
      return;
    }
    acceptCommands(new StringReader("delete layer " + name));
    try {
      view.renderLayer(model.getLayerList(), model.exportAll());
      updateImage();
    } catch (IllegalStateException e) {
      view.clear();
    }

  }

  @Override
  public void current(String name) {
    acceptCommands(new StringReader("current " + name));
    updateImage();
  }

  @Override
  public void visible(String name) {
    if (!emptyCurrent()) {
      acceptCommands(new StringReader("visible " + name));
      updateImage();
    }
  }

  @Override
  public void invisible(String name) {
    if (!emptyCurrent()) {
      acceptCommands(new StringReader("invisible " + name));
      updateImage();

    }
  }

  @Override
  public String[] getLayers() {
    List<String> names = model.getLayerList();
    String[] layers = new String[names.size()];
    for (int i = 0; i < names.size(); i++) {
      layers[i] = names.get(i);
    }
    return layers;
  }
}
