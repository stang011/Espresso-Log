package net.androidbootcamp.jayschimmoller.espressolog;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3000);
                }
                catch (Exception ignored) {
                }
                finally {
                    Intent i = new Intent(SplashScreen.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
