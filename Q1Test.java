import java.util.ArrayList;
import java.util.List;


public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3) {
          int[][] t;
          try {
               t = new int[][] { parse(traversal1), parse(traversal2), parse(traversal3) };
          } catch (NumberFormatException e) {
               return "Invalid traversals";
          }

          // all traversals must have the same number of nodes
          if (t[0].length != t[1].length || t[1].length != t[2].length)
               return "Invalid traversals";

          // try every assignment of (inorder, preorder, postorder) to the 3 strings
          int[][] perms = { { 0, 1, 2 }, { 0, 2, 1 }, { 1, 0, 2 }, { 1, 2, 0 }, { 2, 0, 1 }, { 2, 1, 0 } };
          for (int[] p : perms) {
               if (isValid(t[p[0]], t[p[1]], t[p[2]])) {
                    String[] names = new String[3];
                    names[p[0]] = "Inorder";
                    names[p[1]] = "Preorder";
                    names[p[2]] = "Postorder";
                    return "Traversal 1 - " + names[0] + ", Traversal 2 - " + names[1]
                              + ", Traversal 3 - " + names[2];
               }
          }
          return "Invalid traversals";
     }

     private static int[] parse(String s) {
          String[] parts = s.trim().split("-");
          int[] nums = new int[parts.length];
          for (int i = 0; i < parts.length; i++)
               nums[i] = Integer.parseInt(parts[i].trim());
          return nums;
     }

     private static boolean isValid(int[] in, int[] pre, int[] post) {
          // inorder of a BST must be strictly ascending
          for (int i = 1; i < in.length; i++)
               if (in[i - 1] >= in[i])
                    return false;

          // a BST is uniquely determined by its preorder: build it by insertion
          Node root = null;
          for (int v : pre)
               root = insert(root, v);

          List<Integer> actualIn = new ArrayList<>();
          List<Integer> actualPre = new ArrayList<>();
          List<Integer> actualPost = new ArrayList<>();
          inorder(root, actualIn);
          preorder(root, actualPre);
          postorder(root, actualPost);

          // duplicates in pre would make the tree smaller than the input
          return matches(actualIn, in) && matches(actualPre, pre) && matches(actualPost, post);
     }

     private static boolean matches(List<Integer> list, int[] arr) {
          if (list.size() != arr.length)
               return false;
          for (int i = 0; i < arr.length; i++)
               if (list.get(i) != arr[i])
                    return false;
          return true;
     }

     private static Node insert(Node node, int v) {
          if (node == null)
               return new Node(v);
          if (v < node.value)
               node.left = insert(node.left, v);
          else if (v > node.value)
               node.right = insert(node.right, v);
          return node;
     }

     private static void inorder(Node n, List<Integer> out) {
          if (n == null)
               return;
          inorder(n.left, out);
          out.add(n.value);
          inorder(n.right, out);
     }

     private static void preorder(Node n, List<Integer> out) {
          if (n == null)
               return;
          out.add(n.value);
          preorder(n.left, out);
          preorder(n.right, out);
     }

     private static void postorder(Node n, List<Integer> out) {
          if (n == null)
               return;
          postorder(n.left, out);
          postorder(n.right, out);
          out.add(n.value);
     }

     private static class Node {
          int value;
          Node left, right;

          Node(int value) {
               this.value = value;
          }
     }
}