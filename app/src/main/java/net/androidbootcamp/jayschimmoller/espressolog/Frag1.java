package net.androidbootcamp.jayschimmoller.espressolog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag1 extends Fragment {

    private View view, frag2View;

    TextView espressoInput = null;
    TextView outputInput = null;
    TextView ratio = null;
    TextView brewTime = null;
    TextView grindSetting = null;
    TextView runTime = null;
    TextView origin = null;
    TextView taste = null;

    DecimalFormat df = new DecimalFormat("##.0");

    ArrayList<Recipe> recipes = new ArrayList<>();
    public static final String FILE_NAME = "recipeFile";

    Recipe recipeToAdd = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag1_layout, container, false);

        loadRecipes();

        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(goodInput()) {
                    saveRecipe();
                    saveSaveFile();
                    Toast.makeText(getActivity(), "Espresso Recipe Saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "All fields must be full", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button clearButton = (Button) view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearFields();
                Toast.makeText(getActivity(),"Espresso Recipe Cleared", Toast.LENGTH_SHORT).show();
            }
        });

        espressoInput = (TextView) view.findViewById(R.id.espressoInput);
        outputInput = (TextView) view.findViewById(R.id.outputInput);
        ratio = (TextView) view.findViewById(R.id.outputInput2);
        brewTime = (TextView) view.findViewById(R.id.brewTimeInput);
        grindSetting = (TextView) view.findViewById(R.id.grinderInput);
        runTime = (TextView) view.findViewById(R.id.runTimeInput);
        origin = (TextView) view.findViewById(R.id.originInput);
        taste = (TextView) view.findViewById(R.id.tasteInput);

        outputInput.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateRatio();
                brewTime.requestFocus();
                brewTime.setCursorVisible(true);
            }
        });

        ratio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateOutput();
                brewTime.requestFocus();
                brewTime.setCursorVisible(true);
            }
        });

        return view;
    }

    private boolean goodInput() {
        if(!espressoInput.getText().toString().equals("") && !outputInput.getText().toString().equals("") &&
           !ratio.getText().toString().equals("") && !brewTime.getText().toString().equals("") && !grindSetting.getText().toString().equals("") &&
           !runTime.getText().toString().equals("") && !origin.getText().toString().equals("") && !taste.getText().toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private void calculateRatio() {
        String espressoInputString = espressoInput.getText().toString();
        String outputInputString = outputInput.getText().toString();
        if(espressoInputString != null && outputInputString != null && !espressoInputString.equals("") && !outputInputString.equals("")) {
            double espressoQuant = Double.parseDouble(espressoInputString);
            double outputQuant = Double.parseDouble(outputInputString);
            if(espressoQuant > 0) {
                double ratioQuant = outputQuant / espressoQuant;
                ratio.setText(df.format(ratioQuant) + "");
            }
        }
    }

    public void calculateOutput() {
        String espressoInputString = espressoInput.getText().toString();
        String ratioString = ratio.getText().toString();
        if(espressoInputString != null && ratioString != null && !espressoInputString.equals("") && !ratioString.equals("")) {
            double espressoQuant = Double.parseDouble(espressoInputString);
            double ratioQuant = Double.parseDouble(ratioString);
            double outputQuant = espressoQuant * ratioQuant;
            outputInput.setText(df.format(outputQuant) + "");
        }
    }

    // Load saved recipes so new recipes are appended to the file
    private void loadRecipes() {
        recipes.clear();
        try {
            Scanner scanner = new Scanner(getContext().getApplicationContext().openFileInput(FILE_NAME));
            String input;

            while(scanner.hasNextLine()) {
                input = scanner.nextLine();
                Recipe newRecipe = new Recipe(input);
                //Log.d("myTag", newRecipe.toString());
                recipes.add(newRecipe);
            }
            scanner.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Save a new recipe to the end of the list. Append to the current list of recipes
    private void saveRecipe() {
        recipeToAdd = new Recipe(Double.parseDouble(espressoInput.getText().toString()), Double.parseDouble(outputInput.getText().toString()),
                                 Double.parseDouble(ratio.getText().toString()), Integer.parseInt(brewTime.getText().toString()),
                                 Integer.parseInt(grindSetting.getText().toString()), Double.parseDouble(runTime.getText().toString()),
                                 origin.getText().toString(), taste.getText().toString());
    }

    // Save appended list of recipes to a file to be loaded from in the saved logs fragment
    private void saveSaveFile() {

        loadRecipes();
        recipes.add(recipeToAdd);


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

        clearFields();
        updateSavedLogList();
    }

    private void updateSavedLogList() {
        Frag2 fragment = (Frag2) getFragmentManager().findFragmentById(R.id.view_pager);
        getFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
    }

    private void clearFields() {
        espressoInput.setText("");
        espressoInput.clearFocus();
        outputInput.setText("");
        outputInput.clearFocus();
        ratio.setText("");
        ratio.clearFocus();
        brewTime.setText("");
        brewTime.clearFocus();
        grindSetting.setText("");
        grindSetting.clearFocus();
        runTime.setText("");
        runTime.clearFocus();
        origin.setText("");
        origin.clearFocus();
        taste.setText("");
        taste.clearFocus();
    }
}