package com.example.build.blockpuzzle;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {

    // DB Score Save
    private Button save_btn;
    private EditText usr_edit_text;
    private TextView time_text_view;
    DBHelper mydb;
    String username;

    class ButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String usrStr = usr_edit_text.getText().toString();
            Log.i("Click = ", v.toString());

            // Check that user typed a username
            if(usrStr.length()==0){
                Toast.makeText(getApplicationContext(),
                        "Please enter a username", Toast.LENGTH_SHORT).show();
                usr_edit_text.requestFocus();
                return;
            }
            //check if there is an existing user or not
            Cursor cursor = mydb.getPlayer(usrStr);
            if (cursor != null) {
                if (cursor.moveToFirst()) {//there is a record

                    username = cursor.getString(cursor.getColumnIndex(DBHelper.USERNAME_COL));
                    String old_score_str = cursor.getString(cursor.getColumnIndex(DBHelper.SCORE_COL));

                    Toast.makeText(getApplicationContext(),
                            "Welcome back " + usrStr + ". Your previous score is "
                                    + old_score_str, Toast.LENGTH_SHORT).show();
                } else {//there is no record
                    Toast.makeText(getApplicationContext(),
                            usrStr + " added", Toast.LENGTH_SHORT).show();
                    mydb.insertPlayer(usrStr);
                    username = usrStr;
                }
                cursor.close();
                //update score
                mydb.updatePlayer(username, MainActivity.time_to_solve_puzzle);
                Toast.makeText(getApplicationContext(),
                        "Your score " + String.valueOf(MainActivity.time_to_solve_puzzle)
                                + " is saved." , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Main4Activity.this, Main3Activity.class);
                startActivity(intent);

            }
        }//onClick()
    }//end of ButtonHandler


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        //create DBHelper object
        mydb = new DBHelper(this);
        //create a handler object
        ButtonHandler buttonHandler = new ButtonHandler();
        //get the button and attach the handler to it
        save_btn = (Button)findViewById(R.id.save_btn);
        save_btn.setOnClickListener(buttonHandler);
        time_text_view = (TextView)findViewById(R.id.time_text_view);
        time_text_view.setText("Time: " + String.valueOf(MainActivity.time_to_solve_puzzle));
        usr_edit_text = (EditText)findViewById(R.id.usr_edit_text);
        usr_edit_text.requestFocus();

    }//end of onCreate
}//end of SecondAvtivity

