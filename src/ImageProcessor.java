import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Allows an image processing program that works on various image formats (jpg, png, ppm, bmp)
 * to be run.
 */
public class ImageProcessor {
  /**
   * Runs a new controller with no preloaded images using the command line (System.in) as the
   * input source. Defaults to a GUI controller but can be changed with arguments.
   *
   * @param args the arguments (supports "-file name-of-script.txt" to load script text files as
   *             the input rather than typed user input, and "-text" to load the file through a
   *             text command interface rather than the GUI)
   */
  public static void main(String[] args) {
    InputStreamReader input = new InputStreamReader(System.in);

    if (args.length > 1 && args[0].equals("-file")) {
      if (!args[1].substring(args[1].length() - 4).equals(".txt")) {
        throw new IllegalArgumentException("trying to read script from non text file");
      }
      try {
        input = new InputStreamReader(new FileInputStream(args[1]));
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      ImageController controller = new SimpleImageController(input);
      controller.execute();
    } else if (args.length > 0 && args[0].equals("-text")) {
      ImageController controller = new SimpleImageController(input);
      controller.execute();
    } else {
      GUIController controller = new GUIController();
      controller.execute();
    }
  }
}
