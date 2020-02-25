package com.stdio.incomeandexpenseplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerTouchListener.RecyclerTouchListenerHelper{

    private OnActivityTouchListener touchListener;
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private RecyclerTouchListener onTouchListener;
    public static ArrayList<DataModel> list = new ArrayList<>();
    DBExpenses dbExpenses;
    public static SQLiteDatabase database;
    public static String taskName;
    public static String taskCategory;
    public static int taskID;
    public static String taskCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbExpenses = new DBExpenses(this);
        database = dbExpenses.getWritableDatabase();

        getData();
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        onTouchListener = new RecyclerTouchListener(this, mRecyclerView);
        onTouchListener
                .setClickable((new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                        //Toast.makeText(MainActivity.this, "Button in row " + (position + 1) + " clicked!", Toast.LENGTH_SHORT).show();
                    }
                })).setLongClickable(true, (new RecyclerTouchListener.OnRowLongClickListener() {
            public void onRowLongClicked(int position) {
                Toast.makeText(MainActivity.this, "Row " + (position + 1) + " long clicked!", Toast.LENGTH_SHORT).show();
            }
        })).setSwipeOptionViews(R.id.add, R.id.edit, R.id.change).setSwipeable(R.id.rowFG, R.id.rowBG, (new RecyclerTouchListener.OnSwipeOptionsClickListener() {
            public void onSwipeOptionClicked(int viewID, int position) {
                if (viewID == R.id.add) {
                    Toast.makeText(MainActivity.this, "add " + position, Toast.LENGTH_SHORT).show();
                } else if (viewID == R.id.edit) {
                    Toast.makeText(MainActivity.this, "edit " + position, Toast.LENGTH_SHORT).show();
                }
                else if (viewID == R.id.change) {
                    deleteItem(MainActivity.this, position);
                }
            }
        }));
        setRecyclerViewAdapter();
        mRecyclerView.addOnItemTouchListener(onTouchListener);
    }

    private void getData() {
        list = new ArrayList();

        Cursor cursor = database.query(DBExpenses.TABLE_EXPENSES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(DBExpenses.KEY_NAME);
            int categoryIndex = cursor.getColumnIndex(DBExpenses.KEY_CATEGORY);
            int idIndex = cursor.getColumnIndex(DBExpenses.KEY_ID);
            int dateIndex = cursor.getColumnIndex(DBExpenses.KEY_DATE);
            int costIndex = cursor.getColumnIndex(DBExpenses.KEY_COST);
            do {
                taskName = cursor.getString(nameIndex);
                taskCategory = cursor.getString(categoryIndex);
                taskID = cursor.getInt(idIndex);
                taskCost = cursor.getString(costIndex);
                System.out.println(cursor.getString(dateIndex));
                System.out.println(cursor.getString(cursor.getColumnIndex(DBExpenses.KEY_MONTH)));
                list.add(new DataModel(taskName, taskCategory, taskID, cursor.getString(dateIndex), taskCost, ""));
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }
    }

    public void deleteItem(Context context, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

//        // set title
//        alertDialogBuilder.setTitle("Delete item");

        // set dialog message
        alertDialogBuilder
                .setMessage("Вы действительно хотите удалить?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        list.remove(position);
                        mAdapter.removeItem(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        alertDialogBuilder.setCancelable(true);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void onClick (View view) {
        startActivity(new Intent(this, AddExpensesActivity.class));
    }

    public void IntentToStatistic(View view) {
        startActivity(new Intent(this, ExpensesStatisticActivity.class));
    }

    @Override
    public void setOnActivityTouchListener(OnActivityTouchListener listener) {
        this.touchListener = listener;
    }

    private void setRecyclerViewAdapter() {
        mAdapter = new MainAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (touchListener != null) {
            touchListener.getTouchCoordinates(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
