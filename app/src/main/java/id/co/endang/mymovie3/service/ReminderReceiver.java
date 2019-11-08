package id.co.endang.mymovie3.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import id.co.endang.mymovie3.MainActivity;
import id.co.endang.mymovie3.R;
import id.co.endang.mymovie3.model.Movie;
import id.co.endang.mymovie3.model.MovieResponse;
import id.co.endang.mymovie3.rest.RESTMovie;
import id.co.endang.mymovie3.rest.RESTMovieCallback;

public class ReminderReceiver extends BroadcastReceiver implements RESTMovieCallback {

    public static final String DAILY_REMINDER = "DailyReminder";
    public static final String RELEASE_REMINDER = "ReleaseReminder";
    private static final String EXTRA_MESSAGE = "extra_message";
    private static final String EXTRA_TYPE = "extra_type";
    private static final CharSequence CHANNEL_NAME = "movie_channel";
    private static final int NOTIFICATION_REQUEST_CODE = 200;
    private final int ID_DAILY_REMINDER = 100;
    private final int ID_RELEASE_REMINDER = 101;
    private final String CHANNEL_ID = "channel_01";
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        mContext = context;
        String title = type.equalsIgnoreCase(DAILY_REMINDER) ? DAILY_REMINDER : RELEASE_REMINDER;
        int notifId = type.equalsIgnoreCase(DAILY_REMINDER) ? ID_DAILY_REMINDER : ID_RELEASE_REMINDER;

        showToast(context, title, message);
        if (type.equalsIgnoreCase(DAILY_REMINDER))
            showDailyReminderNotification(context, title, message, notifId);
        else
            getReleaseMoviesToday();
    }

    private void showToast(Context context, String title, String message) {
        Toast.makeText(context, title + " : " + message, Toast.LENGTH_SHORT).show();
    }

    public void setDailyReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, context.getResources().getString(R.string.app_name) + " " + context.getResources().getString(R.string.daily_remind_text));
        intent.putExtra(EXTRA_TYPE, DAILY_REMINDER);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, intent, 0);
        if (alarmManager != null)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, "Daily Reminder set up", Toast.LENGTH_SHORT).show();
    }

    public void setReleaseReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, "RELEASE REMINDER");
        intent.putExtra(EXTRA_TYPE, RELEASE_REMINDER);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_REMINDER, intent, 0);
        if (alarmManager != null)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, "Release Reminder set up", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        int requestCode = type.equalsIgnoreCase(DAILY_REMINDER) ? ID_DAILY_REMINDER : ID_RELEASE_REMINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null)
            alarmManager.cancel(pendingIntent);

        Toast.makeText(context, type.equals(DAILY_REMINDER) ? "Daily Reminder Canceled" : "Release Reminder Canceled", Toast.LENGTH_SHORT).show();
    }

    private void getReleaseMoviesToday() {
        Calendar calendar = Calendar.getInstance();
        new RESTMovie().searchMovies(RESTMovie.MOVIE_RELEASE, "", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()), this);
    }

    private void showDailyReminderNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Reminder channel";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }

        Notification notification = builder.build();
        if (notificationManager != null)
            notificationManager.notify(notifId, notification);
    }

    @Override
    public void onSuccess(MovieResponse response) {
        ArrayList<Movie> movies = response.getResults();
        if (movies.size() > 0) {
            showReleaseReminderNotification(movies);
        }

    }

    private void showReleaseReminderNotification(ArrayList<Movie> movies) {
        Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.cinema_clapboard);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (movies.size() <= 2) {
            showMaxTwoNotification(movies, largeIcon, alarmSound);
        } else {
            int idNotification = 1;
            PendingIntent pendingIntent = getLaunchIntent(idNotification);
            NotificationCompat.Builder builder;
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

            for (Movie movie : movies) {
                inboxStyle.addLine(movie.getTitle());
            }
            inboxStyle
                    .setBigContentTitle(movies.size() + " movies release today")
                    .setSummaryText("movies release reminder");

            builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                    .setContentTitle(movies.size() + " movies release today")
                    .setSmallIcon(R.drawable.ic_movie_)
                    .setLargeIcon(largeIcon)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setSound(alarmSound)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setAutoCancel(true);


            buildNotification(idNotification, builder);
        }
    }

    private void showMaxTwoNotification(ArrayList<Movie> movies, Bitmap largeIcon, Uri alarmSound) {
        int idNotification = 0;
        for (Movie movie : movies) {
            PendingIntent pendingIntent = getLaunchIntent(idNotification);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                    .setContentTitle(movie.getTitle())
                    .setContentText(movie.getTitle() + " has been release today!")
                    .setSmallIcon(R.drawable.ic_movie_)
                    .setLargeIcon(largeIcon)
                    .setContentIntent(pendingIntent)
                    .setSound(alarmSound)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setAutoCancel(true);

            buildNotification(idNotification, builder);
            idNotification++;
        }

    }

    private void buildNotification(int idNotification, NotificationCompat.Builder builder) {
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }

        Notification notification = builder.build();
        if (notificationManager != null)
            notificationManager.notify(idNotification, notification);
    }

    public PendingIntent getLaunchIntent(int notificationId) {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("notificationId", notificationId);
        return PendingIntent.getActivity(mContext, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @Override
    public void onFailed(String error) {

    }
}
