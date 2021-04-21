package linearADTs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SequentialSetTest {
  @Test
  void addAndContainsTest() {
    SequentialSet<Integer> integers = getSampleIntegerSet();
    for (int i = 0; i < 5; i++) {
      Assertions.assertTrue(integers.contains(i));
    }
  }

  @Test
  void removeTest() {
    SequentialSet<Integer> integers = getSampleIntegerSet();
    Assertions.assertTrue(integers.contains(4));
    integers.remove(4);
    Assertions.assertFalse(integers.contains(4));
  }

  @Test
  void sizeTest() {
    SequentialSet<Integer> integers = getSampleIntegerSet();
    Assertions.assertEquals(5, integers.size());
  }

  private SequentialSet<Integer> getSampleIntegerSet() {
    SequentialSet<Integer> integers = new SequentialSet<>();
    for (int i = 0; i < 5; i++) {
      integers.add(i);
    }
    return integers;
  }
}
