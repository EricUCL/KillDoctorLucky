package game.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * A utility class to generate random numbers. It allows for both truly random number generation and
 * pseudo-random generation based on predefined sequences.
 *
 * <p>In scenarios where deterministic results are preferred (e.g., testing), a sequence of
 * predefined numbers can be used. When retrieving a number, it will sequentially return numbers
 * from the predefined list. Once all predefined numbers have been consumed, or if no predefined
 * numbers are set, it will return truly random numbers.</p>
 */
public class RandomGenerator {

  /** Internal random number generator. */
  private final Random random;

  /** Queue of predefined numbers to be returned in sequence. */
  private Queue<Integer> predefinedNumbers = null;

  /**
   * Default constructor which initializes a truly random number generator.
   */
  public RandomGenerator() {
    random = new Random();
  }

  /**
   * Constructor that initializes the random generator with a sequence of predefined numbers.
   *
   * @param numbers A sequence of predefined numbers that the generator will return in order.
   */
  public RandomGenerator(int... numbers) {
    random = new Random();
    predefinedNumbers = new LinkedList<>();
    for (int num : numbers) {
      predefinedNumbers.add(num);
    }
  }

  /**
   * Retrieves a random number within the specified range. If there are predefined numbers
   * available, it returns the next number from the sequence. Otherwise, it generates a truly random
   * number.
   *
   * @param min The lower bound (inclusive) of the random number to be generated.
   * @param max The upper bound (inclusive) of the random number to be generated.
   * @return A random number between the specified range.
   */
  public int getRandomNumberInRange(int min, int max) {
    if (predefinedNumbers == null || predefinedNumbers.isEmpty()) {
      return random.nextInt((max - min) + 1) + min;
    } else {
      return predefinedNumbers.poll();
    }
  }
}
