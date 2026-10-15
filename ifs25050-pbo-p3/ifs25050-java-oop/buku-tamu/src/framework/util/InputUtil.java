package framework.util;

import java.util.Scanner;

/**
 * Utilitas untuk membaca input dari konsol.
 */
public class InputUtil {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String input(String prompt) {
        System.out.print(prompt + " : ");
        return SCANNER.nextLine().trim();
    }

    public static String inputRaw(String prompt) {
        System.out.print(prompt + " : ");
        return SCANNER.nextLine();
    }
}
