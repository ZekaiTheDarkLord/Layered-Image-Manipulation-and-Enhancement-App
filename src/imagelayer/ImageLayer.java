package imagelayer;

import pixels.Pixels;

/**
 * An representation of a single Image Layer. Expect the image to be represented by using pixels,
 * and keep track of whether this image is transparent.
 */
public interface ImageLayer {

  /**
   * Set the image of this layer with the given image. Implementation either use Pixels interface to
   * store image or decompose it into your own representation.
   *
   * @param image The image represented in Pixels
   */
  void setImage(Pixels[][] image);

  /**
   * Export the image stored in current layer.
   *
   * @return the representation of Image
   */
  Pixels[][] peekImage();

  /**
   * Get the visibility in this layer.
   *
   * @return true for visible and false for transparent
   */
  boolean getVisibility();

  /**
   * Set the visibility of this image layer.
   *
   * @param t true for visible and false for transparent
   */
  void setVisibility(boolean t);


}
