package com.test.sporttracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.sporttracker.model.Exercise;

import io.realm.Realm;
import io.realm.RealmResults;

public class AnalysisDataActivity extends AppCompatActivity {

    Realm realm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analysis);
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        RealmResults<Exercise> results = realm.where(Exercise.class).findAll();


        LinearLayout table = (LinearLayout) findViewById(R.id.table);

        for (Exercise result : results) {

            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.item_table, null, false);

            TextView date = (TextView) linearLayout.findViewById(R.id.date);
            TextView exercise = (TextView) linearLayout.findViewById(R.id.exercise);
            TextView wegth = (TextView) linearLayout.findViewById(R.id.wegth);
            TextView count = (TextView) linearLayout.findViewById(R.id.count);
            TextView repeat = (TextView) linearLayout.findViewById(R.id.repeat);

            date.setText(result.getDate());
            exercise.setText(getName(result.getName()));
            wegth.setText(result.getWeight()+"");
            count.setText(""+result.getQuantity());
            repeat.setText(""+result.getRepetition());

            table.addView(linearLayout);
        }
    }

    private String getName(int position) {
        switch (position) {
            case 0:
                return "Протяжка рывковая";
            case 1:
                return "Рывок классический";
            case 2:
                return "Трастер";
            case 3:
                return "Тяга в наклоне";
            default:
                return "";
        }
    }
}
