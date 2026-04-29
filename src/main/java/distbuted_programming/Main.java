package distbuted_programming;

import distbuted_programming.controller.AppController;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    AppController controller = new AppController();
    controller.run(scanner);
    scanner.close();
  }
}
