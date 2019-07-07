package io.github.bibekshakya35.simplenoteapp.util;

import android.app.Activity;
import android.content.Intent;


import io.github.bibekshakya35.simplenoteapp.AppConstants;
import io.github.bibekshakya35.simplenoteapp.model.Note;
import io.github.bibekshakya35.simplenoteapp.ui.activity.AddNoteActivity;
import io.github.bibekshakya35.simplenoteapp.ui.activity.PwdActivity;

public class NavigatorUtils implements AppConstants {


    public static void redirectToPwdScreen(Activity activity,
                                           Note note) {
        Intent intent = new Intent(activity, PwdActivity.class);
        intent.putExtra(INTENT_TASK, note);
        activity.startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
    }


    public static void redirectToEditTaskScreen(Activity activity,
                                                Note note) {
        Intent intent = new Intent(activity, AddNoteActivity.class);
        intent.putExtra(INTENT_TASK, note);
        activity.startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
    }

    public static void redirectToViewNoteScreen(Activity activity,
                                                Note note) {
        Intent intent = new Intent(activity, AddNoteActivity.class);
        intent.putExtra(INTENT_TASK, note);
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        activity.startActivity(intent);
        activity.finish();
    }
}
