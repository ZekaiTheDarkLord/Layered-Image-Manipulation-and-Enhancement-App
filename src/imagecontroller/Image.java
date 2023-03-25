package imagecontroller;

import pixels.Pixels;

/**
 * The interface that support any image functionality. Any class that implements this methods should
 * support importing and exporting in ppm form.
 */
public interface Image {


  /**
   * Read any kind of image type, determined by each implementation about what type is supported
   * from a path.
   *
   * @param path String that contains the image to read
   * @throws IllegalArgumentException If path is null or IOException.
   */
  Pixels[][] read(String path);


  /**
   * Write any kind of image type, determined by each implementation about what type is supported
   * for designate path.
   *
   * @param path String that contains the file to write to
   * @throws IllegalArgumentException If path is null or IOException.
   */
  void write(String path, Pixels[][] image);

}

