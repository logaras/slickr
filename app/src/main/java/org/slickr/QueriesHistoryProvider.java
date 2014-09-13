package org.slickr;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Used for storing typed-in queries.
 */
public class QueriesHistoryProvider extends SearchRecentSuggestionsProvider{
    public final static String AUTHORITY = QueriesHistoryProvider.class.getName();
    public final static int MODE = DATABASE_MODE_QUERIES;

    public QueriesHistoryProvider(){
        setupSuggestions(AUTHORITY,MODE);
    }
}
