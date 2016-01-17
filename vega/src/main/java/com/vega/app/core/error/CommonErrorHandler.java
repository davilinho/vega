package com.vega.app.core.error;

import android.content.Context;
import android.widget.Toast;

/**
 * Component created on 29/05/2015.
 *
 * @author dmartin
 */
public class CommonErrorHandler {

    private Context context;

    public CommonErrorHandler(Context context) {
        this.context = context;
    }

    public void showCommonErrors() {
        Toast.makeText(context, "Generic error!!!", Toast.LENGTH_SHORT).show();
    }

}
