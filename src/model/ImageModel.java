package model;

/**
 * The model for image, use a certain kinds of Pixels determined by implementation and support
 * multiple operations.
 *
 * @param <K> an representation of Pixel
 */
public interface ImageModel<K> extends ImageModelGetState<K> {

  /**
   * Create a checkBoard image using the given two kinds of color and width height.
   *
   * @param a          the first kind of color
   * @param b          the second kind of color
   * @param tileNumber the width of checkBoard
   * @param size       the height of checkBoard
   * @return an set of Arrays of Arrays that represents an image.
   */
  K[][] checkerBoard(K a, K b, int tileNumber, int size);


  void setImage(K[][] image);

  /**
   * Blur the imported image.
   */
  void blurImage();

  /**
   * Sharpening the imported image.
   */
  void sharpeningImage();

  /**
   * To make the imported image a sepia style.
   */
  void sepiaImage();

  /**
   * To make the imported image a monochrome style.
   */
  void monochromeImage();


}
