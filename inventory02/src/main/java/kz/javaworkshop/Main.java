package kz.javaworkshop;

import java.util.Scanner;
public class Main {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        Inventory inventory = new Inventory(4, 6000);
        inventory.add(new Item("Меч", "weapon", 2500, 100));
        inventory.add(new Item("Щит", "armor", 3000, 80));
        inventory.add(new Item("Зелье", "potion", 500, 20));

        while (true) {
            System.out.println();
            System.out.println("1 добавить | 2 показать | 3 удалить | 4 использовать зелье");
            System.out.println("5 сводка   | 6 поиск    | 0 выйти");
            int choice = readInt("Выбор: ");
            switch (choice) {
                case 1 -> addItem(inventory);
                case 2 -> show(inventory);
                case 3 -> removeItem(inventory);
                case 4 -> usePotion(inventory);
                case 5 -> summary(inventory);
                case 6 -> search(inventory);
                case 0 -> {
                    System.out.println("Выход");
                    return;
                }
                default -> System.out.println("Нет такой команды");
            }
        }
    }

    private static void addItem(Inventory inventory) {
        System.out.print("Имя: ");
        String name = in.nextLine();
        String type = readType();
        int grams = readInt("Масса, г: ");
        int value = readInt("Стоимость, монет: ");
        try {
            Item item = new Item(name, type, grams, value);
            boolean added = inventory.add(item);
            System.out.println(added ? "Добавлено" : "Нет места или массы");
        } catch (IllegalArgumentException ex) {
            System.out.println("Данные предмета: " + ex.getMessage());
        }
    }

    private static void show(Inventory inventory) {
        Item[] items = inventory.snapshot();
        if (items.length == 0) {
            System.out.println("Инвентарь пуст");
            return;
        }
        for (int i = 0; i < items.length; i++) {
            System.out.println((i + 1) + ". " + items[i]);
        }
    }

    private static void removeItem(Inventory inventory) {
        int number = readInt("Номер предмета: ");
        Item removed = inventory.remove(number - 1);
        System.out.println(removed != null ? "Удалено: " + removed : "Нет предмета с таким номером");
    }

    private static void usePotion(Inventory inventory) {
        int index = readInt("Номер предмета: ") - 1;
        Item item = inventory.get(index);
        if (item == null) {
            System.out.println("Нет предмета с таким номером");
        } else if ("potion".equals(item.getType())) {
            inventory.remove(index);
            System.out.println("Зелье использовано");
        } else {
            System.out.println("Это не зелье, ничего не изменилось");
        }
    }

    private static void summary(Inventory inventory) {
        System.out.printf("Ячейки: %d/%d; масса: %d/%d; стоимость: %d%n",
                inventory.size(), inventory.capacity(),
                inventory.totalWeightGrams(), inventory.getMaxWeightGrams(),
                inventory.totalValue());
    }

    private static void search(Inventory inventory) {
        System.out.print("Часть имени: ");
        Item[] found = inventory.findByName(in.nextLine());
        if (found.length == 0) {
            System.out.println("Ничего не найдено");
        }
        for (Item item : found) {
            System.out.println("- " + item);
        }
    }
    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!in.hasNextInt()) {
            System.out.println("Введите целое число");
            in.nextLine();
            System.out.print(prompt);
        }
        int number = in.nextInt();
        in.nextLine();
        return number;
    }

    private static String readType() {
        while (true) {
            System.out.print("Тип (weapon/armor/potion): ");
            String type = in.nextLine().strip().toLowerCase();
            if (Item.isValidType(type)) {
                return type;
            }
            System.out.println("Допустимы только weapon, armor, potion");
        }
    }
}