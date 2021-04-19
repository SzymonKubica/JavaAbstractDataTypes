package linearADTs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListLinkedNodeBasedTest {
  @Test
  void isEmptyTest() {
    ListLinkedNodeBased<Integer> list = getSampleList();
    Assertions.assertFalse(list.isEmpty());
    ListLinkedNodeBased<Integer> list2 = new ListLinkedNodeBased<>();
    Assertions.assertTrue(list2.isEmpty());
  }

  @Test
  void sizeTest() {
    ListLinkedNodeBased<Integer> list = getSampleList();
    Assertions.assertEquals(list.size(), 10);
  }

  @Test
  void getTest() {
    ListLinkedNodeBased<Integer> list = getSampleList();
    Assertions.assertEquals((int) list.get(3), 3);
  }

  @Test
  void addTest() {
    ListLinkedNodeBased<Integer> list = getSampleList();
    list.add(7, 17);

    Assertions.assertEquals((int) list.get(7), 17);
    Assertions.assertEquals((int) list.get(8), 7);

    list.add(19);
    Assertions.assertEquals((int) list.get(0), 19);
    Assertions.assertEquals((int) list.get(1), 0);
  }

  @Test
  void addTestBigList() {
    ListLinkedNodeBased<Integer> list = getBigList();
    list.add(0, 111);
    Assertions.assertEquals((int) list.get(100), 99);
    Assertions.assertEquals(list.size(), 101);
  }

  @Test
  void remove() {
    ListLinkedNodeBased<Integer> list = getSampleList();
    list.remove(4);
    Assertions.assertNotEquals((int) list.get(4), 4);
    Assertions.assertEquals((int) list.get(4), 5);
  }

  private static ListLinkedNodeBased<Integer> getSampleList() {
    ListLinkedNodeBased<Integer> list = new ListLinkedNodeBased<>();
    for (int i = 0; i < 10; i++) {
      list.add(i, i);
    }
    return list;
  }

  private static ListLinkedNodeBased<Integer> getBigList() {
    ListLinkedNodeBased<Integer> list = new ListLinkedNodeBased<>();
    for (int i = 0; i < 100; i++) {
      list.add(i, i);
    }
    return list;
  }
}
