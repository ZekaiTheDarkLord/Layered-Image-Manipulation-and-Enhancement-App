import static org.junit.Assert.assertEquals;

import filter.Blur;
import filter.Filter;
import filter.Sharpening;
import org.junit.Before;
import org.junit.Test;
import pixels.Pixel;
import pixels.Pixels;

/**
 * Test for filter and its implementation.
 */
public class TestFilter {

  private Filter blur;
  private Filter sharpen;

  @Before
  public void initCond() {
    blur = new Blur();
    sharpen = new Sharpening();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputBlur() {
    blur.apply(null);
  }

  @Test
  public void testBlurWithSinglePixel() {
    Pixels[][] testPixels = new Pixels[1][1];
    testPixels[0][0] = new Pixel(0, 15, 30);

    blur.apply(testPixels);

    assertEquals(0, testPixels[0][0].getR());
    assertEquals(3, testPixels[0][0].getG());
    assertEquals(7, testPixels[0][0].getB());
  }

  @Test
  public void testBlurWithSmallMatrix() {
    Pixels[][] testPixels = new Pixels[2][2];
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        testPixels[i][j] = new Pixel(5, 5, 5);
      }
    }

    blur.apply(testPixels);

    Pixels[][] expectedPixels = new Pixels[2][2];

    expectedPixels[0][0] = new Pixel(2, 2, 2);
    expectedPixels[0][1] = new Pixel(2, 2, 2);
    expectedPixels[1][0] = new Pixel(2, 2, 2);
    expectedPixels[1][1] = new Pixel(1, 1, 1);

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(expectedPixels[i][j], testPixels[i][j]);
      }
    }
  }

  @Test
  public void testBlurWithHugeMatrix() {
    Pixels[][] testPixels = new Pixels[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        testPixels[i][j] = new Pixel(10, 10, 10);
      }
    }

    blur.apply(testPixels);

    Pixels[][] expectedPixels = new Pixels[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        expectedPixels[i][j] = new Pixel(5, 5, 5);
      }
    }

    for (int i = 0; i < 1; i++) {
      for (int j = 0; j < 1; j++) {
        assertEquals(expectedPixels[i][j], testPixels[i][j]);
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputSharpen() {
    sharpen.apply(null);
  }

  @Test
  public void testSharpeningWithSinglePixel() {
    Pixels[][] testPixels = new Pixels[1][1];
    testPixels[0][0] = new Pixel(0, 15, 30);

    sharpen.apply(testPixels);

    assertEquals(0, testPixels[0][0].getR());
    assertEquals(15, testPixels[0][0].getG());
    assertEquals(30, testPixels[0][0].getB());
  }

  @Test
  public void testSharpeningWithSmallMatrix() {
    Pixels[][] testPixels = new Pixels[2][2];
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        testPixels[i][j] = new Pixel(5, 5, 5);
      }
    }

    sharpen.apply(testPixels);

    Pixels[][] expectedPixels = new Pixels[2][2];

    expectedPixels[0][0] = new Pixel(8, 8, 8);
    expectedPixels[0][1] = new Pixel(9, 9, 9);
    expectedPixels[1][0] = new Pixel(10, 10, 10);
    expectedPixels[1][1] = new Pixel(11, 11, 11);

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(expectedPixels[i][j], testPixels[i][j]);
      }
    }
  }

  @Test
  public void testSharpeningWithHugeMatrix() {
    Pixels[][] testPixels = new Pixels[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        testPixels[i][j] = new Pixel(1, 1, 1);
      }
    }

    sharpen.apply(testPixels);

    Pixels[][] expectedPixels = new Pixels[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        expectedPixels[i][j] = new Pixel(1, 1, 1);
      }
    }

    expectedPixels[1][1] = new Pixel(2, 2, 2);
    expectedPixels[1][3] = new Pixel(2, 2, 2);

    for (int i = 0; i < 1; i++) {
      for (int j = 0; j < 1; j++) {
        assertEquals(expectedPixels[i][j], testPixels[i][j]);
      }
    }
  }

}
