package kz.javaworkshop;

import java.util.Arrays;

public class Inventory {
    private final Item[] items;
    private int size;
    private final int maxWeightGrams;

    public Inventory(int slots, int maxWeightGrams) {
        if (slots < 1 || slots > 20) {
            throw new IllegalArgumentException("Число ячеек должно быть от 1 до 20");
        }
        if (maxWeightGrams < 1 || maxWeightGrams > 100_000) {
            throw new IllegalArgumentException("Лимит массы должен быть от 1 до 100000 г");
        }
        this.items = new Item[slots];
        this.maxWeightGrams = maxWeightGrams;
        this.size = 0;
    }
    public boolean add(Item item) {
        if (item == null) {
            return false;
        }
        if (size == items.length) {
            return false;
        }
        if (totalWeightGrams() + item.getWeightGrams() > maxWeightGrams) {
            return false;
        }
        items[size] = item;
        size++;
        return true;
    }
    public Item remove(int index) {
        if (!isValidIndex(index)) {
            return null;
        }
        Item removed = items[index];
        for (int i = index; i < size - 1; i++) {
            items[i] = items[i + 1];
        }
        size--;
        items[size] = null;
        return removed;
    }

    public Item get(int index) {
        return isValidIndex(index) ? items[index] : null;
    }
    public int size() {
        return size;
    }
    public int capacity() {
        return items.length;
    }
    public int getMaxWeightGrams() {
        return maxWeightGrams;
    }
    public int totalWeightGrams() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += items[i].getWeightGrams();
        }
        return sum;
    }

    public long totalValue() {
        long sum = 0;
        for (int i = 0; i < size; i++) {
            sum += items[i].getValue();
        }
        return sum;
    }
    public Item[] snapshot() {
        return Arrays.copyOf(items, size);
    }
    public Item[] findByName(String query) {
        if (query == null || query.isBlank()) {
            return new Item[0];
        }
        String q = query.strip().toLowerCase();
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (items[i].getName().toLowerCase().contains(q)) {
                count++;
            }
        }
        Item[] result = new Item[count];
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (items[i].getName().toLowerCase().contains(q)) {
                result[j++] = items[i];
            }
        }
        return result;
    }
    public boolean transferTo(int index, Inventory target) {
        if (target == null || target == this) {
            return false;
        }
        Item item = get(index);
        if (item == null) {
            return false;
        }
        if (!target.add(item)) {
            return false;
        }
        remove(index);
        return true;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < size;
    }
}