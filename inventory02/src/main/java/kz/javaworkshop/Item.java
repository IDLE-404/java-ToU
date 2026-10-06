package kz.javaworkshop;

public class Item {
    private final String name;
    private final String type;
    private final int weightGrams;
    private final int value;

    public static boolean isValidType(String type){
        return "weapon".equals(type) || "armor".equals(type) || "potion".equals(type);
    }

    public Item(String name, String type, int weightGrams, int value) {
        if (name == null || name.strip().isEmpty()){
            throw new IllegalArgumentException("Имя предмета пусто");
        }
        String normalized = name.strip();
        if(normalized.length() > 40){
            throw new IllegalArgumentException("Имя длиннее 40 символов");
        }
        if (!isValidType(type)){
            throw new IllegalArgumentException("Тип должен быть weapon, armor или potion");
        }
        if(weightGrams < 1 || weightGrams > 100000){
            throw new IllegalArgumentException("Масса должна быть 1 до 100000 г");
        }
        if(value < 0 || value > 1000000){
            throw new IllegalArgumentException("Стоимость должна быть от 0 до 1000000");
        }
        this.name = normalized;
        this.type = type;
        this.value = value;
        this.weightGrams = weightGrams;
    }

    public String getName() {
        return name;
    }
        public String getType() {
        return type;
    }
        public int getWeightGrams() {
        return weightGrams;
    }
        public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + " [" + type + "], " + weightGrams + " г, " + value + " монет";
    }



}