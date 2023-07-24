package com.example.quiz_assignment;//package com.example.quiz_assignment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

public class Helper {
    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    // the method is used to set the language at runtime
    public static Context setLocale(Context context, String language) {
        persist(context, language);

        // Use the default language if the provided language is empty or not found
        if (language.isEmpty() || !isLanguageAvailable(language)) {
            language = Locale.getDefault().getLanguage();
        }

        // updating the language for devices above android nougat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }
        // for devices having lower version of android os
        return updateResourcesLegacy(context, language);
    }

    // Helper method to check if the provided language is available in the app's resources
    private static boolean isLanguageAvailable(String language) {
        Resources resources = Resources.getSystem();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(language);
        configuration.setLocale(locale);
        try {
            // Check if the locale is available in the app's resources
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            return true;
        } catch (Exception e) {
            // Locale not found, return false
            return false;
        }
    }


    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

    // the method is used update the language of application by creating
    // object of inbuilt Locale class and passing language argument to it
    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }

    // Helper method to get the saved language from SharedPreferences
    public static String getLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, ""); // Default to empty string (English)
    }
}
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.os.Build;
//import android.preference.PreferenceManager;
//
//import java.util.Locale;
//
//public class LocaleHelper {
//    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";
//
//    // the method is used to set the language at runtime
//    public static Context setLocale(Context context, String language) {
//        persist(context, language);
//
//        // updating the language for devices above android nougat
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return updateResources(context, language);
//        }
//        // for devices having lower version of android os
//        return updateResourcesLegacy(context, language);
//    }
//
//    private static void persist(Context context, String language) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(SELECTED_LANGUAGE, language);
//        editor.apply();
//    }
//
//    // the method is used update the language of application by creating
//    // object of inbuilt Locale class and passing language argument to it
//    @TargetApi(Build.VERSION_CODES.N)
//    private static Context updateResources(Context context, String language) {
//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//
//        Configuration configuration = context.getResources().getConfiguration();
//        configuration.setLocale(locale);
//        configuration.setLayoutDirection(locale);
//
//        return context.createConfigurationContext(configuration);
//    }
//
//
//    @SuppressWarnings("deprecation")
//    private static Context updateResourcesLegacy(Context context, String language) {
//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//
//        Resources resources = context.getResources();
//
//        Configuration configuration = resources.getConfiguration();
//        configuration.locale = locale;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            configuration.setLayoutDirection(locale);
//        }
//
//        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//
//        return context;
//    }
//}

