package com.test.firefly.fireflytechnical;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.test.firefly.fireflytechnical.recyclerviewdata.ItemMasterContract;


public class ItemMasterCursorAdapter extends CursorAdapter {

    private String itemTypeString;
    private int itemTypeId;
    public ItemMasterCursorAdapter(Context context, Cursor c, String itemType, int itemTypeIds) {
        super(context, c, 0 /* flags */);
        itemTypeString = itemType;
        itemTypeId = itemTypeIds;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list items view using the layout specified in list_item.xml
        int codeColumnIndex = cursor.getColumnIndex(ItemMasterContract.ItemEntry.COLUMN_ITEM_TYPE_CODE);

        if(cursor.getString(codeColumnIndex).equals(itemTypeString)){
            return LayoutInflater.from(context).inflate(R.layout.list_itemtype, parent, false);
        }else  return LayoutInflater.from(context).inflate(R.layout.list_empty, parent, false);

    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        int codeColumnIndex = cursor.getColumnIndex(ItemMasterContract.ItemEntry.COLUMN_ITEM_TYPE_CODE);

        if(cursor.getString(codeColumnIndex).equals(itemTypeString)){
            // Find individual views that we want to modify in the list items layout
            TextView codeTextView =view.findViewById(R.id.codeTV);
            TextView descTextView =  view.findViewById(R.id.descTV);

            // Find the columns of item attributes that we're interested in
            int masterCodeColumnIndex = cursor.getColumnIndex(ItemMasterContract.ItemEntry.COLUMN_ITEM_MASTER_CODE);
            int descColumnIndex = cursor.getColumnIndex(ItemMasterContract.ItemEntry.COLUMN_ITEM_MASTER_DESCRIPTION);

            // Read the item attributes from the Cursor for the current item
            String itemCode = cursor.getString(masterCodeColumnIndex);
            String itemdesc = cursor.getString(descColumnIndex);

            // Update the TextViews with the attributes for the current item
            codeTextView.setText(itemCode);
            descTextView.setText(itemdesc);
        }else{
            view.setVisibility(View.GONE);

        }



        // Log index values for debugging purposes
        /*Log.v("ItemCursorAdapter", "Name Index = " + nameColumnIndex +
                "\nPrice Index = " + priceColumnIndex + "\nQuantity Index = " + quantityColumnIndex);*/


    }

}