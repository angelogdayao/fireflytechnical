package com.test.firefly.fireflytechnical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.test.firefly.fireflytechnical.recyclerviewdata.ItemContract;
import com.test.firefly.fireflytechnical.recyclerviewdata.ItemTypeContract;

public class ItemTypeCursorAdapter extends CursorAdapter {

    public ItemTypeCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list items view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_itemtype, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list items layout
        TextView codeTextView =view.findViewById(R.id.codeTV);
        TextView descTextView =  view.findViewById(R.id.descTV);



        // Find the columns of item attributes that we're interested in
        int codeColumnIndex = cursor.getColumnIndex(ItemTypeContract.ItemEntry.COLUMN_ITEM_TYPE_CODE);
        int descColumnIndex = cursor.getColumnIndex(ItemTypeContract.ItemEntry.COLUMN_ITEM_TYPE_DESCRIPTION);


        // Log index values for debugging purposes
        /*Log.v("ItemCursorAdapter", "Name Index = " + nameColumnIndex +
                "\nPrice Index = " + priceColumnIndex + "\nQuantity Index = " + quantityColumnIndex);*/

        // Read the item attributes from the Cursor for the current item
        String itemCode = cursor.getString(codeColumnIndex);
        String itemdesc = cursor.getString(descColumnIndex);

        // Update the TextViews with the attributes for the current item
        codeTextView.setText(itemCode);
        descTextView.setText(itemdesc);
    }

}
