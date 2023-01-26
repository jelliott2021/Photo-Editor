import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HistogramTest {
  @Test
  public void testShowRed() {
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File("res\\PNGs\\testPNG.png"));
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    }
    BufferedImage hist = new Histogram(image).showRed();

    int[] frequencies = new int[256];
    frequencies[84] = 100;
    frequencies[91] = 100;
    frequencies[101] = 100;
    frequencies[149] = 100;
    frequencies[166] = 100;
    frequencies[192] = 100;
    frequencies[199] = 100;
    frequencies[201] = 100;
    frequencies[210] = 100;

    for (int v = 0; v < 256; v++) {
      for (int h = 100 - 1; h > 100 - frequencies[v]; h--) {
        if (h > 100 - frequencies[v]) {
          Assert.assertEquals(Color.RED.getRGB(), hist.getRGB(v, h));
        } else {
          Assert.assertEquals(Color.BLACK.getRGB(), hist.getRGB(v, h));
        }
      }
    }
  }

  @Test
  public void testShowGreen() {
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File("res\\PNGs\\testPNG.png"));
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    }
    BufferedImage hist = new Histogram(image).showGreen();

    int[] frequencies = new int[256];
    frequencies[101] = 50;
    frequencies[108] = 50;
    frequencies[118] = 50;
    frequencies[151] = 50;
    frequencies[168] = 50;
    frequencies[194] = 50;
    frequencies[201] = 100;
    frequencies[210] = 50;

    for (int v = 0; v < 256; v++) {
      for (int h = 100 - 1; h > 100 - frequencies[v]; h--) {
        if (h > 100 - frequencies[v]) {
          Assert.assertEquals(Color.GREEN.getRGB(), hist.getRGB(v, h));
        } else {
          Assert.assertEquals(Color.BLACK.getRGB(), hist.getRGB(v, h));
        }
      }
    }
  }

  @Test
  public void testShowBlue() {
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File("res\\PNGs\\testPNG.png"));
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    }
    BufferedImage hist = new Histogram(image).showBlue();

    int[] frequencies = new int[256];
    frequencies[7] = 100;
    frequencies[12] = 100;
    frequencies[22] = 100;
    frequencies[163] = 100;
    frequencies[180] = 100;
    frequencies[206] = 100;
    frequencies[211] = 100;
    frequencies[213] = 100;
    frequencies[220] = 100;

    for (int v = 0; v < 256; v++) {
      for (int h = 100 - 1; h > 100 - frequencies[v]; h--) {
        if (h > 100 - frequencies[v]) {
          Assert.assertEquals(Color.BLUE.getRGB(), hist.getRGB(v, h));
        } else {
          Assert.assertEquals(Color.BLACK.getRGB(), hist.getRGB(v, h));
        }
      }
    }
  }

  @Test
  public void testShowIntensity() {
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File("res\\PNGs\\testPNG.png"));
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    }
    BufferedImage hist = new Histogram(image).showIntensity();

    int[] frequencies = new int[256];
    frequencies[64] = 50;
    frequencies[70] = 50;
    frequencies[80] = 50;
    frequencies[154] = 50;
    frequencies[171] = 50;
    frequencies[197] = 50;
    frequencies[204] = 100;
    frequencies[213] = 50;

    for (int v = 0; v < 256; v++) {
      for (int h = 100 - 1; h > 100 - frequencies[v]; h--) {
        if (h > 100 - frequencies[v]) {
          Assert.assertEquals(Color.GRAY.getRGB(), hist.getRGB(v, h));
        } else {
          Assert.assertEquals(Color.BLACK.getRGB(), hist.getRGB(v, h));
        }
      }
    }
  }
}