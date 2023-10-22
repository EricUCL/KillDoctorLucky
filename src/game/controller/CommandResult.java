package game.controller;

/**
 * Represents the result of a command executed in the game context. The result includes a message
 * detailing the outcome and a flag indicating if the outcome is an error.
 */
public class CommandResult {

  private final String message;

  private final boolean isError;

  /**
   * Constructs a new command result with the specified message and error flag.
   *
   * @param message The message detailing the outcome of the command.
   * @param isError Flag indicating if the command resulted in an error.
   */
  public CommandResult(String message, boolean isError) {
    this.message = message;
    this.isError = isError;
  }

  /**
   * Returns the message detailing the outcome of the command.
   *
   * @return The outcome message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Checks if this command result indicates an error.
   *
   * @return {@code true} if the command resulted in an error; {@code false} otherwise.
   */
  public boolean isError() {
    return isError;
  }
}
