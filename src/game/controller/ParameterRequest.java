package game.controller;

public class ParameterRequest {
  private final String paramName;
  private final String promptMessage;

  public ParameterRequest(String paramName, String promptMessage) {
    this.paramName = paramName;
    this.promptMessage = promptMessage;
  }

  public String getParamName() {
    return paramName;
  }

  public String getPromptMessage() {
    return promptMessage;
  }
}