package model;

import filter.Blur;
import filter.Filter;
import filter.Sharpening;
import pixels.Pixels;
import transformation.ColorTransformation;
import transformation.Greyscale;
import transformation.Sepia;

//INVARIANT: Pixels inside is never null after imported(created when imported)

/**
 * An abstract class that implements part of Model functionality to avoid redundant code.
 */
public abstract class AbstractImageModel implements ImageModel<Pixels> {

  protected static int maxValue;
  protected Pixels[][] pixels;
  protected int width;
  protected int height;

  public abstract boolean isImported();

  @Override
  public Pixels[][] checkerBoard(Pixels a, Pixels b, int tileNumbers, int size) {
    if (a == null || b == null || a.equals(b) || tileNumbers <= 0 || size <= 0) {
      throw new IllegalArgumentException("Illegal input for checkerBoard");
    }

    int totalPixel = (int) Math.floor(Math.sqrt(tileNumbers) * size);
    Pixels[][] checkerBoard = new Pixels[totalPixel][totalPixel];
    int count = 0;

    for (int i = 0; i < totalPixel; i++) {
      if (i % size != 0) {
        count += 1;
      }
      for (int j = 0; j < totalPixel; j++) {
        if (j % size == 0) {
          count += 1;
        }
        if (count % 2 == 0) {
          checkerBoard[i][j] = a;
        }
        if (count % 2 == 1) {
          checkerBoard[i][j] = b;
        }
      }
    }

    return checkerBoard;
  }


  @Override
  public Pixels[][] getPixels() {
    goOrException();
    Pixels[][] temp = new Pixels[pixels.length][pixels[0].length];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        temp[i][j] = pixels[i][j].getCopy();
      }
    }

    return temp;
  }

  @Override
  public int observeLimit() {
    goOrException();
    int temp = maxValue;
    return temp;
  }

  @Override
  public Pixels observePixel(int width, int height) {
    goOrException();
    Pixels temp = pixels[width][height].getCopy();
    return temp;
  }

  @Override
  public int observeWidth() {
    goOrException();
    int temp = this.width;
    return temp;
  }

  @Override
  public int observeHeight() {
    goOrException();
    int temp = this.height;
    return temp;
  }


  @Override
  public void setImage(Pixels[][] image) {
    if (image == null) {
      throw new IllegalArgumentException("Null input for image");
    }
    this.height = image.length;
    this.width = image[0].length;
    this.pixels = new Pixels[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.pixels[i][j] = image[i][j];
      }
    }
  }

  private void goOrException() {
    if (!this.isImported()) {
      throw new IllegalStateException("There are no file imported!");
    }
  }

  @Override
  public void blurImage() {
    Blur blur = new Blur();
    blur.apply(this.pixels);
  }

  @Override
  public void sharpeningImage() {
    Filter sharp = new Sharpening();
    sharp.apply(this.pixels);
  }

  @Override
  public void monochromeImage() {
    ColorTransformation ct = new Greyscale();
    ct.apply(this.pixels);
  }

  @Override
  public void sepiaImage() {
    ColorTransformation ct = new Sepia();
    ct.apply(this.pixels);
  }
}
