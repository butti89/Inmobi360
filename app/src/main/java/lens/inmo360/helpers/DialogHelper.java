package lens.inmo360.helpers;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import lens.inmo360.R;

/**
 * Created by estebanbutti on 5/16/16.
 */
public class DialogHelper {
    public static void showNoConnectionDialog(Context ctx){
        new MaterialDialog.Builder(ctx)
                .title(R.string.no_connection_title)
                .content(R.string.no_connection_message)
                .positiveText(R.string.ok_dialog_button_text)
                .show();
    }

    public static void showInvalidCompanyIDDialog(Context ctx){
        new MaterialDialog.Builder(ctx)
                .title(R.string.invalid_company_title)
                .content(R.string.invalid_company_message)
                .positiveText(R.string.ok_dialog_button_text)
                .show();
    }
}
