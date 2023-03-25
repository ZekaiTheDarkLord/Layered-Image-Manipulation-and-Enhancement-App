package model;

import imagelayer.ImageLayer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import pixels.Pixels;

/**
 * The Model specially build for multi layers of image. Using LinkedHashMap in charge of sequence
 * and keep track of topmost visible by a String.
 */
public class MultiLayerImageModel extends AbstractImageModel implements Layers {


  private Map<String, ImageLayer> layers;
  private String current;

  /**
   * By default, it support popular image import and export,i.e what ImageIo library support.
   */
  public MultiLayerImageModel() {
    layers = new LinkedHashMap<>();
  }


  /**
   * Whether at least one layer have been created.
   *
   * @return true for imported layer
   */
  @Override
  public boolean isImported() {
    return !layers.isEmpty();
  }


  // In the sequence when put
  private String firstVisible() {
    goOrException();
    ListIterator<String> i = new ArrayList<String>(layers.keySet()).listIterator(layers.size());
    while (i.hasPrevious()) {
      String key = i.previous();
      if (layers.get(key) == null) {
        continue;
      }
      if (layers.get(key).getVisibility()) {
        return key;
      }
    }
    throw new IllegalStateException("No Image Visible");
  }


  @Override
  public void createLayer(String name) {
    if (layers.containsKey(name)) {
      throw new IllegalArgumentException("Name not found");
    }
    layers.put(name, null);
  }

  @Override
  public void deleteLayer(String name) {
    if (!layers.containsKey(name)) {
      throw new IllegalArgumentException("Layer not found.");
    }
    layers.remove(name);
    current = firstVisible();
  }


  @Override
  public Pixels[][] getImage(String name) {
    goOrException();
    if (!layers.containsKey(name)) {
      throw new IllegalArgumentException("Name not found");
    }
    return layers.get(name).peekImage();
  }

  @Override
  public void setName(String from, String to) {
    goOrException();
    if (current.equals(from)) {
      current = to;
    }
    if (layers.containsKey(to) && this.current.equals(from)) {
      this.current = to;
    }
    ImageLayer temp = layers.get(from);
    layers.remove(from);
    layers.put(to, temp);
  }

  @Override
  public void loadImage(String name, Pixels[][] image) {
    goOrException();
    if (!layers.containsKey(name)) {
      throw new IllegalArgumentException("Name not found");
    }
    this.setImage(image);
    layers.replace(name, new SimpleImageLayer(image));
    current = name;
  }

  @Override
  public Map<String, ImageLayer> exportAll() {

    goOrException();
    return this.layers;
  }

  @Override
  public void setVisibility(String name, boolean t) {
    goOrException();
    if (!layers.containsKey(name)) {
      throw new IllegalArgumentException("Name not found");
    }

    layers.get(name).setVisibility(t);
    current = firstVisible();
  }

  @Override
  public void clear() {
    this.current = "";
    this.layers = new LinkedHashMap<>();
  }

  @Override
  public List<String> getLayerList() {

    if (layers == null || !isImported()) {
      return new ArrayList<>(0);
    }
    ListIterator<String> i = new ArrayList<String>(layers.keySet()).listIterator(layers.size());
    List<String> keys = new ArrayList<>();
    while (i.hasPrevious()) {
      keys.add(i.previous());
    }
    return keys;
  }

  @Override
  public String getCurrent() {
    goOrException();
    return current;
  }

  @Override
  public void setCurrent(String name) {
    goOrException();
    if (name == null || !layers.containsKey(name)) {
      throw new IllegalArgumentException("Name Not found");
    }
    current = name;

  }

  @Override
  public void blurImage() {
    goOrException();
    if (layers.get(current) == null) {
      throw new IllegalArgumentException("Nothing here");
    }
    this.pixels = layers.get(current).peekImage();
    super.blurImage();
  }

  @Override
  public void sharpeningImage() {
    goOrException();
    if (layers.get(current) == null) {
      throw new IllegalArgumentException("Nothing here");
    }
    this.pixels = layers.get(current).peekImage();
    super.sharpeningImage();
  }

  @Override
  public void sepiaImage() {
    goOrException();
    if (layers.get(current) == null) {
      throw new IllegalArgumentException("Nothing here");
    }
    this.pixels = layers.get(current).peekImage();
    super.sepiaImage();
  }

  @Override
  public void monochromeImage() {
    goOrException();
    if (layers.get(current) == null) {
      throw new IllegalArgumentException("Nothing here");
    }
    this.pixels = layers.get(current).peekImage();
    super.monochromeImage();
  }

  private void goOrException() {
    if (layers == null || !isImported()) {
      throw new IllegalStateException("Layers not imported");
    }

  }


  //Inner class that only used by this model
  private class SimpleImageLayer implements ImageLayer {


    private boolean visible;
    private Pixels[][] image;


    /**
     * By default, an image is visible.
     *
     * @param image the image inside this layer.
     */
    public SimpleImageLayer(Pixels[][] image) {
      this.visible = true;
      this.image = image;
    }

    //by default, the image is empty
    public SimpleImageLayer() {
      this.visible = true;
      this.image = null;
    }


    @Override
    public void setImage(Pixels[][] image) {
      this.image = image;
    }

    @Override
    public Pixels[][] peekImage() {
      if (this.image == null) {
        throw new IllegalStateException("Please save image before peek");
      }
      return image.clone();
    }

    @Override
    public boolean getVisibility() {
      return this.visible;
    }

    @Override
    public void setVisibility(boolean t) {
      this.visible = t;
    }

  }

}
