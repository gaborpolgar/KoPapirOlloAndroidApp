package com.gabor.kopapirollouj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView machinesChoiceImage;
    private ImageView yourChoiceImage;
    private TextView yourChoiceText;
    private TextView machinesChoiceText;
    private TextView eredmenyText;
    private Button koButton, papirButton, olloButton;
    private int maxGyozelem;
    private int gepNyert;
    private int veletlen;
    private boolean nyertel;
    private boolean dontetlen;
    private String jatekosValasztasa;
    private String gepValasztasa;
    private AlertDialog.Builder builderJatekVege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ujJatek();

            olloButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    yourChoiceImage.setImageResource(R.drawable.scissors);
                    jatekosValasztasa = "olló";
                    run();
                }
            });
            papirButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    yourChoiceImage.setImageResource(R.drawable.paper);
                    jatekosValasztasa = "papír";
                    run();
                }
            });
            koButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    yourChoiceImage.setImageResource(R.drawable.rock);
                    jatekosValasztasa = "kő";
                    run();
                }
            });

    }

    private void run(){
        Random random = new Random();
        veletlen = random.nextInt(3);
        switch (veletlen) {
            case 0:
                gepValasztasa = "kő";
                machinesChoiceImage.setImageResource(R.drawable.rock);
                break;
            case 1:
                gepValasztasa = "papír";
                machinesChoiceImage.setImageResource(R.drawable.paper);
                break;
            case 2:
                gepValasztasa = "olló";
                machinesChoiceImage.setImageResource(R.drawable.scissors);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + veletlen);
        }
        if (gepValasztasa.equals(jatekosValasztasa)) {
            dontetlen = true;
        } else if (gepValasztasa.equals("olló") && Objects.equals(jatekosValasztasa, "kő")) {
            nyertel = true;
        } else if (gepValasztasa.equals("olló") && Objects.equals(jatekosValasztasa, "papír")) {
            nyertel = false;
        } else if (gepValasztasa.equals("kő") && Objects.equals(jatekosValasztasa, "papír")) {
            nyertel = true;
        } else if (gepValasztasa.equals("kő") && Objects.equals(jatekosValasztasa, "olló")) {
            nyertel = false;
        } else if (gepValasztasa.equals("papír") && Objects.equals(jatekosValasztasa, "olló")) {
            nyertel = true;
        } else if (gepValasztasa.equals("papír") && Objects.equals(jatekosValasztasa, "kő")) {
            nyertel = false;
        }
        ;

        if (nyertel) {
            maxGyozelem++;
            Toast.makeText(MainActivity.this, "Ezt megnyerted. Játsz tovább a végső győzelemért.", Toast.LENGTH_SHORT).show();
        } else {
            gepNyert++;
            Toast.makeText(MainActivity.this, "Ezt elvesztetted, de ne csüggedj, játsz tovább a végső győzelemért.", Toast.LENGTH_SHORT).show();
        }
        ;
        eredmenyText.setText(String.format("Eredmény: Ember: %s Computer: %s", maxGyozelem, gepNyert));
        if (maxGyozelem == 3) {
            builderJatekVege.setTitle("Győzelem").create().show();
        } else if (gepNyert == 3) {
            builderJatekVege.setTitle("Vereség").create().show();
        }
    }

    private void ujJatek() {
        maxGyozelem = 0;
        eredmenyText.setText("Eredmény: Ember: 0 Computer: 0");
        veletlen = 0;
        gepNyert = 0;
    }

    public void init() {
        machinesChoiceImage = findViewById(R.id.machinesChoiceImage);
        yourChoiceImage = findViewById(R.id.yourChoiceImage);
        yourChoiceText = findViewById(R.id.yourChoiceText);
        machinesChoiceText = findViewById(R.id.machinesChoiceText);
        eredmenyText = findViewById(R.id.eredmenyText);
        koButton = findViewById(R.id.koButton);
        papirButton = findViewById(R.id.papirButton);
        olloButton = findViewById(R.id.olloButton);
        dontetlen = false;
        jatekosValasztasa = "";
        gepValasztasa = "";
        nyertel = false;
        builderJatekVege = new AlertDialog.Builder(MainActivity.this);
        builderJatekVege.setCancelable(false)
                .setTitle("Nyert /Vesztettél")
                .setMessage("Szeretne új játékot játszani?")
                .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ujJatek();
                    }
                })
                .create();

    }
}