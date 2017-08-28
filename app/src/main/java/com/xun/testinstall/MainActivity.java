package com.xun.testinstall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xun.testinstall.service.AppInstallService;

public class MainActivity extends AppCompatActivity {
    private Button downloadAppBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadAppBtn = (Button) findViewById(R.id.download_app);
        downloadAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AppInstallService.class);
                startService(intent);
            }
        });
    }

}
