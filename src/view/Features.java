package view;

import imagecontroller.ImageController;

/**
 * All the listener for possible buttons in GUI, by implementing this interface means supporting the
 * listed methods as action listener to GUI's buttons.
 */
public interface Features extends ImageController {

  /**
   * An action listener for button blur, operating image blur when called.
   */
  void blur();

  /**
   * An action listener for button sharpening, operating image sharpening when called.
   */
  void sharpening();

  /**
   * An action listener for button sepia, operating image sepia when called.
   */
  void sepia();

  /**
   * An action listener for button greyScale, operating image greyScale when called.
   */
  void greyScale();

  /**
   * An action listener for button mosaic, operating image mosaic when called. The user will give
   * the string that may contains seeds for mosaic, must check whether the input is valid,i.e
   * positive integer.
   *
   * @param seeds the seeds for mosaic
   */
  void mosaic(String seeds);

  /**
   * An action listener for button downScaling, operating image downScaling when called. The user
   * will give the string that may contains seeds for downScaling, must check whether the input is
   * valid, i.e positive integer and must be smaller than original image.
   *
   * @param width  the new width for down scaling
   * @param height the new height down scaling
   */
  void downScaling(String width, String height);

  /**
   * An action listener for loading an image. The user will specify a file path with any mean, in
   * this GUI it is file chooser so no need to check for valid file path, but must check for image
   * format, restricted using fileChooser in GUI.
   *
   * @param path the path of image, will render message if nothing is found or not in correct
   *             format.
   */
  void load(String path);

  /**
   * An action listener for saving an image. The user will specify the directory to save and them in
   * which format it will save. If the format specified is not supported or the directory is
   * illegal, an message should displayed to user.
   *
   * @param path   the directory to save the image
   * @param format the image format for saving..
   */
  void save(String path, String format);

  /**
   * An action listener for loading all image using an txt file path. The user will specify the txt
   * that contains the layer information and in this project, we by default believe the image is
   * saved under same directory.
   *
   * @param path the path of txt, other format is not acceptable
   */
  void loadAll(String path);

  /**
   * An action listener for saving all image using an txt file path. The user will specify the
   * directory to store the txt that contains the layer information and in this project, we by
   * default believe the image is saved under same directory.
   */
  void saveAll();

  /**
   * An action listener for saving an image with a specified name. The name can be null or "" and
   * will displaying message if its not acceptable. But "null" is considered as a valid name. The
   * create must called the first before every other buttons, which means by pressing any other
   * before creating an image should display an warning message in GUI, this project only.
   *
   * @param name the name for a new image layer.
   */
  void create(String name);

  /**
   * An action listener for deleting an image with a specified name. The user will type the name
   * that matches the displayed image layers or choose by an option panel. If the name is null or
   * "", the action is ignored and if trying to delete with nothing created, warning message should
   * be displayed.
   *
   * @param name the name of layer to be deleted
   */
  void delete(String name);

  /**
   * An action listener for setting an image with a specified name to current. The user will type
   * the name that matches the displayed image layers or choose by an option panel. If the name is
   * null or "", the action is ignored. If the current image is invisible, nothing will appear on
   * the board.
   *
   * @param name the name of layer to be deleted
   */
  void current(String name);

  /**
   * An action listener for making an image visible. When it is called, the rendered image and
   * current image will look for topmost visible image and displayed on board. The sequence is
   * considered by the creation of layer. The newer it is created, it is considered at topmost. The
   * following operation will by default operates on this topmost visible layer.
   *
   * @param name the name of image to be visible.
   */
  void visible(String name);

  /**
   * An action listener for making an image invisible. When it is called, the rendered image and
   * current image will look for topmost visible image and displayed on board.The sequence is *
   * considered by the creation of layer. The following operation will by default operates on this
   * topmost visible layer. If all the layers are invisible, no image will be displayed.
   *
   * @param name the name of image to be visible.
   */
  void invisible(String name);

  /**
   * Specify the layer that have been created and allows the GUI to display them by using a string
   * array. Used for displaying options for choosing an existing layer.
   *
   * @return a copy of current layers name.
   */
  String[] getLayers();
}
