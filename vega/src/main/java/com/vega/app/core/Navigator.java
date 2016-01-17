package com.vega.app.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.vega.R;
import com.vega.app.constants.IntentValuesConstants;
import com.vega.app.core.fragment.AbstractFragment;

import java.io.Serializable;

/**
 * Component created on 19/11/2015.
 *
 * @author dmartin
 */
public class Navigator<T extends Serializable> {

    private Context context;

    public Navigator(Context context) {
        this.context = context;
    }

    public void navigateToActivity(Class<?> activityClass, T data) {
        if (context != null) {
            navigate(activityClass, data);
        }
    }

    public void navigateToActivity(Class<?> activityClass) {
        if (context != null) {
            navigate(activityClass, null);
        }
    }

    public void navigateToHomeScreen() {
        if (context != null) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    public void navigateToFragment(AppCompatActivity activity, AbstractFragment fragment, boolean addToBackStack) {
        if (activity != null && fragment != null) {
            navigate(activity, fragment, addToBackStack);
        }
    }

    public void navigateToFragment(AppCompatActivity activity, AbstractFragment fragment, T data,
                                   boolean addToBackStack) {
        if (activity != null && fragment != null) {
            if (data != null) {
                prepareData(fragment, data);
            }
            navigate(activity, fragment, addToBackStack);
        }
    }

    private void navigate(Class<?> activityClass, T data) {
        final Intent intent = new Intent(context, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        putData(data, intent);
        context.startActivity(intent);
    }

    private void putData(T data, Intent intent) {
        if (data != null) {
            intent.putExtra(IntentValuesConstants.ACTIVITY_DATA, data);
        }
    }

    private void prepareData(AbstractFragment fragment, T data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentValuesConstants.FRAGMENT_DATA, data);
        fragment.setArguments(bundle);
    }

    private void navigate(FragmentActivity activity, AbstractFragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.content_frame, fragment);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

}
