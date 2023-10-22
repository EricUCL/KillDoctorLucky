package game.controller;

/**
 * Represents a request for a parameter in the context of a game. Each request consists of a
 * parameter name and an associated prompt message.
 */
public class ParameterRequest {

  private final String paramName;

  private final String promptMessage;

  /**
   * Constructs a new parameter request with the specified parameter name and prompt message.
   *
   * @param paramName     The name of the parameter being requested.
   * @param promptMessage The message to prompt the user for this parameter.
   */
  public ParameterRequest(String paramName, String promptMessage) {
    this.paramName = paramName;
    this.promptMessage = promptMessage;
  }

  /**
   * Returns the name of the parameter being requested.
   *
   * @return The parameter name.
   */
  public String getParamName() {
    return paramName;
  }

  /**
   * Returns the prompt message associated with this parameter request.
   *
   * @return The prompt message.
   */
  public String getPromptMessage() {
    return promptMessage;
  }
}
