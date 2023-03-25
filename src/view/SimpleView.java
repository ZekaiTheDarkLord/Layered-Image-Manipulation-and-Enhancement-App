package view;

import imagelayer.ImageLayer;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A text based view that will interact with user with diffrent input using an appendable.
 */
public class SimpleView implements PictureProcessingView {

  private final Appendable ap;

  public SimpleView() {
    this.ap = new StringBuilder();
  }

  public SimpleView(Appendable ap) {
    this.ap = ap;
  }


  @Override
  public void renderLayer(List<String> layers, Map<String, ImageLayer> data) {
    String visibility = null;
    String occupy;

    String image = "----------------------------\n";

    for (String s : layers) {

      if (data.get(s) == null) {
        occupy = "empty";
        image = image + s + "(invisible)" + " " + occupy + "\n";
      } else {
        occupy = "occupy";

        if (data.get(s).getVisibility()) {
          visibility = "(visible)";
        } else {
          visibility = "(invisible)";
        }

        image = image + s + visibility + " " + occupy + "\n";
      }
    }

    image = image + "----------------------------\n";

    try {
      ap.append(image);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void renderMessage(String message) {
    try {
      ap.append(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Appendable peekOutput() {
    return new StringBuilder(ap.toString());
  }

}
