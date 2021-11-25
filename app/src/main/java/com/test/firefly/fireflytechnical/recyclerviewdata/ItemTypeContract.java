package com.test.firefly.fireflytechnical.recyclerviewdata;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ItemTypeContract {

    private ItemTypeContract() {}

    public static final String CONTENT_AUTHORITY = "com.test.firefly.fireflytechnical";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ITEMS = "fireflytechnical";

    public static final class ItemEntry implements BaseColumns {

        /** The content URI to access the item data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);

        /** The MIME type of the {@link #CONTENT_URI} for a list of items. */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        /** The MIME type of the {@link #CONTENT_URI} for a single item. */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        /** Name of database table for items */
        public final static String TABLE_NAME = "itemtype";

        /**
         * Unique ID number for the item (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;


        /**
         * Code of the item.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ITEM_TYPE_CODE ="code";



        /**
         * Description of the item.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ITEM_TYPE_DESCRIPTION ="description";



    }
}
