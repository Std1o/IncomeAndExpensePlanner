package com.stdio.incomeandexpenseplanner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExpensesStatisticActivity extends AppCompatActivity {

    DBExpenses dbExpenses;
    public static SQLiteDatabase database;
    Spinner spCategories;
    String category;
    String dbCategory;
    String dbCost;
    int sum = 0;
    TextView tvSum;
    String currentMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_statistic);

        tvSum = findViewById(R.id.tvSumm);

        dbExpenses = new DBExpenses(this);
        database = dbExpenses.getWritableDatabase();

        currentMonth = new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());

        spCategories = findViewById(R.id.spinner_categories);
        spCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.categoriesForExpenses);
                category = choose[selectedItemPosition];
                tvSum.setText("Расходы в текущем месяце: " + getAndSumData());
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public int getAndSumData (){
        sum = 0;
        Cursor cursor = database.query(DBExpenses.TABLE_EXPENSES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int categoryIndex = cursor.getColumnIndex(DBExpenses.KEY_CATEGORY);
            int costIndex = cursor.getColumnIndex(DBExpenses.KEY_COST);
            int monthIndex = cursor.getColumnIndex(DBExpenses.KEY_MONTH);
            do {
                dbCategory = cursor.getString(categoryIndex);
                dbCost = cursor.getString(costIndex);
                if (category.equals(dbCategory) && currentMonth.equals(cursor.getString(monthIndex))) {
                    sum += Integer.valueOf(dbCost);
                }
            } while (cursor.moveToNext());
        } else
            cursor.close();
        return sum;
    }
}
