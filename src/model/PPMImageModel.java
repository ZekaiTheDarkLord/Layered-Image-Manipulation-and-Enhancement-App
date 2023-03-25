package model;

import pixels.Pixels;

/**
 * A simple ppm image model to operates several functionalities.
 */
public class PPMImageModel extends AbstractImageModel {

  /**
   * The constructor with one parameter.
   *
   * @param image the input image.
   */
  public PPMImageModel(Pixels[][] image) {
    this.pixels = image;
  }

  /**
   * A convinient constuctor.
   */
  public PPMImageModel() {
    //default constructor for empty model
  }

  @Override
  public boolean isImported() {
    return this.pixels != null;
  }
}
