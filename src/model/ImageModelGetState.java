package model;

/**
 * Provide a read only version of Image model for future implementation of View and Controller.
 *
 * @param <K> an representation of Pixel
 */
public interface ImageModelGetState<K> {

  /**
   * Observe the width of the imported image without changing the original.
   *
   * @return the width of the imported image.
   * @throws IllegalStateException When there are no imported image.
   */
  int observeWidth();

  /**
   * Observe the height of the imported image without changing the orininal data.
   *
   * @return the height of the imported image.
   * @throws IllegalStateException When there are no imported image.
   */
  int observeHeight();

  /**
   * Observe the limit of the imported image without changing the orininal data.
   *
   * @return the limit of the imported image.
   * @throws IllegalStateException When there are no imported image.
   */
  int observeLimit();

  /**
   * Ovserve whether the image is imported or not.
   *
   * @return true if the image is imported. false if there are no imported image.
   */
  boolean isImported();

  /**
   * Observe the single pixel at the given width and height.
   *
   * @param width  the width of that pixel
   * @param height the hight of that pixel
   * @return a copy of the pixel at the given point
   * @throws IllegalArgumentException if null input or is not contained inside the image
   */
  K observePixel(int width, int height);


  /**
   * Get the pixels in a image model.
   */
  K[][] getPixels();
}
