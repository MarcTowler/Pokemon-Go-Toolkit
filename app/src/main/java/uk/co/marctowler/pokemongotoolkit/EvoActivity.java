package uk.co.marctowler.pokemongotoolkit;

import android.support.v4.view.PointerIconCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PointerIconCompat;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EvoActivity extends AppCompatActivity implements OnItemSelectedListener {
    Button button_calculate;
    Button button_reset;
    Button button_add;
    int candiesPerEvolution;
    EditText howManyCandies;
    EditText howManyPokemon;
    int pokeCount;
    int candyCount;
    int numOfCandies;
    int numOfEvolutions;
    int numOfPokemon;
    Spinner spinner;
    String pokemon;
    TextView output;
    ArrayList<String> pokeEntryList = new ArrayList<>();
    ArrayList<Integer> totalEntryList = new ArrayList<>();
    ArrayList<Integer> candyEntryList = new ArrayList<>();

    class C01801 implements OnClickListener {
        C01801() {
        }

        public void onClick(View view2) {
            EvoActivity.this.howManyPokemon.setText(BuildConfig.FLAVOR);
            EvoActivity.this.howManyCandies.setText(BuildConfig.FLAVOR);
        }
    }

    class C01812 implements DialogInterface.OnClickListener {
        C01812() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evolution_calc);
        this.spinner = (Spinner) findViewById(R.id.pokemonList);
        this.pokemon = spinner.toString();
        this.howManyPokemon = (EditText) findViewById(R.id.mon_num);
        this.howManyCandies = (EditText) findViewById(R.id.can_num);
        this.button_calculate = (Button) findViewById(R.id.btn_calc);
        this.button_reset = (Button) findViewById(R.id.btn_reset);
        this.button_reset.setOnClickListener(new C01801());
        //this.spinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.evo_pokemoncandies, 17367049));
        this.spinner.setOnItemSelectedListener(this);
        this.output = (TextView) findViewById(R.id.output);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.button_add = (Button) findViewById(R.id.btn_add);

        button_add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                pokemon = spinner.getSelectedItem().toString();

                if(!(howManyPokemon.getText().toString().equals("") || howManyCandies.getText().toString().equals(""))) {
                    pokeCount = Integer.parseInt(howManyPokemon.getText().toString());
                    candyCount = Integer.parseInt(howManyCandies.getText().toString());
                    addPokemon(pokemon, pokeCount, candyCount);
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int value = this.spinner.getSelectedItemPosition();

        if(value > 0) {
            Toast.makeText(this, "You Selected " + ((TextView) view).getText(), 0).show();
        }

        if (value > 0 && value <= 3) {
            this.candiesPerEvolution = 12;
        } else if ((value >= 4 && value <= 6) || (value >= 10 && value <= 21)) {
            this.candiesPerEvolution = 25;
        } else if ((value >= 7 && value <= 9) || (value >= 22 && value <= 56)) {
            this.candiesPerEvolution = 50;
        } else if (value >= 57 && value <= 69) {
            this.candiesPerEvolution = 100;
        } else if (value == 70) {
            this.candiesPerEvolution = 400;
        }
    }

    private int transferPokemon() {
        int transferPokemonForMoreCandy = (this.candiesPerEvolution * 60) - this.numOfCandies;
        if (transferPokemonForMoreCandy < 0) {
            return 0;
        }
        return transferPokemonForMoreCandy;
    }

    private int evolveMorePokemon(int evolvePokemonNow) {
        int evolveMorePokemon = 60 - evolvePokemonNow;
        if (evolveMorePokemon < 0) {
            return 0;
        }
        return evolveMorePokemon;
    }

    private int evolvePokemonNow() {
        int evolvePokemonNow = this.numOfCandies / this.candiesPerEvolution;
        if (evolvePokemonNow > this.numOfPokemon) {
            evolvePokemonNow = this.numOfPokemon;
        }
        if (evolvePokemonNow < 0) {
            return 0;
        }
        return evolvePokemonNow;
    }

    private int gainXP(int evolvePokemonNow) {
        return evolvePokemonNow * 500;
    }

    private int gainXP_LuckyEgg(int evolvePokemonNow) {
        return evolvePokemonNow * PointerIconCompat.TYPE_DEFAULT;
    }

    private int candiesLeftOver(int evolvePokemonNow) {
        int candiesLeftOver = this.numOfCandies - (this.candiesPerEvolution * evolvePokemonNow);
        if (candiesLeftOver < 0) {
            return 0;
        }
        return candiesLeftOver;
    }

    private int pokemonLeftOver(int evolvePokemonNow) {
        int pokemonLeftOver = this.numOfPokemon - evolvePokemonNow;
        if (pokemonLeftOver < 0) {
            return 0;
        }
        return pokemonLeftOver;
    }

    private float howManyMinutes(int evolvePokemonNow) {
        if (evolvePokemonNow != 0) {
            return (((float) evolvePokemonNow) * 30.0f) / 60.0f;
        }
        return 0.0f;
    }

    private int candiesRequiredForCurrentPokemon(Integer numOfPokemon, Integer candiesPerEvolution) {
        return numOfPokemon.intValue() * candiesPerEvolution.intValue();
    }

    private void addPokemon(String pokemon, Integer totalCount, Integer candy) {
        pokeEntryList.add(pokemon);
        totalEntryList.add(totalCount);
        candyEntryList.add(candy);

        int arraySize = pokeEntryList.size();
        for(int i = 0; i < arraySize; i++) {
            if(i == 0) {
                output.setText(pokeEntryList.get(i) + "\t\t" + totalEntryList.get(i) + "\t\t" + candyEntryList.get(i) + "\n");
            } else {
                output.append(pokeEntryList.get(i) + "\t\t" + totalEntryList.get(i) + "\t\t" + candyEntryList.get(i) + "\n");
            }
        }
    }

    private String calculate() {
        try {
            this.numOfPokemon = Integer.parseInt(this.howManyPokemon.getText().toString());
            this.numOfCandies = Integer.parseInt(this.howManyCandies.getText().toString());
            int pokemonCount = evolvePokemonNow();
            return displayResults(transferPokemon(), evolveMorePokemon(pokemonCount), pokemonCount, gainXP(pokemonCount), gainXP_LuckyEgg(pokemonCount), candiesLeftOver(pokemonCount), pokemonLeftOver(pokemonCount), howManyMinutes(pokemonCount), candiesRequiredForCurrentPokemon(Integer.valueOf(this.numOfPokemon), Integer.valueOf(pokemonCount)));
        } catch (NumberFormatException e) {
            this.numOfPokemon = 0;
            this.numOfCandies = 0;
            return "Missing data";
        }
    }

    public String displayResults(int transferPokemon, int evolveMorePokemon, int evolvePokemonNow, int gainXP, int gainXP_LuckyEgg, int candiesLeftOver, int pokemonLeftOver, float howManyMinutes, int candiesRequiredForCurrentPokemon) {
        StringBuilder displayResults = new StringBuilder();
        displayResults.append("Lucky Egg Recommendation");
        displayResults.append("\n");
        if (evolvePokemonNow >= 60) {
            displayResults.append("Do it!");
            displayResults.append("\n\n");
        } else {
            displayResults.append("Do not use any Lucky Eggs until you can evolve at least ");
            displayResults.append(evolveMorePokemon);
            displayResults.append(" more Pokemon");
            displayResults.append("\n\n");
            displayResults.append("This will require an additional ");
            displayResults.append(transferPokemon);
            displayResults.append(" candies");
            displayResults.append("\n\n");
        }
        displayResults.append("Right now, you will be able to evolve ");
        displayResults.append(evolvePokemonNow);
        displayResults.append(" Pokemon");
        displayResults.append("\n\n");
        displayResults.append("If you evolve your Pokemon now, you will gain ");
        displayResults.append(gainXP_LuckyEgg);
        displayResults.append(" XP (with Lucky Egg) ");
        displayResults.append(" and ");
        displayResults.append(gainXP);
        displayResults.append(" XP (without Lucky Egg) ");
        displayResults.append("\n\n");
        displayResults.append("On average, it will take ");
        displayResults.append(howManyMinutes);
        displayResults.append(" minute(s) to evolve your Pokemon");
        displayResults.append("\n\n");
        displayResults.append("You will have ");
        displayResults.append(pokemonLeftOver);
        displayResults.append(" Pokemon and ");
        displayResults.append(candiesLeftOver);
        displayResults.append(" candies left over");
        return displayResults.toString();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void showAlert(View view) {
        Builder myAlert = new Builder(this);
        myAlert.setMessage(calculate()).setPositiveButton((CharSequence) "Okay, thanks!", new C01812()).create();
        myAlert.show();
    }
}