import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;

import model.IImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A class to hold tests for PPM Controller.
 */
public class ControllerTest {

  /**
   * Tests that the controller correctly manages Images based on input.
   */
  @Test
  public void testCommands() {
    HashMap<String, IImage> collection = new HashMap<String, IImage>();
    Appendable input = new StringBuilder(
            "load res/PPMs/TestImage.ppm testPPM " +
                    "load res/JPEGs/testJPG.jpg testJPG " +
                    "save res/BMPs/testBMP.bmp testJPG " +
                    "load res/BMPs/testBMP.bmp testBMP " +
                    "save res/PNGs/testPNG.png testBMP " +
                    "load res/PNGs/testPNG.png testPNG " +
                    "brighten 3 testBMP test-brighter " +
                    "vertical-flip testPNG test-vertical " +
                    "horizontal-flip testJPG test-horizontal " +
                    "value-component testPPM test-value " +
                    "intensity-component testBMP test-intensity " +
                    "luma-component testPNG test-luma " +
                    "red-component testJPG test-red " +
                    "green-component testPPM test-green " +
                    "blue-component testBMP test-blue " +
                    "blur testPNG test-blur " +
                    "sharpen testJPG test-sharpen " +
                    "greyscale testPPM test-greyscale " +
                    "sepia testBMP test-sepia " +
                    "save res/PPMs/TestControllerImage.ppm testPPM " +
                    "load res/PPMs/TestControllerImage.ppm saved " +
                    "quit");
    Readable userInput = new StringReader(input.toString());
    SimpleImageController controller = new SimpleImageController(collection, userInput);
    controller.execute();

    assertEquals(collection.get("testPPM").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "9\n" +
            " 3 5 1  2 1 0  0 7 6 \n" +
            " 0 4 5  9 3 8  2 5 1 \n" +
            " 3 8 1  0 1 2  3 1 2 \n");

    assertEquals(collection.get("testBMP").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 149 151 163  166 168 180  210 210 220 \n" +
            " 199 201 213  192 194 206  201 201 211 \n" +
            " 101 118 22  91 108 12  84 101 7 \n");

    assertEquals(collection.get("testPNG").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 149 151 163  166 168 180  210 210 220 \n" +
            " 199 201 213  192 194 206  201 201 211 \n" +
            " 101 118 22  91 108 12  84 101 7 \n");

    assertEquals(collection.get("testJPG").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 149 151 163  166 168 180  210 210 220 \n" +
            " 199 201 213  192 194 206  201 201 211 \n" +
            " 101 118 22  91 108 12  84 101 7 \n");

    assertEquals(collection.get("test-brighter").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 152 154 166  169 171 183  213 213 223 \n" +
            " 202 204 216  195 197 209  204 204 214 \n" +
            " 104 121 25  94 111 15  87 104 10 \n");

    assertEquals(collection.get("test-vertical").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 101 118 22  91 108 12  84 101 7 \n" +
            " 199 201 213  192 194 206  201 201 211 \n" +
            " 149 151 163  166 168 180  210 210 220 \n");

    assertEquals(collection.get("test-horizontal").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 210 210 220  166 168 180  149 151 163 \n" +
            " 201 201 211  192 194 206  199 201 213 \n" +
            " 84 101 7  91 108 12  101 118 22 \n");

    assertEquals(collection.get("test-value").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "9\n" +
            " 5 5 5  2 2 2  7 7 7 \n" +
            " 5 5 5  9 9 9  5 5 5 \n" +
            " 8 8 8  2 2 2  3 3 3 \n");

    assertEquals(collection.get("test-intensity").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 154 154 154  171 171 171  213 213 213 \n" +
            " 204 204 204  197 197 197  204 204 204 \n" +
            " 80 80 80  70 70 70  64 64 64 \n");

    assertEquals(collection.get("test-luma").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 151 151 151  168 168 168  210 210 210 \n" +
            " 201 201 201  194 194 194  201 201 201 \n" +
            " 107 107 107  97 97 97  90 90 90 \n");

    assertEquals(collection.get("test-red").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 149 149 149  166 166 166  210 210 210 \n" +
            " 199 199 199  192 192 192  201 201 201 \n" +
            " 101 101 101  91 91 91  84 84 84 \n");

    assertEquals(collection.get("test-green").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "9\n" +
            " 5 5 5  1 1 1  7 7 7 \n" +
            " 4 4 4  3 3 3  5 5 5 \n" +
            " 8 8 8  1 1 1  1 1 1 \n");

    assertEquals(collection.get("test-blue").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 163 163 163  180 180 180  220 220 220 \n" +
            " 213 213 213  206 206 206  211 211 211 \n" +
            " 22 22 22  12 12 12  7 7 7 \n");

    assertEquals(collection.get("test-blur").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 93 95 100  133 134 143  109 110 115 \n" +
            " 118 122 111  161 167 150  125 128 115 \n" +
            " 72 79 44  92 101 56  69 75 40 \n");

    assertEquals(collection.get("test-sharpen").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 198 196 251  255 255 255  255 255 255 \n" +
            " 255 255 255  255 255 255  255 255 255 \n" +
            " 120 140 33  221 246 106  104 123 16 \n");

    assertEquals(collection.get("test-greyscale").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "9\n" +
            " 4 4 4  1 1 1  5 5 5 \n" +
            " 3 3 3  4 4 4  4 4 4 \n" +
            " 6 6 6  0 0 0  1 1 1 \n");

    assertEquals(collection.get("test-sepia").toString(), "" +
            "P3\n" +
            "3 3\n" +
            "255\n" +
            " 205 182 142  228 203 158  255 254 198 \n" +
            " 255 243 189  255 234 182  255 243 189 \n" +
            " 134 119 93  121 107 83  112 99 77 \n");
  }


  /**
   * Checks that the controller throws an exception when invalid input is entered.
   */
  @Test
  public void testException() {
    HashMap<String, IImage> collection = new HashMap<String, IImage>();
    Appendable input = new StringBuilder("jhdiksd");
    Readable userInput = new StringReader(input.toString());
    SimpleImageController controller = new SimpleImageController(collection, userInput);

    try {
      controller.execute();
      fail("Did not throw exception");
    } catch (IllegalStateException e) {
      assertEquals("Invalid commands entered via input: jhdiksd", e.getMessage());
    }
  }

  /**
   * Checks that the controller throws an exception when null image map is passed to constructor.
   */
  @Test
  public void testConstructor1() {
    Appendable input = new StringBuilder("quit");
    Readable userInput = new StringReader(input.toString());

    try {
      SimpleImageController controller = new SimpleImageController(null, userInput);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot create a SimpleImageController with null images or input", e.getMessage());
    }
  }

  /**
   * Checks that the controller throws an exception when null input is passed to constructor.
   */
  @Test
  public void testConstructor2() {
    HashMap<String, IImage> collection = new HashMap<String, IImage>();
    Appendable input = new StringBuilder("quit");

    try {
      SimpleImageController controller = new SimpleImageController(collection, null);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot create a SimpleImageController with null images or input", e.getMessage());
    }
  }

  /**
   * Checks that the controller throws an exception when null input is passed to constructor.
   */
  @Test
  public void testConstructor3() {

    try {
      SimpleImageController controller = new SimpleImageController(null);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot create a SimpleImageController with null images or input", e.getMessage());
    }
  }
}
