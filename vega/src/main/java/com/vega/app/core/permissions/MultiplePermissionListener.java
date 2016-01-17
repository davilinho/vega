package com.vega.app.core.permissions;

import android.Manifest;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.vega.app.core.activity.AbstractActivity;

import java.util.List;

/**
 * Component created on 21/12/2015.
 *
 * @author dmartin
 */
public class MultiplePermissionListener implements MultiplePermissionsListener {

    private final AbstractActivity activity;

    public MultiplePermissionListener(AbstractActivity activity) {
        this.activity = activity;
    }

    /**
     * Method called when all permissions has been completely checked
     *
     * @param report In detail report with all the permissions that has been denied and granted
     */
    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
            activity.showPermissionGranted(response.getPermissionName());
        }

        for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
            activity.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
        }
    }

    /**
     * Method called whenever Android asks the application to inform the user of the need for the
     * requested permissions. The request process won't continue until the token is properly used
     *
     * @param permissions The permissions that has been requested. Collections of values found in
     *                    {@link Manifest.permission}
     * @param token       Token used to continue or cancel the permission request process. The permission
     */
    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        activity.showPermissionRationale(token);
    }
}
