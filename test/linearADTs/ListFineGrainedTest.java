package linearADTs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListFineGrainedTest {
  @Test
  void isEmptyTest() {
    ListFineGrained<Integer> list = getSampleList();
    Assertions.assertFalse(list.isEmpty());
    ListFineGrained<Integer> list2 = new ListFineGrained<>();
    Assertions.assertTrue(list2.isEmpty());
  }

  @Test
  void sizeTest() {
    ListFineGrained<Integer> list = getSampleList();
    Assertions.assertEquals(list.size(), 10);
  }

  @Test
  void getTest() {
    ListFineGrained<Integer> list = getSampleList();
    Assertions.assertEquals((int) list.get(3), 3);
  }

  @Test
  void addTest() {
    ListFineGrained<Integer> list = getSampleList();
    list.add(7, 17);

    Assertions.assertEquals((int) list.get(7), 17);
    Assertions.assertEquals((int) list.get(8), 7);

    list.add(19);
    Assertions.assertEquals((int) list.get(0), 19);
    Assertions.assertEquals((int) list.get(1), 0);
  }

  @Test
  void addTestBigList() {
    ListFineGrained<Integer> list = getBigList();
    list.add(0, 111);
    Assertions.assertEquals((int) list.get(100), 99);
    Assertions.assertEquals(list.size(), 101);
  }

  @Test
  void remove() {
    ListFineGrained<Integer> list = getSampleList();
    list.remove(4);
    Assertions.assertNotEquals((int) list.get(4), 4);
    Assertions.assertEquals((int) list.get(4), 5);
  }

  private static ListFineGrained<Integer> getSampleList() {
    ListFineGrained<Integer> list = new ListFineGrained<>();
    for (int i = 0; i < 10; i++) {
      list.add(i, i);
    }
    return list;
  }

  private static ListFineGrained<Integer> getBigList() {
    ListFineGrained<Integer> list = new ListFineGrained<>();
    for (int i = 0; i < 100; i++) {
      list.add(i, i);
    }
    return list;
  }
}
