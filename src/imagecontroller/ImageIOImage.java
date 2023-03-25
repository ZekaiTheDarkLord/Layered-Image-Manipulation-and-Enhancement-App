package imagecontroller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import pixels.Pixel;
import pixels.Pixels;

/**
 * If I write an ImageFormatUtil class and move all supported format to there, .pmp,.jpg.... Then I
 * don't need these ImageModel to expand.
 *
 * <p>But good news is, this extension and delegation support client to design there own image
 * format or some not supported in ImageIO.
 */
public class ImageIOImage implements Image {

  private final Image delegate;


  public ImageIOImage() {
    delegate = new PPMImage();
  }

  @Override
  public Pixels[][] read(String path) {
    int index = path.indexOf(".");
    if (path.substring(index + 1).equals("ppm") || path.substring(index + 1)
        .equals("txt")) {
      return delegate.read(path);
    } else {

      File file;
      BufferedImage src = null;
      ImageWriter imgWrier;
      file = new File(path);
      try {
        src = ImageIO.read(file);
        return convertImageToPixels(src);

      } catch (IOException e) {
        throw new IllegalArgumentException("Wrong File Path");
      }
    }
  }

  @Override
  public void write(String filePath, Pixels[][] image) {
    int index = filePath.indexOf(".");
    if (index < 0 || filePath.substring(index + 1).equals("ppm") || filePath.substring(index + 1)
        .equals("txt")) {
      delegate.write(filePath, image);
    } else {
      String type = filePath.substring(index + 1);
      try {
        writeImageFromPixels(filePath, type, image);
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Failed to export");
      }
    }
  }


  private Pixels[][] convertImageToPixels(BufferedImage bf) {
    // 获取图片宽度和高度
    int width = bf.getWidth();
    int height = bf.getHeight();
    // 将图片sRGB数据写入一维数组
    int[] data = new int[width * height];

    bf.getRGB(0, 0, width, height, data, 0, width);

    Pixels[][] pixels = new Pixels[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Color c = new Color(data[i * width + j]);
        Pixels p = new Pixel(c.getRed(), c.getGreen(), c.getBlue());
        pixels[i][j] = p;
      }
    }
    return pixels;
  }

  private void writeImageFromPixels(String imageFile, String type, Pixels[][] image)
      throws FileNotFoundException {
    // 获取数组宽度和高度
    int width = image[0].length;
    int height = image.length;

    int[] data = new int[width * height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixels p = image[i][j];
        Color c = new Color(p.getR(), p.getG(), p.getB());
        data[i * width + j] = c.getRGB();
        data[i * width + j] = data[i * width + j];
      }
    }

    // 将数据写入BufferedImage
    BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    bf.setRGB(0, 0, width, height, data, 0, width);
    // 输出图片
    try {
      File file = new File(imageFile);
      ImageIO.write(bf, type, file);
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}


