package Usachev.Maxim.CaesarCrypto;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Cipher {

    private static final String RUS_SYMBOLS = "абвгдежзийклмнопрстуфхцчшщъыьэюя .,”:-!?";
    public static int alphaLength = RUS_SYMBOLS.length();


    private static String encrypt(String string, int key) {
        string = string.toLowerCase();
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            int charPosition = RUS_SYMBOLS.indexOf(string.charAt(i));
            if (charPosition != -1) {
                int keyVal = (charPosition + key) % alphaLength;
                char replaceVal = RUS_SYMBOLS.charAt(keyVal);
                cipherText.append(replaceVal);
            } else {
                cipherText.append(string.charAt(i));
            }
        }
        return cipherText.toString();
    }

    public static void getEncFile() {
        int key = getKey();
        writeResultFile(Menu.getInputFile(), key);
    }

    public static void getDecFile() {
            int key = (alphaLength - getKey()) % alphaLength;
            if (key < 0) {
                key = (alphaLength + key) % alphaLength;
            }
        writeResultFile(Menu.getInputFile(), key);
    }

    public static void bruteForce() {
        boolean isRead = false;
        List<String> list = Menu.getInputFile();
        int key;
        for (key = 1; key < alphaLength; key++) {
            for (String s: list) {
                if (s.length() > 40) {
                    String temp = encrypt(s, key);
                    if (isReadable(temp)) {
                        isRead = true;
                        break;
                    }
                }
            }
            if (isRead) {
                break;
            }
        }
        System.out.println("Ключ: " + (alphaLength - key));
        writeResultFile(list, key);
    }

    private static boolean isReadable(String text) {
        String[] s = text.split(" ");
        for (String value : s) {
            if (value.length() > 25) {
                return false;
            }
        }
        return true;
    }

    private static int getKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ключ шифрования:");
        return scanner.nextInt();
    }

    private static void writeResultFile(List<String> list, int key) {
        try (BufferedWriter writer = Files.newBufferedWriter(Menu.getOutputPath())) {
            for (String s : list) {
                writer.write(encrypt(s, key) + "\n");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            System.out.println("Файл готов!");
        }
    }


    private static HashMap<Character, Integer> getStat(String s) {
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Integer integer = hashMap.get(c);
            if (integer == null) {
                hashMap.put(c, 1);
            } else {
                integer++;
                hashMap.put(c, integer);
            }
        }
        return hashMap;
    }
}
