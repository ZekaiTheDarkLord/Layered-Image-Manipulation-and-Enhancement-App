import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import pixels.Pixel;

/**
 * Test the Pixels and its implementation.
 */
public class TestPixel {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNegativeRed() {
    Pixel testPixel = new Pixel(-2, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithRedOutOfBound() {
    Pixel testPixel = new Pixel(258, 2, 3);
  }

  @Test
  public void testConstructorWithRedJustInBound() {
    Pixel testPixel = new Pixel(255, 2, 3);
    assertEquals(255, testPixel.getR());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNegativeGreen() {
    Pixel testPixel = new Pixel(2, -2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithGreenOutOfBound() {
    Pixel testPixel = new Pixel(255, 258, 3);
  }

  @Test
  public void testConstructorWithGreenJustInBound() {
    Pixel testPixel = new Pixel(2, 255, 3);
    assertEquals(255, testPixel.getG());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNegativeBlue() {
    Pixel testPixel = new Pixel(2, 2, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithBlueOutOfBound() {
    Pixel testPixel = new Pixel(255, 253, 258);
  }

  @Test
  public void testConstructorWithBlueJustInBound() {
    Pixel testPixel = new Pixel(2, 2, 255);
    assertEquals(255, testPixel.getB());
  }

  @Test
  public void testGetter() {
    Pixel testPixel = new Pixel(2, 3, 4);
    int red = testPixel.getR();
    red = 100;
    int green = testPixel.getG();
    green = 100;
    int blue = testPixel.getB();
    blue = 100;

    assertEquals(2, testPixel.getR());
    assertEquals(3, testPixel.getG());
    assertEquals(4, testPixel.getB());
  }

  @Test
  public void testSetRGB() {
    Pixel testPixel = new Pixel(2, 3, 4);
    int num = 100;
    testPixel.setRGB(new Pixel(num, num, num));
    num = 500;

    assertEquals(100, testPixel.getR());
    assertEquals(100, testPixel.getG());
    assertEquals(100, testPixel.getB());
  }

  @Test
  public void testEqualsAndHashCode() {
    Pixel testPixel = new Pixel(2, 3, 4);
    Pixel testPixel2 = new Pixel(2, 3, 4);
    Pixel testPixel3 = new Pixel(4, 3, 2);
    Pixel testPixel4 = new Pixel(5, 5, 5);

    assertEquals(testPixel, testPixel2);
    assertEquals(testPixel.hashCode(), testPixel2.hashCode());

    assertNotEquals(testPixel, testPixel4);
    assertNotEquals(testPixel.hashCode(), testPixel4.hashCode());

    assertNotEquals(testPixel, testPixel3);
    assertNotEquals(testPixel.hashCode(), testPixel3.hashCode());
  }
}
