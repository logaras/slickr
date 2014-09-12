package org.slickr;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by marlog on 9/12/14.
 */
public class QueriesHistoryProvider extends SearchRecentSuggestionsProvider{
    public final static String AUTHORITY = QueriesHistoryProvider.class.getName();
    public final static int MODE = DATABASE_MODE_QUERIES;

    public QueriesHistoryProvider(){
        setupSuggestions(AUTHORITY,MODE);
    }
}
