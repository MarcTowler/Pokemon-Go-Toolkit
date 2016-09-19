package uk.co.marctowler.pokemongotoolkit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.sql.Array;

public class EggActivity extends AppCompatActivity {

    Button twokm;
    Button fivekm;
    Button tenkm;
    Array input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.twokm = (Button) findViewById(R.id.btn_2km);

        twokm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get the 2km array and replace the textviews with values
            }
        });
    }

}
