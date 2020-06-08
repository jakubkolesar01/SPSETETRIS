package sk.upjs.ics.android.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SetDiff extends AppCompatActivity {
    ImageView zaciatocnik,miernepokrocily,pokrocily;
    EditText name;
    Highscores highscores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Toast toastik = Toast.makeText(this,"ZADAJTE MENO",Toast.LENGTH_SHORT);
        final Intent start = new Intent(SetDiff.this, MainActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_diff);
        zaciatocnik=(ImageView) findViewById(R.id.zaciatocnik);
        miernepokrocily=(ImageView) findViewById(R.id.miernepokrocily);
        pokrocily=(ImageView) findViewById(R.id.pokrocily);
        name=(EditText) findViewById(R.id.name);
        highscores = Highscores.getInstance();
        zaciatocnik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().length()==0){
                    toastik.show();
                }
                else {
                    highscores.setCurrentName(name.getText().toString());
                    startActivity(start);
                }
            }
        });
        miernepokrocily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().length()==0){
                    toastik.show();
                }
                else {
                    highscores.setCurrentName(name.getText().toString());
                    startActivity(start);
                }
            }
        });
        pokrocily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().length()==0){
                    toastik.show();
                }
                else {
                    highscores.setCurrentName(name.getText().toString());
                    startActivity(start);
                }
            }
        });
    }

}
