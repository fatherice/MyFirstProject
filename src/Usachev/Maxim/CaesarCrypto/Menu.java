package Usachev.Maxim.CaesarCrypto;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final String HELLO = "Что необходимо сделать? Укажите номер.";
    private static final String CIPHER = "1. Зашифровать файл.";
    private static final String DECIPHER = "2. Расшифровать файл.";
    private static final String BRUTEFORCE = "3. Подобрать шифр (brute force).";
    private static final String STATIC_ANALYSIS = "4. Подобрать шифр методом статистического анализа. (В разработке)";
    private static final String EXIT = "0. Выход.";

    public static void run() {
        int i = getMenuChoice();
        if (i == 1) {
            Cipher.getEncFile();
        } if (i == 2) {
            Cipher.getDecFile();
        } if (i == 3) {
            Cipher.bruteForce();
        } if (i == 4) {
            System.out.println("В разработке!");
        }else if (i == 0) {
            System.exit(0);
        }
    }

    private static int getMenuChoice() {
        Scanner scanner = new Scanner(System.in);
        int i;
        showMenu();
        while (true) {
            String choice = scanner.nextLine();
            try {
                i = Integer.parseInt(choice);
                if (i >= 0 && i < 5) {
                    return i;
                }
            } catch (NumberFormatException n) {
                System.out.println("Введите номер существующего раздела или нажмите 0 для выхода!" + "\n");
                showMenu();
            }
        }
    }

    public static Path getOutputPath() throws IOException {
        String chooseOutputFile = "Куда запишем результаты?:";
        System.out.println(chooseOutputFile);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Path path = Paths.get(scanner.nextLine());
            if (!Files.isRegularFile(path)) {
                System.out.println("Файл не существует. Создание...");
                try {
                    Files.createDirectories(path.getParent());
                    Files.createFile(path);
                    System.out.println("Файл создан успешно");
                    return path;
                } catch (FileSystemException e) {
                    System.out.println("Введен не верный путь, попробуйте снова!");
                }
            } else {
                return path;
            }
        }
    }

    public static List<String> getInputFile() {
        System.out.println("Укажите путь к исходному файлу:");
        Scanner scanner = new Scanner(System.in);
        Path path = Paths.get(scanner.nextLine());
        while (!Files.isRegularFile(path)) {
            System.out.println("Файл не существует. Введите корректный путь.");
            path = Paths.get(scanner.nextLine());
        }
        List<String> list = null;
        try {
            list = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void showMenu() {
            System.out.println(HELLO + "\n\n" + CIPHER + "\n" + DECIPHER + "\n" + BRUTEFORCE
                    + "\n" + STATIC_ANALYSIS + "\n" + EXIT);
    }

}
