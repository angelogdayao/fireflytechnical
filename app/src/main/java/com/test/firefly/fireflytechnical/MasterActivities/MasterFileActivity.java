package com.test.firefly.fireflytechnical.MasterActivities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.firefly.fireflytechnical.ItemMasterCursorAdapter;
import com.test.firefly.fireflytechnical.R;
import com.test.firefly.fireflytechnical.recyclerviewdata.ItemMasterContract;

public class MasterFileActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    /** Identifier for the item data loader */
    private static final int ITEM_LOADER = 1;

    /** Adapter for the ListView */
    ItemMasterCursorAdapter mCursorAdapter;

    private String itemTypeCode;
    private int itemTypeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterfile);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MasterFileActivity.this, MasterEditorActivity.class);
                intent.putExtra("itemcode", itemTypeCode);
                intent.putExtra("itemid", itemTypeId);
                startActivity(intent);
            }
        });

        if(!getIntent().getStringExtra("itemcode").isEmpty()){
            itemTypeCode = getIntent().getStringExtra("itemcode");
            itemTypeId = getIntent().getIntExtra("itemid", 0);
        }


        // Find the ListView which will be populated with the item data
        ListView itemListView = (ListView) findViewById(R.id.list);
        registerForContextMenu(itemListView);
        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        itemListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list items for each row of item data in the Cursor.
        // There is no item data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new ItemMasterCursorAdapter(this, null, itemTypeCode, itemTypeId);
        itemListView.setAdapter(mCursorAdapter);

        // Setup the items click listener
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               /* // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(MasterFileActivity.this, EditorActivity.class);

                // Form the content URI that represents the specific item that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link ItemEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.android.shelf/shelf/2"
                // if the item with ID 2 was clicked on.
                Uri currentItemUri = ContentUris.withAppendedId(ItemMasterContract.ItemEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentItemUri);

                // Launch the {@link EditorActivity} to display the data for the current item.
                startActivity(intent);*/
            }
        });

        itemListView.setItemsCanFocus(true);
        // Kick off the loader
        getLoaderManager().initLoader(ITEM_LOADER, null, this);
    }

    /** Helper method to delete all items in the database. */
    private void deleteAllItems() {
        int rowsDeleted = getContentResolver().delete(ItemMasterContract.ItemEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from item database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu shelf to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option

            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllItems();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                ItemMasterContract.ItemEntry._ID,
                ItemMasterContract.ItemEntry.COLUMN_ITEM_TYPE_CODE,
                ItemMasterContract.ItemEntry.COLUMN_ITEM_MASTER_CODE,
                ItemMasterContract.ItemEntry.COLUMN_ITEM_MASTER_DESCRIPTION};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                ItemMasterContract.ItemEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link ItemCursorAdapter} with this new cursor containing updated item data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.list) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                // edit stuff here
                return true;
            case R.id.delete:
                // remove stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
