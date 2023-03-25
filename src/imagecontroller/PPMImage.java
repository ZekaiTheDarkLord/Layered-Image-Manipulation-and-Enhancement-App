package imagecontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import pixels.Pixel;
import pixels.Pixels;

/**
 * An implementation of image model in the form of PPM that supports image functions as well as PPM
 * import and export.
 */
public final class PPMImage implements Image {


  /**
   * If the path has same name, the new file will cover the old file, If the path is different,
   * create a new file.
   *
   * <p>If the path does not have .ppm or .txt at the end of String, or does not contain "."
   * or is not either one of ppm or txt after the "." throw IllegalArgumentexception
   *
   * <p>If there is no image stored (run before importPPM) throw IllegalstateException
   *
   * @throws IOException when the exporting process faces a problem
   */
  @Override
  public void write(String path, Pixels[][] pixels) {
    int index = path.indexOf(".");
    if (index < 0 || (!path.substring(index + 1).equals("ppm") && !path.substring(index + 1)
        .equals("txt"))) {
      throw new IllegalArgumentException("Not a ppm format");
    }

    StringBuilder log = new StringBuilder();
    File f = new File(path);
    FileWriter fr;

    try {
      fr = new FileWriter(f);

      log.append("P3\n");
      int height = pixels.length;
      int width = pixels[0].length;
      log.append(width + " " + height + "\n" + 255 + "\n");

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = pixels[i][j].getR();
          int g = pixels[i][j].getG();
          int b = pixels[i][j].getB();
          log.append(r + "\n" + g + "\n" + b + "\n");
        }
      }

      fr.write(log.toString());
      fr.flush();
      f.createNewFile();

    } catch (IOException e) {
      throw new IllegalArgumentException("IOException");
    }
  }

  /**
   * Import an image file with a given file destination. If the given file path is either null or is
   * not an ppm, throw IllegalArgumentException
   *
   * <p>If the comment inside the file is not started with '#' or
   * the ppm file is not in the order of P3, Image size and Allowed color value(255) throw
   * IllegalArgumentException.
   *
   * <p>If the number of Pixel is not correspond with given image size(i.e too many pixels)
   * or any of the pixel inside the image does not have enough r,g,b value throw new
   * IllegalArgumentException.
   *
   * <p>If the r,g,b value contains illegal character (1A1) or is out of bound(>255 or <0)
   * throw illegalArgumentException
   *
   * @throws IllegalArgumentException when the file does not exist or the file path is illegal.
   */
  @Override
  public Pixels[][] read(String path) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("No path found");
    }

    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (!s.equals("") && s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());
    String token;
    token = sc.next();

    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    Pixels[][] pixels;

    try {

      int width = sc.nextInt();
      int height = sc.nextInt();

      int maxValue = sc.nextInt();
      pixels = new Pixel[height][width];

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          pixels[i][j] = new Pixel(r, g, b);
        }
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Not in a PPM format");
    }

    if (sc.hasNext()) {
      throw new IllegalArgumentException("More value than expected");
    }

    return pixels;
  }

}
