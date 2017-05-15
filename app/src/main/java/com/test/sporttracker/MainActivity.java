package com.test.sporttracker;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.test.sporttracker.model.Exercise;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.textDate)
    TextView textDate;

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.weight)
    TextInputEditText weight;
    @BindView(R.id.approachest)
    TextInputEditText quantity;
    @BindView(R.id.repetitions)
    TextInputEditText repetitions;

    @BindView(R.id.record)
    Button record;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Realm.init(this);

        realm = Realm.getDefaultInstance();
    }

    @OnClick({R.id.imageDate, R.id.textDate})
    public void onClickDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.show(getFragmentManager(), "date");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"."
                +((monthOfYear+1) < 10 ? "0"+(monthOfYear+1) : (monthOfYear+1))
                +"."+year;
        textDate.setText(date);

        clearFocus();
        clearData();
       /* Exercise exercise = findExercise(date);
        if (exercise != null) {
            setData(exercise);
        } else {
            clearData();
        }*/
    }

    private void setData(Exercise exercise) {
        spinner.setSelection(exercise.getName());
        weight.setText(""+exercise.getWeight());
        quantity.setText(""+exercise.getQuantity());
        repetitions.setText(""+exercise.getRepetition());
        record.setText("Перезаписать");
    }

    @OnClick(R.id.record)
    public void setRecord() {
        realm.beginTransaction();
        //Exercise findExercise = findExercise(textDate.getText().toString());
        Exercise exercise = realm.createObject(Exercise.class);
        exercise.setDate(textDate.getText().toString());
        exercise.setName(spinner.getSelectedItemPosition());
        exercise.setWeight(Double.parseDouble(weight.getText().toString()));
        exercise.setQuantity(Integer.parseInt(quantity.getText().toString()));
        exercise.setRepetition(Integer.parseInt(repetitions.getText().toString()));
        realm.commitTransaction();

        Toast.makeText(this, "Записано", Toast.LENGTH_SHORT).show();
        clearData();
        clearFocus();
    }

    private int findExercise() {
        RealmResults<Exercise> where = realm.where(Exercise.class).findAll();
        return where.size();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void clearData() {
        spinner.setSelection(0);
        weight.setText("");
        quantity.setText("");
        repetitions.setText("");
        record.setText("Записать");
    }

    private void clearFocus() {
        weight.clearFocus();
        quantity.clearFocus();
        repetitions.clearFocus();
    }
}

