import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 * Self testing for ImageIO library.
 */
public class ImageIoAcceptFormat {

  /**
   * Some javadoc.
   *
   * @param args description
   * @throws IOException whatever
   */
  public static void main(String[] args) throws IOException {
    String[] readFormats = ImageIO.getReaderFormatNames();
    String[] writeFormats = ImageIO.getWriterFormatNames();
    System.out.println("Readers:  " + Arrays.asList(readFormats));
    System.out.println("Writers:  " + Arrays.asList(writeFormats));
    //[JPG, jpg, tiff, bmp, BMP, gif, GIF, WBMP, png, PNG, JPEG, tif, TIF, TIFF, wbmp, jpeg]
  }


}
