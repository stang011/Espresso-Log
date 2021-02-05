package net.androidbootcamp.jayschimmoller.espressolog;

import java.util.ArrayList;

public class FileManager {
    private ArrayList<Recipe> recipes;

    public FileManager() {
        recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe r) {
        recipes.add(r);
    }
    public void removeRecipe(int index) {
        recipes.remove(index);
    }

    public Recipe getReceipe(int index) {
        return recipes.get(index);
    }
}
