import java.util.Scanner;

public class Main {
    // String[] args массив всех аргументов
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        String name = Hero(console);
        String classHero = ClassHero(console);
        int level = Level(console);
        boolean shield = Shield(console);

        if (!checkAccess(level, classHero, shield)) {
            console.close();
            return;
        }
        System.out.println("Допущен");

        int maxHp = 40;
        int healAmount = 8;
        int xpReward = 20;
        int heroPower = 10;

        String[] enemyNames = {"Крыса", "Скелет", "Страж"};
        int[] enemyHpArr = {12, 18, 24};
        int[] enemyDamageArr = {4, 6, 8};

        int heroHp = maxHp;
        int xp = 0;
        int wins = 0;
        boolean gameOver = false;
        String result = "";
        //бой1
        for (int i = 0; i < enemyNames.length && !gameOver; i++) {
            int enemyHp = enemyHpArr[i];
            int enemyDamage = enemyDamageArr[i];
            String enemyName = enemyNames[i];
            boolean healed = false;

            System.out.println("Бой с противником: " + enemyName);

            while (heroHp > 0 && enemyHp > 0) {
                System.out.println("HP героя: " + heroHp + " / HP " + enemyName + ": " + enemyHp);
                int command = readCommand(console);

                if (command == 1) {
                    enemyHp = attack(enemyHp, heroPower);
                    if (enemyHp > 0) {
                        heroHp = attack(heroHp, enemyDamage);
                    }
                } else if (command == 2) {
                    if (!healed) {
                        heroHp = heal(heroHp, healAmount, maxHp);
                        healed = true;
                        if (enemyHp > 0) {
                            heroHp = attack(heroHp, enemyDamage);
                        }
                    } else {
                        System.out.println("Лечение уже использовано в этом бою");
                    }
                } else if (command == 0) {
                    result = "Игра прервана";
                    gameOver = true;
                } else {
                    System.out.println("Неизвестная команда");
                }

                if (gameOver) {
                    break;
                }
                if (heroHp <= 0) {
                    result = "Поражение";
                    gameOver = true;
                }
            }

            if (!gameOver && enemyHp <= 0) {
                xp = xp + xpReward;
                wins = wins + 1;
                System.out.println(enemyName + " побеждён");
            }
        }

        if (result.equals("")) {
            if (wins == enemyNames.length) {
                result = "Победа на арене";
            } else {
                result = "Поражение";
            }
        }

        System.out.println("Игрок: " + name);
        System.out.println("Побед: " + wins);
        System.out.println("Опыт: " + xp);
        System.out.println("HP героя: " + heroHp);
        System.out.println("Итог: " + result);

        console.close();
    }

    private static String Hero(Scanner console) {
        String name = "";
        boolean ok = false;
        while (!ok) {
            System.out.println("Введите ваше имя");
            name = console.nextLine().strip();
            if (name.length() >= 1 && name.length() <= 30) {
                ok = true;
            } else {
                System.out.println("Имя не меньше 1 и не больше 30 символов, введите заново");
            }
        }
        return name;
    }

    private static String ClassHero(Scanner console) {
        String classHero = "";
        boolean ok = false;
        while (!ok) {
            System.out.println("Введите класс героя: воин, маг или лучник");
            String input = console.nextLine().strip().toLowerCase();
            switch (input) {
                case "воин":
                    classHero = "воин";
                    ok = true;
                    break;
                case "маг":
                    classHero = "маг";
                    ok = true;
                    break;
                case "лучник":
                    classHero = "лучник";
                    ok = true;
                    break;
                default:
                    System.out.println("Такого класса нет, попробуйте снова");
            }
        }
        return classHero;
    }

    private static int Level(Scanner console) {
        int level = 0;
        boolean ok = false;
        while (!ok) {
            System.out.println("Введите уровень от 1 до 80");
            if (console.hasNextInt()) {
                level = console.nextInt();
                console.nextLine();
                if (level >= 1 && level <= 80) {
                    ok = true;
                } else {
                    System.out.println("Уровень должен быть от 1 до 80");
                }
            } else {
                System.out.println("Это не число, попробуйте снова");
                console.nextLine();
            }
        }
        return level;
    }

    private static boolean Shield(Scanner console) {
        while (true) {
            System.out.println("Есть щит? да/нет");
            String input = console.nextLine().strip().toLowerCase();
            if (input.equals("да")) {
                return true;
            }
            if (input.equals("нет")) {
                return false;
            }
            System.out.println("Ответьте да или нет");
        }
    }

    private static boolean checkAccess(int level, String classHero, boolean shield) {
        if (level < 10) {
            System.out.println("Отказ: недостаточный уровень");
            return false;
        }

        if (!shield && !classHero.equals("маг")) {
            System.out.println("Отказ: нужен щит или класс маг");
            return false;
        }

        return true;
    }

    private static int readCommand(Scanner console) {
        while (true) {
            System.out.println("Команда: 1 - атаковать, 2 - лечиться, 0 - закончить игру");
            if (console.hasNextInt()) {
                int command = console.nextInt();
                console.nextLine();
                return command;
            } else {
                System.out.println("Это не число, попробуйте снова");
                console.nextLine();
            }
        }
    }

    private static int attack(int hp, int power) {
        return Math.max(0, hp - power);
    }

    private static int heal(int hp, int amount, int maxHp) {
        return Math.min(maxHp, hp + amount);
    }
}