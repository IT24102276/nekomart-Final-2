package com.nekomart.model;

import java.util.LinkedList;
import java.util.List;
public class ToyLinkedList {
    private LinkedList<Toy> toys = new LinkedList<>();

    public void add(Toy toy) { toys.add(toy); }
    public void remove(int id) {
        toys.removeIf(t -> t.getId() == id);
    }
    public Toy get(int id) {
        for (Toy t : toys) if (t.getId() == id) return t;
        return null;
    }
    public List<Toy> getAll() { return toys; }
    public void update(Toy toy) {
        for (int i = 0; i < toys.size(); i++) {
            if (toys.get(i).getId() == toy.getId()) {
                toys.set(i, toy);
                break;
            }
        }
    }
    // Selection Sort by ageGroup
    public void sortByAgeGroup() {
        for (int i = 0; i < toys.size() - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < toys.size(); j++) {
                if (toys.get(j).getAgeGroup() < toys.get(minIdx).getAgeGroup()) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                Toy temp = toys.get(i);
                toys.set(i, toys.get(minIdx));
                toys.set(minIdx, temp);
            }
        }
    }
}
