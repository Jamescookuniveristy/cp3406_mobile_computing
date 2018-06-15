package com.example.kongwenyao.weathertoday.settings_activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.kongwenyao.weathertoday.R;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * NumberPickerDialog.class contains override of onCreateDialog() that create custom NumberPicker
 * widget within an AlertDialog. SetNumberPickerTextColor() sets text color of numbers within NumberPicker
 * widget.
 *
 * Created by kongwenyao on 12/9/17.
 */

public class NumberPickerDialog extends DialogFragment {

    private NumberPicker.OnValueChangeListener valueChangeListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Create number picker widget
        final NumberPicker numberPicker = new NumberPicker(getActivity());
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);   //Turn off keyboard
        numberPicker.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorBackground2));
        setNumberPickerTextColor(numberPicker, ContextCompat.getColor(getContext(), R.color.colorBlack));   //Set text color of number picker values

        //Create alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialog);  //Dialog with custom style
        builder.setTitle("Choose Value ");
        builder.setMessage("Choose an interval: ");

        //Positive button of alert dialog
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(numberPicker, numberPicker.getValue(), numberPicker.getValue());
            }
        });

        //Negative button of alert dialog
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        builder.setView(numberPicker);  //Alert Dialog set number picker widget
        return builder.create();
    }

    //Setter for valueChangeListener
    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }


    /**
     *  Set text color of EditText widget within a NumberPicker widget.
     *
     *  @param numberPicker NumberPicker widget
     *  @param color Hex color code
     */
    public static void setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();

        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                }
                catch(NoSuchFieldException | IllegalArgumentException | IllegalAccessException e){
                    Log.w("setNumberPickerTextColor", e);
                }
            }
        }
    }
}
