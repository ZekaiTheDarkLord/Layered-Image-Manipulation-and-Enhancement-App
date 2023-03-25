package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import extra.Downscaling;
import extra.ImageDownscaling;
import extra.ImageMosaicing;
import extra.Mosaicing;
import imagecontroller.Image;
import imagecontroller.ImageIOImage;
import org.junit.Test;
import pixels.Pixels;

/**
 * The class that helps to test the functionality of downscaling and mosaicing.
 */
public class testExtra {
  @Test(expected = IllegalArgumentException.class)
  public void testDownScalingNegativeWidth() {
    Downscaling ds = new ImageDownscaling();
    Image io = new ImageIOImage();
    Pixels[][] image2 = io.read("res/a.jpg");
    Pixels[][] dsc = ds.apply(image2, -200, 100);
    io.write("downScaling.jpg", dsc);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownScalingNegativeHeight() {
    Downscaling ds = new ImageDownscaling();
    Image io = new ImageIOImage();
    Pixels[][] image2 = io.read("res/a.jpg");
    Pixels[][] dsc = ds.apply(image2, 200, -100);
    io.write("downScaling.jpg", dsc);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownScalingWidthOutOfBound() {
    Downscaling ds = new ImageDownscaling();
    Image io = new ImageIOImage();
    Pixels[][] image2 = io.read("res/a.jpg");
    Pixels[][] dsc = ds.apply(image2, 800, 100);
    io.write("downScaling.jpg", dsc);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownScalingHeightOutOfBound() {
    Downscaling ds = new ImageDownscaling();
    Image io = new ImageIOImage();
    Pixels[][] image2 = io.read("res/a.jpg");
    Pixels[][] dsc = ds.apply(image2, 200, 500);
    io.write("downScaling.jpg", dsc);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMosaicingNegativeSeeds() {
    Mosaicing mosaicing = new ImageMosaicing();
    Image io = new ImageIOImage();
    Pixels[][] image = io.read("res/a.jpg");
    mosaicing.apply(image, -1000);
    io.write("mosaic.jpg", image);
  }

  @Test
  public void testDownScaling() {
    Downscaling ds = new ImageDownscaling();
    Image io = new ImageIOImage();
    Pixels[][] image2 = io.read("res/a.jpg");
    Pixels[][] dsc = ds.apply(image2, 200, 100);

    assertEquals(100, dsc.length);
    assertEquals(200, dsc[0].length);

    io.write("downScaling.jpg", dsc);
  }

  @Test
  public void testMosaicing() {
    Mosaicing mosaicing = new ImageMosaicing();
    Image io = new ImageIOImage();
    Pixels[][] image = io.read("res/a.jpg");
    Pixels[][] imageCopy = image.clone();

    mosaicing.apply(image, 1000);
    io.write("mosaic.jpg", image);

    assertEquals(imageCopy.length, image.length);
    assertEquals(imageCopy[0].length, image[0].length);
  }
}
