package game.utils;

/**
 * Represents the result of an operation in the game, encapsulating success status and details of
 * the operation.
 */
public class OperationResult {
  private final boolean isSuccess;
  private final String details;

  /**
   * Constructs a new OperationResult with specified success status and details.
   *
   * @param isSuccess true if the operation was successful, false otherwise
   * @param details   the details or message about the operation
   */
  public OperationResult(boolean isSuccess, String details) {
    this.isSuccess = isSuccess;
    this.details = details;
  }

  /**
   * Checks if the operation was successful.
   *
   * @return true if the operation was successful, false otherwise
   */
  public boolean isSuccess() {
    return isSuccess;
  }

  /**
   * Retrieves the details of the operation.
   *
   * @return a string containing the details of the operation
   */
  public String getDetails() {
    return details;
  }

  /**
   * Returns a string representation of the OperationResult.
   *
   * @return a string in the format "OperationResult{isSuccess=VALUE, details='DETAILS'}"
   */
  @Override
  public String toString() {
    return "OperationResult{" + "isSuccess=" + isSuccess + ", details='" + details + '\'' + '}';
  }
}
