package game.controller;

public class CommandResult {

  private final String message;
  private final boolean isError;

  // Constructor for the CommandResult
  public CommandResult(String message, boolean isError) {
    this.message = message;
    this.isError = isError;
  }

  // Getter for the message.
  public String getMessage() {
    return message;
  }

  // Checks if this result is an error.
  public boolean isError() {
    return isError;
  }
}
