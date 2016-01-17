package com.vega.app.core.annotation;

import android.app.Activity;

import com.vega.presentation.presenter.Presenter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Component created on 25/11/2015.
 *
 * @author dmartin
 */
public class AnnotationChecker {

    public static void addPresentedAnnotated(Activity activity) {

        for (Field field : activity.getClass().getDeclaredFields()) {

            if (field.isAnnotationPresent(Presenter.class)) {

                if (!Modifier.isPublic(field.getModifiers())) {

                    throw new RuntimeException("Presenter must be accessible for this class." +
                            "Change visibility to public");

                } else {

                    try {

                        field.get(activity);

                    } catch (IllegalAccessException e) {

                        throw new RuntimeException("The presenter " + field.getName() + " can not be access");

                    }

                }

            }

        }

    }

}
