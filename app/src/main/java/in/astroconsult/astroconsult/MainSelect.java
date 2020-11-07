package in.astroconsult.astroconsult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainSelect extends AppCompatActivity {

    Button client,astrologer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        getSupportActionBar().hide();

        client = findViewById(R.id.client_btn);
        astrologer = findViewById(R.id.lawyer_btn);

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainSelect.this,SignUp.class);
                startActivity(intent);
            }
        });
        astrologer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainSelect.this,SignUp.class);
                startActivity(intent);
            }
        });
    }
}
