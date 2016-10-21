package com.fourtyseven.notificationpoc.notificationpayload;

/**
 * Created by dell on 10/12/2016.
 */
public class Action {

        private String title;

        private String text;

        private String color;

        private String deeplink;

        public String getTitle ()
        {
            return title;
        }

        public void setTitle (String title)
        {
            this.title = title;
        }

        public String getText ()
        {
            return text;
        }

        public void setText (String text)
        {
            this.text = text;
        }

        public String getColor ()
        {
            return color;
        }

        public void setColor (String color)
        {
            this.color = color;
        }

        public String getDeeplink ()
        {
            return deeplink;
        }

        public void setDeeplink (String deeplink)
        {
            this.deeplink = deeplink;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [title = "+title+", text = "+text+", color = "+color+", deeplink = "+deeplink+"]";
        }
    }

