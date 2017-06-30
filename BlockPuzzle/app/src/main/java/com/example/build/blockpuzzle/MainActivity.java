package com.example.build.blockpuzzle;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.build.blockpuzzle.R;

import java.sql.Time;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //9 pieces of the puzzle
    ImageView[] iw = new ImageView[9];

    private final String IMAGE_VIEW_NAME_PREFIX = "iw";

    //this array contains 9 image IDs. It is fixed and it is the correct answer
    //dummy values (-1) will be replaced with the correct IDs in create()
    int correct_id_seq[] = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    //this array is the working array. Its element's values are similar to correct_id_seq[] except at diff locations
    int rand_id_seq[] = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    //array to keep 2 indexes of 2 elements in the array rand_id_seq from 2 clicks
    int two_indexes_to_swap_img[] = {-1, -1};

    int num_of_clicks = 0; //need to have 2 clicks to swap images

    ImageView two_imageView_to_swap_img[] = {null, null};


    static TextView textView;

    Timer T;

    static int time_to_solve_puzzle = -1;

    private MediaPlayer mp; // Play Music on Win





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

       // scoreView = (TextView) findViewById(R.id.textView2);
       // score = 0;
       // scoreView.setText("Score: " + String.valueOf(score));

       //get ImageViews
        for (int i = 0; i < 9; i++) {
            iw[i] = (ImageView) findViewById(this.getResources().getIdentifier(IMAGE_VIEW_NAME_PREFIX + Integer.toString(i), "id", this.getPackageName()));
        }
        //set the values for the correct_id_seq array
        for (int i = 0; i < 9; i++) {
            correct_id_seq[i] = this.getResources().getIdentifier("bat" + Integer.toString(i),"drawable", this.getPackageName());
        }

        //set the resource images of ImageViews
        for (int i = 0; i < 9; i++) {
            iw[i].setImageResource(correct_id_seq[i]);
        }

        //display image in 10s
        for (int i = 0; i < 9; i++) {
            iw[i].setClickable(false);
        }

        textView = (TextView) findViewById(R.id.textView);

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("Time: " + Long.toString(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                for (int i = 0; i < 9; i++) {
                    iw[i].setClickable(true);
                }
                display_puzzle_with_time_tick();
            }

        }.start();
    }

    public void display_puzzle_with_time_tick() {

        //for the array rand_id_seq, firstly, start with the correct sequence of ids
        rand_id_seq = Arrays.copyOf(correct_id_seq, correct_id_seq.length);

        //and then call the function rand_arr_elements to randomly swap elements
        rand_arr_elements(rand_id_seq);

        // based on the values of rand_id_seq, re-set the resource images of ImageViews
        for (int i = 0; i < 9; i++) {
            iw[i].setImageResource(rand_id_seq[i]);
        }

        //set time - ticking by seconds
        time_to_solve_puzzle = -1;

        T = new Timer();

        T.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              time_to_solve_puzzle++;
                                              textView.setText("TIME: " + time_to_solve_puzzle);
                                          }
                                      });
                                  }
                                },1000, 1000);
                            } // display_puzzle_with_time_tick

    public void on_click(View v) {
        ImageView view = (ImageView) v;
        Log.i("Click = ", view.toString());
        if (view == iw[0]) {
            two_indexes_to_swap_img[num_of_clicks] = 0;
            two_imageView_to_swap_img[num_of_clicks] = iw[0];
        } else if (view == iw[1]) {
            two_indexes_to_swap_img[num_of_clicks] = 1;
            two_imageView_to_swap_img[num_of_clicks] = iw[1];
        } else if (view == iw[2]) {
            two_indexes_to_swap_img[num_of_clicks] = 2;
            two_imageView_to_swap_img[num_of_clicks] = iw[2];
        } else if (view == iw[3]) {
            two_indexes_to_swap_img[num_of_clicks] = 3;
            two_imageView_to_swap_img[num_of_clicks] = iw[3];
        } else if (view == iw[4]) {
            two_indexes_to_swap_img[num_of_clicks] = 4;
            two_imageView_to_swap_img[num_of_clicks] = iw[4];
        } else if (view == iw[5]) {
            two_indexes_to_swap_img[num_of_clicks] = 5;
            two_imageView_to_swap_img[num_of_clicks] = iw[5];
        } else if (view == iw[6]) {
            two_indexes_to_swap_img[num_of_clicks] = 6;
            two_imageView_to_swap_img[num_of_clicks] = iw[6];
        } else if (view == iw[7]) {
            two_indexes_to_swap_img[num_of_clicks] = 7;
            two_imageView_to_swap_img[num_of_clicks] = iw[7];
        } else {
            two_indexes_to_swap_img[num_of_clicks] = 8;
            two_imageView_to_swap_img[num_of_clicks] = iw[8];
        }

        if (num_of_clicks ==1) {
            //2 clicks already - swap images now
            two_imageView_to_swap_img[0].setImageResource(rand_id_seq[two_indexes_to_swap_img[1]]);
            two_imageView_to_swap_img[1].setImageResource(rand_id_seq[two_indexes_to_swap_img[0]]);

            //update the rand_id_seq array
            int temp = rand_id_seq[two_indexes_to_swap_img[0]];
            rand_id_seq[two_indexes_to_swap_img[0]] = rand_id_seq[two_indexes_to_swap_img[1]];
            rand_id_seq[two_indexes_to_swap_img[1]] = temp;

            //check if the 2 array rand_id_seq and correct_id_seq are equal
            //if it is then the user wins
            if (Arrays.equals(correct_id_seq, rand_id_seq)) {

                //Toast Popup Message
                Toast.makeText(MainActivity.this,
                        "You won!", Toast.LENGTH_LONG).show();

                //Play Winning Music
                mp = MediaPlayer.create(getApplicationContext(), R.raw.queen_we_are_the_champions);
                mp.start();

                //update score

              //  scoreView.setText("Score: " + String.valueOf(time_to_solve_puzzle));

                T.cancel(); // Timer Stop


                // Save and Exit Button Declaration
                Button save_and_exit = (Button)findViewById(R.id.save_and_exit);
                save_and_exit.setOnClickListener(new Button.OnClickListener()
                {

                    //Goto New Activity on Click
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(MainActivity.this, Main4Activity.class);
                       startActivity(intent);

                    }
                });

                Button next = (Button)findViewById(R.id.next);
                next.setOnClickListener(new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        startActivity(intent);
                        mp.stop();
                    }
                });

                // Show Button When user wins
                next.setVisibility(view.VISIBLE);
               save_and_exit.setVisibility(View.VISIBLE);



            }
        }
        num_of_clicks++;

        if (num_of_clicks == 2)
            num_of_clicks = 0;
    }//end of onClick
}
