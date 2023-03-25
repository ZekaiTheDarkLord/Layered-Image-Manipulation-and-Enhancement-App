import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import pixels.Pixel;
import pixels.Pixels;
import transformation.ColorTransformation;
import transformation.Greyscale;
import transformation.Sepia;

/**
 * Test for color transformation and its implementation.
 */
public class TestColorTransformation {

  private ColorTransformation gs;
  private ColorTransformation sp;

  @Before
  public void initCond() {
    gs = new Greyscale();
    sp = new Sepia();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputGreyscale() {
    gs.apply(null);
  }

  @Test
  public void testGreyscaleWithSinglePixel() {
    Pixels[][] testPixels = new Pixels[1][1];
    testPixels[0][0] = new Pixel(0, 15, 30);

    gs.apply(testPixels);

    assertEquals(12, testPixels[0][0].getR());
    assertEquals(12, testPixels[0][0].getG());
    assertEquals(12, testPixels[0][0].getB());
  }

  @Test
  public void testGreyScaleWithSmallMatrix() {
    Pixels[][] testPixels = new Pixels[2][2];
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        testPixels[i][j] = new Pixel(5, 5, 5);
      }
    }

    gs.apply(testPixels);

    Pixels[][] expectedPixels = new Pixels[2][2];

    expectedPixels[0][0] = new Pixel(4, 4, 4);
    expectedPixels[0][1] = new Pixel(4, 4, 4);
    expectedPixels[1][0] = new Pixel(4, 4, 4);
    expectedPixels[1][1] = new Pixel(4, 4, 4);

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(expectedPixels[i][j], testPixels[i][j]);
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputSepia() {
    sp.apply(null);
  }

  @Test
  public void testSepiaWithSinglePixel() {
    Pixels[][] testPixels = new Pixels[1][1];
    testPixels[0][0] = new Pixel(0, 15, 30);

    sp.apply(testPixels);

    assertEquals(17, testPixels[0][0].getR());
    assertEquals(15, testPixels[0][0].getG());
    assertEquals(12, testPixels[0][0].getB());
  }

  @Test
  public void testSepiaWithSmallMatrix() {
    Pixels[][] testPixels = new Pixels[2][2];
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        testPixels[i][j] = new Pixel(5, 5, 5);
      }
    }

    sp.apply(testPixels);

    Pixels[][] expectedPixels = new Pixels[2][2];
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        expectedPixels[i][j] = new Pixel(6, 6, 4);
      }
    }

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(expectedPixels[i][j], testPixels[i][j]);
      }
    }
  }

}
