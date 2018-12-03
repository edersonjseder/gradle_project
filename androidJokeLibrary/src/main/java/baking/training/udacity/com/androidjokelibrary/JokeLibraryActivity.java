package baking.training.udacity.com.androidjokelibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeLibraryActivity extends AppCompatActivity {

    public static final String JOKE_CASE = "jokeList";

    private TextView tvJokeLabel;
    private String jokeToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_library);

        Intent intentFromDoInBackground = getIntent();

        tvJokeLabel = findViewById(R.id.text_view_joke_label);

        if (intentFromDoInBackground != null) {
            if (intentFromDoInBackground.hasExtra(JOKE_CASE)) {

                jokeToShow = intentFromDoInBackground.getStringExtra(JOKE_CASE);

                tvJokeLabel.setText(jokeToShow);

            } else {

                tvJokeLabel.setText("No connection!");

            }

        }

    }

}
