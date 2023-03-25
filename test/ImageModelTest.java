import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import imagecontroller.ImageController;
import imagecontroller.PPMImageController;
import java.io.Reader;
import java.io.StringReader;
import model.ImageModel;
import model.PPMImageModel;
import org.junit.Before;
import org.junit.Test;
import pixels.Pixel;
import pixels.Pixels;

/**
 * The test class for image model.
 */
public class ImageModelTest {

  ImageModel<Pixels> model;
  ImageController image;

  @Before
  public void initCond() {
    model = new PPMImageModel();
    image = new PPMImageController(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportFilePathNotExist() {
    Reader in = new StringReader("load res/Illegal_File_Path.ppm");
    image.acceptCommands(in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportFileWithIllegalCommentFormat() {
    Reader in = new StringReader("load res/Illegal_File_Path.ppm");
    image.acceptCommands(in);
  }

  @Test
  public void testImportFileWithMultipleComments() {
    ImageModel<Pixels> model2 = new PPMImageModel();
    ImageController image2 = new PPMImageController(model2);

    image.acceptCommands(new StringReader("load res/TestPic.ppm"));
    image2.acceptCommands(new StringReader("load res/TestPicWithMultipleComment.ppm"));

    Pixels[][] pixels1 = model.getPixels();
    Pixels[][] pixels2 = model2.getPixels();

    for (int i = 0; i < pixels1.length; i++) {
      for (int j = 0; j < pixels1[1].length; j++) {
        assertEquals(pixels1[i][j], pixels2[i][j]);
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportFileWithNotEnoughPixels() {
    image.acceptCommands(new StringReader("load res/TestPicNotEnoughData.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportFileWitTooManyPixels() {
    image.acceptCommands(new StringReader("load res/TestPicTooManyPixels.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportFileWithoutP3() {
    image.acceptCommands(new StringReader("load res/TestPicWithoutP3.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportFileWithoutWidth() {
    image.acceptCommands(new StringReader("load res/TestPicWithoutWidth.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportFileWithoutHeight() {
    image.acceptCommands(new StringReader("load res/TestPicWithoutHeight.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportFileWithoutColor() {
    image.acceptCommands(new StringReader("load res/TestPicWithoutColor.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportFileNotInCorrectFormat() {
    image.acceptCommands(new StringReader("load res/TestPicWithoutColor.abc"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportFileWithRGBValueContainsLetter() {
    image.acceptCommands(new StringReader("load res/TestPicWithIllegalRGB.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportFileWithNegativeColor() {
    image.acceptCommands(new StringReader("load res/TestPicNegativeOrColor.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportFileWithColorOutOfBound() {
    image.acceptCommands(new StringReader("load res/TestPicColorOutOfBound.ppm"));
  }

  @Test(expected = IllegalStateException.class)
  public void testExportFileBeforeImport() {
    image.acceptCommands(new StringReader("save res/Output.ppm"));
  }


  @Test(expected = IllegalStateException.class)
  public void testExportFileAfterFailedImport() {
    try {
      image.acceptCommands(new StringReader("save null import"));
    } catch (IllegalArgumentException e) {
      image.acceptCommands(new StringReader("save res/Output.ppm"));
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testExportFileWithNullOutput() {
    image.acceptCommands(new StringReader("save res/TestPic.ppm"));
    image.acceptCommands(new StringReader("save "));
  }

  @Test(expected = IllegalStateException.class)
  public void testExportFileWithNoppmOrtxtOutput() {
    image.acceptCommands(new StringReader("save res/TestPic.ppm"));
    image.acceptCommands(new StringReader("save Output"));
  }

  @Test(expected = IllegalStateException.class)
  public void testExportFileWithppmInMiddleOutput() {
    image.acceptCommands(new StringReader("save res/TestPic.ppm"));
    image.acceptCommands(new StringReader("save Out.ppm put"));
  }

  @Test(expected = IllegalStateException.class)
  public void testExportFileWithtxtInMiddleOutput() {
    image.acceptCommands(new StringReader("save res/TestPic.ppm"));
    image.acceptCommands(new StringReader("save Out.txt put"));
  }


  @Test
  public void testExportFileWithppmOutput() {
    ImageModel<Pixels> model2 = new PPMImageModel();

    ImageController image2 = new PPMImageController(model2);
    image.acceptCommands(new StringReader("load res/TestPic.ppm"));
    image.acceptCommands(new StringReader("save Output.ppm"));

    image.acceptCommands(new StringReader("load Output.ppm"));
    image2.acceptCommands(new StringReader("load res/TestPic.ppm"));
    Pixels[][] pixels1 = model.getPixels();
    Pixels[][] pixels2 = model2.getPixels();

    for (int i = 0; i < pixels1.length; i++) {
      for (int j = 0; j < pixels1[1].length; j++) {
        assertEquals(pixels1[i][j], pixels2[i][j]);
      }
    }

  }

  @Test
  public void testExportFileWithtxtOutput() {
    ImageModel<Pixels> model2 = new PPMImageModel();
    ImageController image2 = new PPMImageController(model2);
    image.acceptCommands(new StringReader("load res/TestPic.ppm"));
    image.acceptCommands(new StringReader("save Output.txt"));

    image.acceptCommands(new StringReader("load Output.txt"));
    image2.acceptCommands(new StringReader("load res/TestPic.ppm"));
    Pixels[][] pixels1 = model.getPixels();
    Pixels[][] pixels2 = model2.getPixels();

    for (int i = 0; i < pixels1.length; i++) {
      for (int j = 0; j < pixels1[1].length; j++) {
        assertEquals(pixels1[i][j], pixels2[i][j]);
      }
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testObserveWidthWithoutImport() {
    ImageModel<Pixels> image2 = new PPMImageModel();
    image2.observeWidth();
  }

  @Test
  public void testObserveWidth() {
    image.acceptCommands(new StringReader("load res/TestPic.ppm"));
    assertEquals(50, model.observeWidth());
  }

  @Test(expected = IllegalStateException.class)
  public void testObserveHeightWithoutImport() {
    ImageModel<Pixels> image2 = new PPMImageModel();
    image2.observeHeight();
  }

  @Test
  public void testObserveHeight() {
    image.acceptCommands(new StringReader("load res/TestPic.ppm"));
    assertEquals(50, model.observeHeight());
  }

  @Test(expected = IllegalStateException.class)
  public void testObserveLimitWithoutImport() {
    ImageModel<Pixels> image2 = new PPMImageModel();
    image2.observeLimit();
  }

  @Test
  public void testIsImportedWithoutImport() {
    assertFalse(model.isImported());
  }

  @Test
  public void testIsImported() {
    image.acceptCommands(new StringReader("load res/TestPic.ppm"));
    assertTrue(model.isImported());
  }

  @Test(expected = IllegalStateException.class)
  public void testObservePixelWithoutImport() {
    ImageModel<Pixels> image2 = new PPMImageModel();
    image2.observePixel(0, 0);
  }

  @Test
  public void testObservePixel() {
    image.acceptCommands(new StringReader("load res/TestPic.ppm"));
    model.observePixel(0, 0);
    assertEquals(new Pixel(255, 255, 255), model.observePixel(0, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetPixelWithoutImport() {
    ImageModel<Pixels> image2 = new PPMImageModel();
    image2.getPixels();
  }

  @Test
  public void testGetPixels() {
    image.acceptCommands(new StringReader("load res/TestPic.ppm"));
    Pixels[][] test = model.getPixels();
    test[0][0] = new Pixel(0, 0, 0);

    assertNotEquals(test, model.getPixels());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testCheckerBoardNullColor() {
    model.checkerBoard(null, null, 4, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckerBoardNullTileNumber() {
    Pixels p = new Pixel(3, 3, 3);
    Pixels p2 = new Pixel(4, 3, 3);
    model.checkerBoard(p, p, -1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckerBoardSameColor() {
    Pixels p = new Pixel(3, 3, 3);
    Pixels p2 = new Pixel(3, 3, 3);
    model.checkerBoard(p, p2, -1, -1);
  }


  //The result of checkerBoard is in alternating color
  @Test
  public void testCheckerBoard() {
    Pixels p = new Pixel(0, 0, 0);
    Pixels p2 = new Pixel(255, 255, 255);
    image.acceptCommands(new StringReader("load res/TestPic.ppm"));
    Pixels[][] checkerBoard = model.checkerBoard(p, p2, 25, 5);
    model.setImage(checkerBoard);

    assertEquals(p2, model.observePixel(0, 0));
    assertEquals(p, model.observePixel(0, 5));
    assertEquals(p2, model.observePixel(1, 0));
    assertEquals(p, model.observePixel(1, 5));

    assertEquals(p2, model.observePixel(1, 10));
    assertEquals(p, model.observePixel(1, 15));
    assertEquals(p2, model.observePixel(2, 10));
    assertEquals(p, model.observePixel(2, 15));

    assertEquals(p2, model.observePixel(24, 10));
    assertEquals(p, model.observePixel(24, 15));

    assertEquals(25, model.observeWidth());
    assertEquals(25, model.observeHeight());
  }
}
