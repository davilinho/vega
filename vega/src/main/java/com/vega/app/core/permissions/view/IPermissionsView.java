package com.vega.app.core.permissions.view;

import com.karumi.dexter.PermissionToken;

/**
 * Component created on 21/12/2015.
 *
 * @author dmartin
 */
public interface IPermissionsView {

    void showPermissionGranted(String permission);

    void showPermissionDenied(String permission, boolean isPermanentlyDenied);

    void showPermissionRationale(final PermissionToken token);
}
