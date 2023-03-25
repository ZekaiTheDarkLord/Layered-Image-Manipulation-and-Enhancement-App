package view;

import imagelayer.ImageLayer;
import java.util.List;
import java.util.Map;

/**
 * A view class that will show all the information when image is processing.
 */
public interface PictureProcessingView {

  /**
   * Gives the image of the iamge processing app.
   */
  void renderLayer(List<String> layers, Map<String, ImageLayer> data);

  /**
   * Append the given message into the output.
   *
   * @param message the message designer wants to show
   */
  void renderMessage(String message);

  /**
   * See the current output of the view.
   *
   * @return the current output.
   */
  Appendable peekOutput();
}
