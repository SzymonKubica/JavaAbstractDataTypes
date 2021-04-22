package adts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoarseGrainedLinkedSetTest {

  // The following tests exercise the regular sequential functionality of the CoarseGrainedLinkedSet.
  @Test
  void addAndContainsTest() {
    CoarseGrainedLinkedSet<Integer> integers = getSampleIntegerSet();
    for (int i = 0; i < 5; i++) {
      Assertions.assertTrue(integers.contains(i));
    }
  }

  @Test
  void removeTest() {
    CoarseGrainedLinkedSet<Integer> integers = getSampleIntegerSet();
    Assertions.assertTrue(integers.contains(4));
    integers.remove(4);
    Assertions.assertFalse(integers.contains(4));
  }

  @Test
  void sizeTest() {
    SequentialSet<Integer> integers = getSampleIntegerSet();
    Assertions.assertEquals(5, integers.size());
  }

  private CoarseGrainedLinkedSet<Integer> getSampleIntegerSet() {
    CoarseGrainedLinkedSet<Integer> integers = new CoarseGrainedLinkedSet<>();
    for (int i = 0; i < 5; i++) {
      integers.add(i);
    }
    return integers;
  }
}
