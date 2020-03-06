package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MatchActivity extends AppCompatActivity {

    TextView homeName, awayName, awayScore, homeScore, nameHomeScore, nameAwayScore;
    ImageView homeLogo, awayLogo;
    Uri uri1, uri2;
    Bitmap bitmap1, bitmap2;
    String homeTeam, awayTeam, returnHome, returnAway, result, message, scorerName;
    int scoreHome = 0;
    int scoreAway = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        homeName = findViewById(R.id.txt_home);
        awayName = findViewById(R.id.txt_away);
        homeLogo = findViewById(R.id.home_logo);
        awayLogo = findViewById(R.id.away_logo);
        awayScore = findViewById(R.id.score_away);
        homeScore = findViewById(R.id.score_home);
        nameHomeScore = findViewById(R.id.homeGoal);
        nameAwayScore = findViewById(R.id.awayGoal);

        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        //3.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang ke ResultActivity, jika seri di kirim text "Draw"

        Bundle extras = getIntent().getExtras();
        homeTeam = extras.getString("inputHome");
        awayTeam = extras.getString("inputAway");

        if (extras != null) {
            uri1 = Uri.parse(extras.getString("logoHome"));
            uri2 = Uri.parse(extras.getString("logoAway"));
            bitmap1 = null;
            bitmap2 = null;

            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri1);
                bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri2);
            } catch (IOException e) {
                e.printStackTrace();
            }

            homeName.setText(homeTeam);
            awayName.setText(awayTeam);
            homeLogo.setImageBitmap(bitmap1);
            awayLogo.setImageBitmap(bitmap2);
        }
    }

    //2.Tombol add score menambahkan satu angka dari angka 0, setiap kali di tekan
    //3.Dari activity scorer akan mengirim kembali ke activity matchactivity otomatis nama pencetak gol dan skor bertambah +1
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                scoreHome = scoreHome + 1;
                homeScore.setText(String.valueOf(scoreHome));
                returnHome = data.getStringExtra("nameScorer");

                String namaHome = returnHome;
                String newNamaHome = nameHomeScore.getText().toString();
                nameHomeScore.setText(String.valueOf(newNamaHome+ "\n "+ namaHome));
            }
        }else{
            if (resultCode == RESULT_OK) {
                scoreAway = scoreAway + 1;
                awayScore.setText(String.valueOf(scoreAway));
                returnAway = data.getStringExtra("nameScorer");

                String namaAway = returnAway;
                String newNamaAway = nameAwayScore.getText().toString();
                nameAwayScore.setText(String.valueOf(newNamaAway+ "\n "+ namaAway));
            }
        }
    }

    //2.Tombol add score menambahkan memindah activity ke scorerActivity dimana pada scorer activity di isikan nama pencetak gol
    public void handleHomeScore(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, 1);
    }

    public void handleAwayScore(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, 2);
    }

    //4.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang beserta nama pencetak gol ke ResultActivity, jika seri di kirim text "Draw",
    public void handleSubmit(View view){
        if (scoreHome > scoreAway){
            result = String.valueOf(scoreHome) + " - " + String.valueOf(scoreAway);
            message = homeTeam + " adalah Pemenang";
            scorerName = nameHomeScore.getText().toString();
        } else if(scoreHome < scoreAway){
            result = String.valueOf(scoreHome) + " - " + String.valueOf(scoreAway);
            message = awayTeam + " adalah Pemenang";
            scorerName = nameAwayScore.getText().toString();
        } else {
            result = String.valueOf(scoreHome) + " - " + String.valueOf(scoreAway);
            message = "DRAW";
            scorerName = "";
        }

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("result", result);
        intent.putExtra("message", message);
        intent.putExtra("name", scorerName);

        startActivity(intent);
    }
}
