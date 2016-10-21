package com.fourtyseven.notificationpoc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dell on 9/14/2016.
 */
public class NotificationId {
        private final static AtomicInteger c = new AtomicInteger(0);
        public static int getID() {
            Date now = new Date();
            int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
            return id;
            /*return c.incrementAndGet();*/
        }
    }
