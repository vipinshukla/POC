package com.fourtyseven.notificationpoc;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.fourtyseven.notificationpoc.notificationtype.AnimatedBannerNotification;
import com.fourtyseven.notificationpoc.notificationtype.BigImageNotification;
import com.fourtyseven.notificationpoc.notificationtype.BigImageNotificationWithbutton;
import com.fourtyseven.notificationpoc.notificationtype.CarouselNotification;
import com.fourtyseven.notificationpoc.notificationtype.InappNotification;
import com.fourtyseven.notificationpoc.notificationtype.StaticBannerNotification;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

   private Button big_img_with_button,animated_banner_button,carouse_noti_button,big_image_button,inapp_noti_button,static_banner_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initUi();
    }

    private void initUi() {

        big_image_button = (Button)findViewById(R.id.big_image_noti_button);
        big_image_button.setOnClickListener(this);
        animated_banner_button = (Button)findViewById(R.id.animated_banner_noti_button);
        animated_banner_button.setOnClickListener(this);
        carouse_noti_button = (Button)findViewById(R.id.carouse_noti_button);
        carouse_noti_button.setOnClickListener(this);
        big_img_with_button = (Button)findViewById(R.id.big_image_with_button);
        big_img_with_button.setOnClickListener(this);
        inapp_noti_button = (Button)findViewById(R.id.inapp_noti_button);
        inapp_noti_button.setOnClickListener(this);
        static_banner_button = (Button)findViewById(R.id.static_banner_noti_button);
        static_banner_button.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onClick(View v) {

        int viewID = v.getId();
        switch (viewID){
            case R.id.big_image_noti_button:
            {
                BigImageNotification bigImageNotification = new BigImageNotification("Banner","Message",NotificationId.getID());
                bigImageNotification.customProgressAnimationNotification(this);

                break;
            }
            case R.id.static_banner_noti_button:
            {
               /* StaticBannerNotification staticBannerNotification = new StaticBannerNotification("Banner","Message",NotificationId.getID());
                staticBannerNotification.customProgressAnimationNotification(this);*/
                break;
            }
            case R.id.inapp_noti_button:
            {
                InappNotification inappNotification = new InappNotification("Inapp","message");
                inappNotification.customProgressAnimationNotification(this,NotificationId.getID());
                break;
            }
            case R.id.big_image_with_button:{
                BigImageNotificationWithbutton bigImageNotificationWithbutton = new BigImageNotificationWithbutton("Banner","Message",NotificationId.getID());
                bigImageNotificationWithbutton.customProgressAnimationNotification(this);
                break;
            }
            case R.id.carouse_noti_button:
            {
                /*CarouselNotification carouselNotification = new CarouselNotification("Banner","Message");
                carouselNotification.customProgressAnimationNotification(this,NotificationId.getID(),true,0);*/
                break;
            }
            case R.id.animated_banner_noti_button:
            {
                /*AnimatedBannerNotification bannerNotification = new AnimatedBannerNotification("Banner","Message",NotificationId.getID());
                bannerNotification.customProgressAnimationNotification(this, bannerNotification);*/
                break;
            }
        }

    }
}
