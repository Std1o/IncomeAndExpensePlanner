package com.stdio.incomeandexpenseplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerTouchListener.RecyclerTouchListenerHelper{

    private OnActivityTouchListener touchListener;
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private RecyclerTouchListener onTouchListener;
    public static ArrayList<DataModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    Toast.makeText(MainActivity.this, "change " + position, Toast.LENGTH_SHORT).show();
                }
            }
        }));
        setRecyclerViewAdapter();
        mRecyclerView.addOnItemTouchListener(onTouchListener);
    }

    private void getData() {
        for (int i =0; i < 20; i++) {
            list.add(new DataModel("name " + i, "category " + i, i, "date " + i, "cost " + i, "month " + i));
        }
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
