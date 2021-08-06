package com.example.korresheader;

import android.content.Context;

import com.example.korresheader.util.SQLiteHandler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SQLiteHandlerModule {

    /**
     * Hilt provider that build instances
     * @param context
     * @return
     */
    @Provides
    @Contact
    @Singleton
    SQLiteHandler contactSQLiteHandler(@ApplicationContext Context context) {
        return new SQLiteHandler(context);
    }

    /**
     * Qualifier for injections
     */
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Contact {

    }

}
