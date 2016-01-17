package com.vega.app.core.permissions;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.vega.app.core.activity.AbstractActivity;

/**
 * Component created on 21/12/2015.
 *
 * @author dmartin
 */
public class SinglePermissionListener implements PermissionListener {

    private final AbstractActivity activity;

    public SinglePermissionListener(AbstractActivity activity) {
        this.activity = activity;
    }
    /**
     * Method called whenever a requested permission has been granted
     *
     * @param response A response object that contains the permission that has been requested and
     *                 any additional flags relevant to this response
     */
    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        activity.showPermissionGranted(response.getPermissionName());
    }

    /**
     * Method called whenever a requested permission has been denied
     *
     * @param response A response object that contains the permission that has been requested and
     *                 any additional flags relevant to this response
     */
    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {
        activity.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
    }

    /**
     * Method called whenever Android asks the application to inform the user of the need for the
     * requested permission. The request process won't continue until the token is properly used
     *
     * @param permission The permission that has been requested
     * @param token      Token used to continue or cancel the permission request process. The permission
     */
    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
        activity.showPermissionRationale(token);
    }
}
