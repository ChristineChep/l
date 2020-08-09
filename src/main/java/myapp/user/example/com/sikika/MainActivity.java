package myapp.user.example.com.sikika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CardView c1;
    private CardView c2;
    private CardView c3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView c1 = findViewById(R.id.card1);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(getActivity(), rights.class);
                startActivity(new Intent(MainActivity.this,Audio.class));
            }
        });

        CardView c2 = findViewById(R.id.card2);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(getActivity(), rights.class);
                startActivity(new Intent(MainActivity.this,Camera.class));
            }
        });

        CardView c3 = findViewById(R.id.card3);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(getActivity(), rights.class);
                startActivity(new Intent(MainActivity.this,Document.class));
            }
        });
    }
}
