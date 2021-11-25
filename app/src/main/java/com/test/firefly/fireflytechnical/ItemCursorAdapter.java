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

public class ItemCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link ItemCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list items view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list items view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list items view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the item data (in the current row pointed to by cursor) to the given
     * list items layout. For example, the name for the current item can be set on the name TextView
     * in the list items layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list items layout
        TextView nameTextView =view.findViewById(R.id.name);
        TextView priceTextView =  view.findViewById(R.id.price);
        TextView quantityTextView =  view.findViewById(R.id.quantity);
        Button sellButton =  view.findViewById(R.id.sellButton);


        // Find the columns of item attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY);
        int idColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry._ID);

        // Log index values for debugging purposes
        Log.v("ItemCursorAdapter", "Name Index = " + nameColumnIndex +
                "\nPrice Index = " + priceColumnIndex + "\nQuantity Index = " + quantityColumnIndex);

        // Read the item attributes from the Cursor for the current item
        String itemName = cursor.getString(nameColumnIndex);
        final int itemPrice = cursor.getInt(priceColumnIndex);
        String itemPriceText = "$ " + itemPrice;
        final int itemQuantity = cursor.getInt(quantityColumnIndex);
        String itemQuantityText = itemQuantity + " left";
        final int itemId = cursor.getInt(idColumnIndex);

        // Update the TextViews with the attributes for the current item
        nameTextView.setText(itemName);
        priceTextView.setText(itemPriceText);
        quantityTextView.setText(itemQuantityText);

        // Add functionality to decrease quantity of clicked list item sell button in catalog view
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = itemQuantity;
                if (quantity > 0) {
                    -- quantity;
                    ContentValues values = new ContentValues();
                    values.put(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY, quantity);
                    String selection = ItemContract.ItemEntry._ID + "=?";
                    String[] selectionArgs = {String.valueOf(itemId)};
                    // Update and send to SQLite database
                    int sellOne = context.getContentResolver().update(
                            ItemContract.ItemEntry.CONTENT_URI, values, selection, selectionArgs);
                } else {
                    Toast.makeText(context, "Item sold out", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
