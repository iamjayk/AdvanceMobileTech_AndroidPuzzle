package com.ajay.build.blockpuzzle101;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    //9 pieces of the puzzle
    ImageView iw0, iw1, iw2, iw3, iw4, iw5, iw6, iw7, iw8 ;

    //this array contains 9 image IDs. It is fixed and it is the correct answer
    //dummy values (-1) will be replaced with the correct IDs in create()
    int correct_id_seq[] = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    //this array is the working array. Its element's values are similar to correct_id_seq[] except at diff locations
    int rand_id_seq[] = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    //array to keep 2 indexes of 2 elements in the array rand_id_seq from 2 clicks
    int two_indexes_to_swap_img[] = {-1, -1};

    int num_of_clicks = 0; //need to have 2 clicks to swap images

    ImageView two_imageView_to_swap_img[] = {null, null};

    TextView textView;

    Timer T;

    int time_to_solve_puzzle = -1;


    //make the array elements appear randomly
    public void rand_arr_elements(int[] arr) {
        Random random = new Random();
        int temp_index;
        int temp_obj;
        for (int i = 0; i < arr.length - 1; i++) {
            // a random number between i + 1 and arr.length - 1
            temp_index = i + 1 + random.nextInt(arr.length - 1 - i);
            // swap the element at i with the element at temp_index
            temp_obj = arr[i];
            arr[i] = arr[temp_index];
            arr[temp_index] = temp_obj;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set the values for the correct_id_seq array
        correct_id_seq[0] = R.drawable.bat1;
        correct_id_seq[1] = R.drawable.bat2;
        correct_id_seq[2] = R.drawable.bat3;
        correct_id_seq[3] = R.drawable.bat4;
        correct_id_seq[4] = R.drawable.bat5;
        correct_id_seq[5] = R.drawable.bat6;
        correct_id_seq[6] = R.drawable.bat7;
        correct_id_seq[7] = R.drawable.bat8;
        correct_id_seq[8] = R.drawable.bat9;

        //display image in 10s
        for (int i = 0; i < 9; i++) {
            iw[i].setClickable(false);
        }

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("Time: " + Long.toString(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                for (int i = 0; i < 9; i++) ;
            }

            display_puzzle_with_time_tick();
        }
    }.Start();




    }
}
