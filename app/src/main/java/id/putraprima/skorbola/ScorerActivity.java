package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ScorerActivity extends AppCompatActivity {

    EditText nameScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);
        nameScore = findViewById(R.id.editText);
    }

    public void scoreResult(View view) {
        String name = nameScore.getText().toString();
        // Put the String to pass back into an Intent and close this activity]
        if (nameScore.equals("")) {
            Toast.makeText(this, "Harap Isi Nama", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra("nameScorer", name);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
