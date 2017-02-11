public class Array {

  private int values[] = new int[10];
  private int count = 0;

  public void add(int number) {
    if (values.length == count) {
      int[] newValues = new int[values.length * 2];
      System.arraycopy(values, 0, newValues, 0, count);
      values = newValues;
    }
    values[count++] = number;
  }

  public int size() {
    return count;
  }

  public int capacity() {
    return values.length;
  }

  public void remove(int index) {
    if (index < count) {
      if (count <= values.length / 4) {
        int[] newValues = new int[values.length / 2];
        System.arraycopy(values, 0, newValues, 0, count);
        values = newValues;
      }
      System.arraycopy(values, index + 1, values, index, count - index - 1);

      count--;
    } else {
      throw new IndexOutOfBoundsException();

    }
  }

  public int get(int i) {
    if (i<count){
      return values [i];
    }else {
      throw new IndexOutOfBoundsException();
    }
  }
}

