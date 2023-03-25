package view;

import java.awt.image.BufferedImage;

public interface ImageGuiView extends PictureProcessingView {


  /**
   * Provide the view with an action listener for the button that should cause the program to
   * process a command.
   *
   * @param features An feature object that supports all functionality for buttons.
   * @throws IllegalArgumentException if features is null
   */
  void addFeatures(Features features);


  /**
   * Render the image by taking the string as file path and give user the preview before save. If
   * the image is null, the rendered image will be empty label.
   *
   * @param image The file path of string
   */
  void renderImage(BufferedImage image);


  /**
   * Repaint and refresh the GUI view when calling, especially after rendering an image.
   */
  void refresh();

  /**
   * Set this GUI's visibility as true. Called when the GUI needs to be shown, after constructing.
   */
  void setVisible();

  /**
   * Clear the imageLabels on the GUI and return them to original state with default message. Used
   * after exporting everything out from this GUI and resetting.
   */
  void clear();

  /**
   * Render a warning message by using a pop up window when called using the given string as content
   * information.
   *
   * @param warn text showed on the screen.
   */
  void renderWarning(String warn);

}
