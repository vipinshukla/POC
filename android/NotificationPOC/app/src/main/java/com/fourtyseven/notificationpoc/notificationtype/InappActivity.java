package com.fourtyseven.notificationpoc.notificationtype;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fourtyseven.notificationpoc.R;


/**
 * Created by dell on 9/23/2016.
 */
public class InappActivity extends Activity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  ActionBar actionBar = getActionBar();
        actionBar.hide();*/
        setContentView(R.layout.inapp_notification_layout);
        button = (Button) findViewById(R.id.show_notification_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
