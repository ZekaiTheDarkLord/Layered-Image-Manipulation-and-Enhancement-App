package imagecontroller;

/**
 * The controller for a image processor. It can receive several command and modify the pictures and
 * layers in respect of commands.
 */
public interface ImageController {


  /**
   * Recieve as a file path for the command.
   *
   * @param path The path of file
   */
  void accepctCommandsInFile(String path);

  /**
   * Receive a command.
   * @param commands the command line
   */
  void acceptCommands(Readable commands);

}
