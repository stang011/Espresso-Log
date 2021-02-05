package net.androidbootcamp.jayschimmoller.espressolog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Frag2 extends Fragment {

    private View view;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    public static final String FILE_NAME = "recipeFile";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag2_layout, container, false);
        recipes.clear();
        loadRecipes();

        List<HashMap<String, String>> aList = new ArrayList<>();

        for(int i = 0; i < recipes.size(); i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("listview_title", recipes.get(i).displayString());
            Log.d("myTag", recipes.get(i).displayString());
            aList.add(hm);
        }

        String[] from = {"listview_title"};
        int[] to = {R.id.custom};

        SimpleAdapter simpleAdapter = new SimpleAdapter(view.getContext(), aList, R.layout.item_list_custom, from, to);
        ListView androidListView = (ListView) view.findViewById(android.R.id.list);
        androidListView.setAdapter(simpleAdapter);

        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "Do you want to delete this recipe?";
                final int p = position;

                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Recipe")
                        .setMessage(message)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                recipes.remove(p);
                                updateFile();
                                reloadList();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Do nothing.
                            }
                        }).show();
            }
        });

        return view;
    }

    private void reloadList() {
        Frag2 fragment = (Frag2) getFragmentManager().findFragmentById(R.id.view_pager);
        getFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
    }

    private void loadRecipes() {
        try {
            Scanner scanner = new Scanner(getContext().getApplicationContext().openFileInput(FILE_NAME));
            String input;

            while(scanner.hasNextLine()) {
                input = scanner.nextLine();
                Recipe newRecipe = new Recipe(input);

                if(!recipes.contains(newRecipe)) {
                    recipes.add(newRecipe);
                    Log.d("myTag", "LOG " + newRecipe.toString());
                }
            }
            scanner.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFile() {
        FileOutputStream outputStream;
        String fileContents = "";
        int numberEles = recipes.size();

        for(int i = 0; i < numberEles; i++) {
            fileContents += recipes.get(i).toString() + "\n";
        }
        try {
            outputStream = getContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}