package com.vega.app.core.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.vega.R;
import com.vega.app.core.Navigator;
import com.vega.app.core.activity.di.ActivityViewInjector;
import com.vega.app.core.annotation.AnnotationChecker;
import com.vega.app.core.application.VegaApplication;
import com.vega.app.core.error.CommonErrorHandler;
import com.vega.app.core.permissions.MultiplePermissionListener;
import com.vega.app.core.permissions.SinglePermissionListener;
import com.vega.app.core.permissions.view.IPermissionsView;
import com.vega.app.geo.manager.GeoLocationManager;
import com.vega.presentation.presenter.IPresenter;
import com.vega.presentation.presenter.Presenter;
import com.vega.presentation.view.IBaseView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Activity base con la funcionalidad base de todas las demas Activities
 */
public abstract class AbstractActivity extends AppCompatActivity implements IBaseView, IPermissionsView {

    @Inject
    protected Navigator navigator;

    @Inject
    protected GeoLocationManager locationManager;

    @Nullable
    protected Toolbar toolbar;

    @Nullable
    @Bind(android.R.id.content)
    protected View rootView;

    @Presenter
    private List<IPresenter> presenterList;

    private MultiplePermissionsListener allPermissionListener;
    private MultiplePermissionsListener locationPermissionListener;

    private PermissionListener callPermissionListener;
    private PermissionListener cameraPermissionListener;
    private PermissionListener readSMSPermissionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDepencences();
        injectViewDependencies();
        injectComponentDependences();

        bindPresenter();

        attachToolbarTitle();
        attachView();

        createPermissionListeners();
        bindPendingRequestsPermission();

        manageOnCreateImplementation();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkHasPresenter()) {
            for (IPresenter presenter : presenterList) {
                presenter.onResume();
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (checkHasPresenter()) {
            for (IPresenter presenter : presenterList) {
                presenter.onPause();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (checkHasPresenter()) {
            for (IPresenter presenter : presenterList) {
                presenter.detachView();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        onSaveInstanceStateImplementation(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onRestoreInstanceStateImplementation(savedInstanceState);
    }

    @Override
    public void manageCommonErrors() {
        new CommonErrorHandler(getApplicationContext()).showCommonErrors();
    }

    protected abstract int getContentView();

    protected abstract void injectComponentDependences();

    protected abstract Toolbar getToolbarInstance();

    protected abstract String getToolbarTitle();

    protected abstract List<IPresenter> getPresenterList();

    protected abstract void onCreateImplementation() throws Exception;

    protected abstract void onSaveInstanceStateImplementation(Bundle outState);

    protected abstract void onRestoreInstanceStateImplementation(Bundle savedInstanceState);

    @Override
    public abstract void showPermissionGranted(String permission);

    @Override
    public abstract void showPermissionDenied(String permission, boolean isPermanentlyDenied);

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showPermissionRationale(final PermissionToken token) {
        new AlertDialog.Builder(this).setTitle(R.string.permission_rationale_title)
                .setMessage(R.string.permission_rationale_message)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.cancelPermissionRequest();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.continuePermissionRequest();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        token.cancelPermissionRequest();
                    }
                })
                .show();
    }

    protected void checkAllPermissions() {
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermissions(allPermissionListener,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_SMS);
    }

    protected void checkCallPermission() {
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermission(callPermissionListener, Manifest.permission.CALL_PHONE);
    }

    protected void checkCameraPermission() {
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermission(cameraPermissionListener, Manifest.permission.CAMERA);
    }

    protected void checkLocationPermission() {
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermissions(locationPermissionListener,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    protected void checkReadSMSPermission() {
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermission(readSMSPermissionListener, Manifest.permission.READ_SMS);
    }

    protected boolean isCallPermission(String permission) {
        return Manifest.permission.CALL_PHONE.equals(permission);
    }

    protected boolean isCameraPermission(String permission) {
        return Manifest.permission.CAMERA.equals(permission);
    }

    protected boolean isLocationPermission(String permission) {
        return Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)
                || Manifest.permission.ACCESS_COARSE_LOCATION.equals(permission);
    }

    protected boolean isReadSMSPermission(String permission) {
        return Manifest.permission.READ_SMS.equals(permission);
    }

    private void injectDepencences() {
        ((VegaApplication) getApplication()).getComponent().inject(this);
    }

    private void injectViewDependencies() {
        new ActivityViewInjector(this).inject(getContentView());
    }

    private void attachToolbarTitle() {

        toolbar = getToolbarInstance();
        if (toolbar != null) {
            toolbar.setTitle(getToolbarTitle());
        }
    }

    @SuppressWarnings("unchecked")
    private void attachView() {

        if (checkHasPresenter()) {
            for (IPresenter presenter : presenterList) {
                presenter.attachView(this);
            }
        }

    }

    private boolean checkHasPresenter() {
        return presenterList != null && !presenterList.isEmpty();
    }

    private void bindPresenter() {

        if (getPresenterList() != null) {
            AnnotationChecker.addPresentedAnnotated(this);
            presenterList = getPresenterList();
        }

    }

    private void manageOnCreateImplementation() {

        try {
            onCreateImplementation();
        } catch (Exception e) {
            //TODO - Controlar exception!!
        }

    }

    private void createPermissionListeners() {

        if (rootView != null) {

            PermissionListener feedbackViewPermissionListener = new SinglePermissionListener(this);
            MultiplePermissionsListener feedbackViewMultiplePermissionListener = new MultiplePermissionListener(this);

            allPermissionListener = new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                    SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with((ViewGroup) rootView,
                            R.string.all_permissions_denied_feedback)
                            .withOpenSettingsButton(R.string.permission_rationale_settings_button_text)
                            .build());

            callPermissionListener = new CompositePermissionListener(feedbackViewPermissionListener,
                    SnackbarOnDeniedPermissionListener.Builder.with((ViewGroup) rootView,
                            R.string.call_phone_permission_denied_feedback)
                            .withOpenSettingsButton(R.string.permission_rationale_settings_button_text)
                            .build());

            cameraPermissionListener = new CompositePermissionListener(feedbackViewPermissionListener,
                    SnackbarOnDeniedPermissionListener.Builder.with((ViewGroup) rootView,
                            R.string.camera_permission_denied_feedback)
                            .withOpenSettingsButton(R.string.permission_rationale_settings_button_text)
                            .build());

            locationPermissionListener = new CompositeMultiplePermissionsListener(
                    feedbackViewMultiplePermissionListener,
                    SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with((ViewGroup) rootView,
                            R.string.location_permission_denied_feedback)
                            .withOpenSettingsButton(R.string.permission_rationale_settings_button_text)
                            .build());

            readSMSPermissionListener = new CompositePermissionListener(feedbackViewPermissionListener,
                    SnackbarOnDeniedPermissionListener.Builder.with((ViewGroup) rootView,
                            R.string.read_sms_permission_denied_feedback)
                            .withOpenSettingsButton(R.string.permission_rationale_settings_button_text)
                            .build());

        }
    }

    private void bindPendingRequestsPermission() {
        if (allPermissionListener != null) {
            Dexter.continuePendingRequestsIfPossible(allPermissionListener);
        }
    }

}
