package com.example.firebaseStarterKit.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LayoutResId {

    int LAYOUT_NOT_DEFINED = -1;

    int layout() default LAYOUT_NOT_DEFINED;

}