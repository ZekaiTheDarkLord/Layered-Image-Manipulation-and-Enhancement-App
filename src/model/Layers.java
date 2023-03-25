package model;

import imagelayer.ImageLayer;
import java.util.List;
import java.util.Map;
import pixels.Pixels;

/**
 * An interface that represent layers of Image inside an ImageModel. Have methods for creat, get
 * remove and set name for each layer. For inherited Model methods, by default are working with the
 * topmost visible layer.
 */
public interface Layers extends ImageModel<Pixels> {

  /**
   * Create a layer with given name and image; name can't be duplicate to what existed And set the
   * image to empty.
   *
   * @param name the name of layer
   * @throws IllegalStateException when the following method is called on an layer without loading
   *                               the image
   */
  void createLayer(String name);

  /**
   * Remove a layer with given name and image, and the rest of layer below it occupy their upper
   * position.
   *
   * @param name the name of layer
   * @throws IllegalArgumentException when the name is not found.
   * @throws IllegalStateException    when there isn't layer created.
   */
  void deleteLayer(String name);

  /**
   * Return the image stored in the layer.
   *
   * @param name the name of layer
   * @return an representation of image
   * @throws IllegalArgumentException when the name is not found.
   * @throws IllegalStateException    when there isn't layer created.
   */
  Pixels[][] getImage(String name);


  /**
   * Set the name from this layer to the name of 'to', if to is already exist, then override the
   * image stored in to with the image of from.
   *
   * @param from the name to be changed from
   * @param to   the name to be moved to
   * @throws IllegalArgumentException when the "from" is not found.
   * @throws IllegalStateException    when there isn't layer created.
   */
  void setName(String from, String to);

  /**
   * Replace a image in the layer if the name is already exist or load an image to empty layer if
   * not found.
   *
   * @param name  the name of layer
   * @param image the image to be loaded
   * @throws IllegalArgumentException when the name is not found.
   * @throws IllegalStateException    when there isn't layer created.
   */
  void loadImage(String name, Pixels[][] image);

  /**
   * Give all the layers that is currently stored in a form of map. The sequence is achieved by
   * calling getLayerList().
   *
   * @return the entire layers
   * @throws IllegalStateException when there isn't layer created.
   */
  Map<String, ImageLayer> exportAll();

  /**
   * Set the visibility of the layer to a particular state.
   *
   * @param name the name of layer
   * @param t    true fro visible and false for not
   * @throws IllegalArgumentException when the name is not found.
   * @throws IllegalStateException    when there isn't layer created.
   */
  void setVisibility(String name, boolean t);

  /**
   * Clear everything inside this model.
   */
  void clear();

  /**
   * Used with the exportAll() to achieve a full state of the layer.
   *
   * @return return the entire list that represent the name of layers defined by user in sequence
   * @throws IllegalStateException when there isn't layer created.
   */
  List<String> getLayerList();

  /**
   * Get the topmost Visible Layer's name.
   *
   * @return the current working layer
   * @throws IllegalStateException when there isn't layer created.
   */
  String getCurrent();

  /**
   * Set the current to the specified layer as topmost visible.
   *
   * @param name the name of layer to be set
   * @throws IllegalArgumentException when the name is not found.
   * @throws IllegalStateException    when there isn't layer created.
   */
  void setCurrent(String name);
}
