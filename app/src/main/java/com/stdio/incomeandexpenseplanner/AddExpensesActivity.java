package com.stdio.incomeandexpenseplanner;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddExpensesActivity extends AppCompatActivity {

    String expensesName;
    String expensesCost;
    String expensesCategory;
    EditText etExpensesName;
    EditText etCost;
    Spinner spinner;
    private static boolean expensesIsAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        etExpensesName = findViewById(R.id.etExpensesName);
        etCost = findViewById(R.id.etCost);

        spinner = findViewById(R.id.spinner_categories);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.categoriesForExpenses);
                expensesCategory = choose[selectedItemPosition];
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onClick(View view) {
        expensesCost = etCost.getText().toString();
        expensesName = etExpensesName.getText().toString();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBExpenses.KEY_DATE, new SimpleDateFormat("dd").format(Calendar.getInstance().getTime()));
            contentValues.put(DBExpenses.KEY_NAME, expensesName);
            contentValues.put(DBExpenses.KEY_CATEGORY, expensesCategory);
            contentValues.put(DBExpenses.KEY_COST, expensesCost);
            contentValues.put(DBExpenses.KEY_MONTH, new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()));
            System.out.println(new SimpleDateFormat("dd").format(Calendar.getInstance().getTime()));
            System.out.println(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()));
            new MainActivity().getDatabase().insert(DBExpenses.TABLE_EXPENSES, null, contentValues);
            expensesIsAdded = true;
            finish();
    }

    public boolean getExpensesIsAdded() {
        return  expensesIsAdded;
    }

    public void setExpensesIsAdded(boolean b) {
        expensesIsAdded = b;
    }
}
