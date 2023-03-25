import static org.junit.Assert.assertEquals;

import imagecontroller.GUIController;
import imagelayer.ImageLayer;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import view.Features;
import view.ImageGuiView;

public class GUITest {

  private Features controller;
  private fakeGui gui;

  @Before
  public void initCond() {
    gui = new fakeGui();
    controller = new GUIController(gui);
  }

  @Test
  public void testCreateLayer() {
    gui.fireCreate("First");
    assertEquals("GUI is Visible\n"
        + "Message rendered: Create layer successfully, layer name: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: The current layer is now: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: Current Layer: First is empty, waiting for import.\n"
        + "Image Rendered\n", gui.peekOutput().toString());
  }

  @Test
  public void testLoadLayer() {
    gui.fireCreate("First");
    gui.fireload("res/a.jpg");
    assertEquals("GUI is Visible\n"
        + "Message rendered: Create layer successfully, layer name: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: The current layer is now: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: Current Layer: First is empty, waiting for import.\n"
        + "Image Rendered\n"
        + "Message rendered: Load the image res/a.jpgat current layer.\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n", gui.peekOutput().toString());
  }

  @Test
  public void testBlur() {
    gui.fireCreate("First");
    gui.fireload("res/a.jpg");
    gui.fireBlur();
    assertEquals("GUI is Visible\n"
        + "Message rendered: Create layer successfully, layer name: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: The current layer is now: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: Current Layer: First is empty, waiting for import.\n"
        + "Image Rendered\n"
        + "Message rendered: Load the image res/a.jpgat current layer.\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n"
        + "Message rendered: Successfully blur\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n", gui.peekOutput().toString());
  }

  @Test
  public void testSharpening() {
    gui.fireCreate("First");
    gui.fireload("res/a.jpg");
    gui.fireSharpening();
    assertEquals("GUI is Visible\n"
        + "Message rendered: Create layer successfully, layer name: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: The current layer is now: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: Current Layer: First is empty, waiting for import.\n"
        + "Image Rendered\n"
        + "Message rendered: Load the image res/a.jpgat current layer.\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n"
        + "Message rendered: Successfully sharpening\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n", gui.peekOutput().toString());
  }

  @Test
  public void testSepia() {
    gui.fireCreate("First");
    gui.fireload("res/a.jpg");
    gui.fireSepia();
    assertEquals("GUI is Visible\n"
        + "Message rendered: Create layer successfully, layer name: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: The current layer is now: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: Current Layer: First is empty, waiting for import.\n"
        + "Image Rendered\n"
        + "Message rendered: Load the image res/a.jpgat current layer.\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n"
        + "Message rendered: Successfully sepia\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n", gui.peekOutput().toString());
  }

  @Test
  public void testGreyScale() {
    gui.fireCreate("First");
    gui.fireload("res/a.jpg");
    gui.fireGreyScale();
    assertEquals("GUI is Visible\n"
        + "Message rendered: Create layer successfully, layer name: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: The current layer is now: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: Current Layer: First is empty, waiting for import.\n"
        + "Image Rendered\n"
        + "Message rendered: Load the image res/a.jpgat current layer.\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n"
        + "Message rendered: Successfully greyscale\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n", gui.peekOutput().toString());
  }

  @Test
  public void testMosaic() {
    gui.fireCreate("First");
    gui.fireload("res/a.jpg");
    gui.fireMosaic("1000");
    assertEquals("GUI is Visible\n"
        + "Message rendered: Create layer successfully, layer name: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: The current layer is now: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: Current Layer: First is empty, waiting for import.\n"
        + "Image Rendered\n"
        + "Message rendered: Load the image res/a.jpgat current layer.\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n"
        + "Message rendered: Successfully mosaic with 1000 seeds\n"
        + "Image Rendered\n"
        + "GUI refreshed\n", gui.peekOutput().toString());
  }

  @Test
  public void testdownScaling() {
    gui.fireCreate("First");
    gui.fireload("res/a.jpg");
    gui.firedownScaling("100", "100");
    assertEquals("GUI is Visible\n"
        + "Message rendered: Create layer successfully, layer name: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: The current layer is now: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: Current Layer: First is empty, waiting for import.\n"
        + "Image Rendered\n"
        + "Message rendered: Load the image res/a.jpgat current layer.\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n"
        + "Message rendered: Successfully downscaling to100 100\n"
        + "Image Rendered\n"
        + "GUI refreshed\n", gui.peekOutput().toString());
  }

  @Test
  public void testSaveAll() {
    gui.fireCreate("Layer");
    gui.fireload("res/a.jpg");
    gui.fireSaveAll();
    assertEquals("GUI is Visible\n"
            + "Message rendered: Create layer successfully, layer name: Layer\n"
            + "\n"
            + "Layer rendered\n"
            + "Message rendered: The current layer is now: Layer\n"
            + "\n"
            + "Layer rendered\n"
            + "Message rendered: Current Layer: Layer is empty, waiting for import.\n"
            + "Image Rendered\n"
            + "Message rendered: Load the image res/a.jpgat current layer.\n"
            + "\n"
            + "Layer rendered\n"
            + "Image Rendered\n"
            + "GUI refreshed\n"
            + "Message rendered: The current layer is now: Layer\n"
            + "\n"
            + "Message rendered: Successfully save the current layer. In path: Layer.ppm.\n"
            + "\n"
            + "Layer rendered\n"
            + "Message rendered: Save all the layers successfully. \n"
            + "\n"
            + "Layer rendered\n"
            + "Labels cleared\n"
            + "Message rendered: Save all the layers successfully in res/MultiLayerPackage/Layers.txt.\n",
        gui.peekOutput().toString());
  }

  @Test
  public void testLoadAll() {
    gui.fireLoadAll();
    assertEquals("GUI is Visible\n"
        + "Message rendered: Ready to read commands from file.\n"
        + "\n"
        + "Message rendered: Create layer successfully, layer name: first\n"
        + "\n"
        + "Message rendered: Create layer successfully, layer name: second\n"
        + "\n"
        + "Message rendered: The current layer is now: first\n"
        + "\n"
        + "Message rendered: Load the image res/a.jpgat current layer.\n"
        + "\n"
        + "Message rendered: Successfully blur\n"
        + "\n"
        + "Message rendered: Successfully save the current layer. In path: windows.ppm.\n"
        + "\n"
        + "Message rendered: The current layer is now: second\n"
        + "\n"
        + "Message rendered: Load the image res/b.jpgat current layer.\n"
        + "\n"
        + "Message rendered: Successfully sepia\n"
        + "\n"
        + "Message rendered: Successfully save the current layer. In path: Sepia.ppm.\n"
        + "\n"
        + "Message rendered: Changed second to Invisible\n"
        + "\n"
        + "Message rendered: Successfully greyscale\n"
        + "\n"
        + "Message rendered: Successfully save the current layer. In path: aGreyScale.jpg.\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n", gui.peekOutput().toString());
  }

  @Test
  public void testInvisibleandVisible() {
    gui.fireCreate("First");
    gui.fireload("res/a.jpg");
    gui.fireVisibleAndInvisible("First");
    assertEquals("GUI is Visible\n"
        + "Message rendered: Create layer successfully, layer name: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: The current layer is now: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: Current Layer: First is empty, waiting for import.\n"
        + "Image Rendered\n"
        + "Message rendered: Load the image res/a.jpgat current layer.\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n"
        + "Message rendered: No Image Visible\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "Image Rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n"
        + "Message rendered: Changed First to Visible\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n", gui.peekOutput().toString());
  }

  @Test
  public void testDelete() {
    gui.fireCreate("First");
    gui.fireload("res/a.jpg");
    gui.fireDelete("First");
    assertEquals("GUI is Visible\n"
        + "Message rendered: Create layer successfully, layer name: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: The current layer is now: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: Current Layer: First is empty, waiting for import.\n"
        + "Image Rendered\n"
        + "Message rendered: Load the image res/a.jpgat current layer.\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n"
        + "Message rendered: Layers not imported\n"
        + "\n"
        + "Message rendered: Layers not imported\n"
        + "\n"
        + "Labels cleared\n", gui.peekOutput().toString());
  }

  @Test
  public void testSave() {
    gui.fireCreate("First");
    gui.fireload("res/a.jpg");
    gui.fireSave("res", ".jpg");
    assertEquals("GUI is Visible\n"
        + "Message rendered: Create layer successfully, layer name: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: The current layer is now: First\n"
        + "\n"
        + "Layer rendered\n"
        + "Message rendered: Current Layer: First is empty, waiting for import.\n"
        + "Image Rendered\n"
        + "Message rendered: Load the image res/a.jpgat current layer.\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n"
        + "Message rendered: Successfully save the current layer. In path: res/First.jpg.\n"
        + "\n"
        + "Layer rendered\n"
        + "Image Rendered\n"
        + "GUI refreshed\n", gui.peekOutput().toString());
  }

  private static class fakeGui implements ImageGuiView {

    private Features features;
    private StringBuilder log = new StringBuilder();

    private void fireBlur() {
      features.blur();
    }

    private void fireSharpening() {
      features.sharpening();
    }

    private void fireSepia() {
      features.sepia();
    }

    private void fireGreyScale() {
      features.greyScale();
    }

    private void fireMosaic(String seeds) {
      features.mosaic(seeds);
    }

    private void firedownScaling(String width, String height) {
      features.downScaling(width, height);
    }

    private void fireload(String path) {
      features.load(path);
    }

    private void fireSave(String path, String format) {
      features.save(path, format);
    }

    private void fireSaveAll() {
      features.saveAll();

    }

    private void fireLoadAll() {
      features.loadAll("res/MultiLayerPackage/CommandsExample1.txt");
    }

    private void fireDelete(String name) {
      features.delete(name);
    }

    private void fireCreate(String name) {
      features.create(name);
    }

    private void fireVisibleAndInvisible(String name) {
      features.invisible(name);
      features.visible(name);
    }

    @Override
    public void addFeatures(Features features) {
      this.features = features;
    }

    @Override
    public void renderImage(BufferedImage image) {
      log.append("Image Rendered\n");
    }

    @Override
    public void refresh() {
      log.append("GUI refreshed\n");
    }

    @Override
    public void setVisible() {
      log.append("GUI is Visible\n");
    }

    @Override
    public void clear() {
      log.append("Labels cleared\n");
    }

    @Override
    public void renderWarning(String warn) {
      log.append("Warning rendered: " + warn + "\n");
    }

    @Override
    public void renderLayer(List<String> layers, Map<String, ImageLayer> data) {
      log.append("Layer rendered\n");
    }

    @Override
    public void renderMessage(String message) {
      log.append("Message rendered: " + message + "\n");
    }

    @Override
    public Appendable peekOutput() {
      return this.log;
    }
  }
}
