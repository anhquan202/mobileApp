package huce.nhom15.mobileapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import huce.nhom15.mobileapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout cl = findViewById(R.id.main);
        TextView tv = cl.findViewById(R.id.tvHeader);
        tv.setText(getString(R.string.Cartgories));
        View arrowleft = findViewById(R.id.arrowLeft);
        arrowleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}