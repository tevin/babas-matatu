package babasmatatu.hackerthon.com.babasmatatu.helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by munene on 4/7/2018.
 */
public class DialogExtension {
    public void generateGenericDialog(Context context, String title, String message){
        Dialog dialog = null;
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null){
                    dialog.dismiss();
                }
            }
        });
        dialog = alert.create();
        dialog.show();
        return;
    }
}

