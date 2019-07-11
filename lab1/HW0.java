/* Exercise 1a
*/

public class DrawStar {
  public static void main(String[] args) {
    String star = "";
    int i = 1;
    while (i <= 5) {
      star = star + "*";
      System.out.println(star);
      i = i + 1;
    }
  }
}


/* Exercise 2
*/

public class ClassNameHere {
   public static int max(int[] m) {
     int n = 0;
     int i = 0;
     while (i < m.length) {
       if (n < m[i]) {
         n = m[i];
       }
       i = i + 1;
     }
       return n;
   }
   public static void main(String[] args) {
      int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
      System.out.println(max(numbers));
   }
}

/* Exercise 3
*/

public class ClassNameHere {
   public static int max(int[] m) {
     int n = 0;
     for (int i = 0; i < m.length; i += 1) {
       if (n < m[i]) {
         n = m[i];
       }
     }
       return n;
   }
   public static void main(String[] args) {
      int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
      System.out.println(max(numbers));
   }
}

/* Exercise 4
*/

public class BreakContinue {
  public static void windowPosSum(int[] a, int n) {
    for (int i = 0; i < a.length; i += 1) {
      if (a[i] < 0) {
        continue;
      }
      int sum = 0;
      for (int j = i; j <= i + n; j += 1) {
        if (j > a.length - 1) {
          break;
        }
        sum += a[j];
      }
      a[i] = sum;
    }
  }

  public static void main(String[] args) {
    int[] a = {1, 2, -3, 4, 5, 4};
    int n = 3;
    windowPosSum(a, n);

    // Should print 4, 8, -3, 13, 9, 4
    System.out.println(java.util.Arrays.toString(a));
  }
}
