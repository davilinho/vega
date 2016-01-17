package com.vega.presentation.annotation;

import com.squareup.otto.Subscribe;
import com.vega.presentation.presenter.IPresenter;

import java.lang.reflect.Method;

/**
 * Component created on 25/11/2015.
 *
 * @author dmartin
 */
public class AnnotationChecker {

    public static void subscribeAnnotationChecker(Class clazz) {

        boolean subscriberMethodFound = false;

        for (Method method : clazz.getMethods()) {

            if (IPresenter.SUBSCRIBER_METHOD_NAME.equals(method.getName())) {

                subscriberMethodFound = true;

                if (!method.isAnnotationPresent(Subscribe.class)) {
                    throw new RuntimeException("The presenter called: " + clazz.getName()
                            + " must implement 'subscriberPresenterResponse'");
                }

                break;

            }

        }

        if (!subscriberMethodFound) {
            throw new RuntimeException("The 'subscriberPresenterResponse' method must have @Subscribe annotation.");
        }

    }

}
