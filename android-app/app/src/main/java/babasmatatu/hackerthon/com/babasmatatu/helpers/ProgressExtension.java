package babasmatatu.hackerthon.com.babasmatatu.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import babasmatatu.hackerthon.com.babasmatatu.R;

import static babasmatatu.hackerthon.com.babasmatatu.helpers.StringExtension.isNullOrWhitespace;

/**
 * Created by munene on 4/8/2018.
 */
public class ProgressExtension {
    private static Dialog dialog;

    public static void setDialog(Activity activity, boolean show, String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View loadingView = inflater.inflate(R.layout.progress, null);

        if (!isNullOrWhitespace(text)){
            TextView messageTextView = (TextView)loadingView.findViewById(R.id.messageTextView);
            messageTextView.setText(text);
        }

        if (show){
            builder.setView(loadingView);
            dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        }
        else if(dialog != null) {
            dialog.dismiss();
        }
    }
}
