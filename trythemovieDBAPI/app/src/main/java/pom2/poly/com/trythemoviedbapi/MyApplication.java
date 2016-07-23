package pom2.poly.com.trythemoviedbapi;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;

/**
 * Created by User on 20/1/2016.
 */
public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

// Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

// Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(getApplicationContext())
        );

// Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        Fresco.initialize(this);


    }
}
