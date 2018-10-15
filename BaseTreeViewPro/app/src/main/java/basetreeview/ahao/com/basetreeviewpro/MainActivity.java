package basetreeview.ahao.com.basetreeviewpro;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    Button singleLayoutButton;
    Button multiLayoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initEvent();
    }

    private void initEvent() {
        singleLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SingleLayoutTreeActivity.class));
            }
        });

        multiLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MultiLayoutTreeActivity.class));
            }
        });
    }

    private void initUI() {
        singleLayoutButton = findViewById(R.id.single);
        multiLayoutButton = findViewById(R.id.multi);
    }

}
