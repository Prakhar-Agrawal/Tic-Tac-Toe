package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons=new Button[3][3];

    private boolean player1turn=true;

    private int roundcount;

    private int player1points;
    private int player2points;

    private TextView textviewplayer1;
    private TextView textviewplayer2;
    private TextView textviewplayerturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textviewplayer1=findViewById(R.id.text_view_p1);
        textviewplayer2=findViewById(R.id.text_view_p2);
        textviewplayerturn=findViewById(R.id.text_view_turn);

       /* Timer t= new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                resetboard();
            }
        } ;
        t.schedule(task,100);
        */

         for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String buttonid = "Button"+ i + j;
                Log.i("Button id info", buttonid);
                int resid = getResources().getIdentifier(buttonid,"id",getPackageName());
                buttons[i][j] = findViewById(resid);
                buttons[i][j].setOnClickListener(this);
            }
        }

       Button buttonreset =findViewById(R.id.button_reset);
        buttonreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")){
            return;
        }

        if (player1turn){

            ((Button) v).setText("X");
        }else{

            ((Button) v).setText("O");
        }

        roundcount++;

        if(checkforwin()){
            if(player1turn) {
                player1wins();
            }else{
                player2wins();
            }
        }
        else if(roundcount==9){
            draw();
        }
        else{
            //Switch turns of players

            player1turn=!player1turn;

            turn(player1turn);
        }
    }

    private boolean checkforwin(){
        String field[][]=new String[3][3];

        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j]=buttons[i][j].getText().toString();
            }
        }

        for(int i=0;i<3;i++){
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")){
                return true;
            }
        }

        for(int i=0;i<3;i++){
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")){
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")){
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")){
            return true;
        }
        return false;
    }

    private void player1wins(){
        player1points++;
        Toast.makeText(this,"Player 1 Wins",Toast.LENGTH_SHORT).show();
        updatepointstext();
        resetboard();

    }
    private void player2wins(){
        player2points++;
        Toast.makeText(this,"Player 2 Wins",Toast.LENGTH_LONG).show();
        updatepointstext();
        resetboard();
        player1turn=!player1turn;
        turn(player1turn);
    }
    private void draw(){
        Toast.makeText(this,"NONE Wins",Toast.LENGTH_LONG).show();
        resetboard();
    }

    @SuppressLint("SetTextI18n")
    private void updatepointstext(){
        String temp1= String.valueOf(player1points);
        String temp2= String.valueOf(player2points);

        textviewplayer1.setText("Player 1(X): " + temp1);
        textviewplayer2.setText("Player 2(O): " + temp2);
    }

    private void resetboard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        roundcount=0;
        player1turn=true;
        turn(player1turn);
    }

    private void turn(boolean chance){
        chance=player1turn;
        if(chance){
            textviewplayerturn.setText("Player 1 Turn");
        }else{
            textviewplayerturn.setText("Player 2 Turn");
        }
    }

    private void resetGame(){
        player1points=0;
        player2points=0;
        updatepointstext();
        resetboard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundcount",roundcount);
        outState.putInt("player1points",player1points);
        outState.putInt("player2points",player2points);
        outState.putBoolean("player1turn",player1turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundcount=savedInstanceState.getInt("roundcount");
        player1points=savedInstanceState.getInt("player1points");
        player2points=savedInstanceState.getInt("player2points");
        player1turn=savedInstanceState.getBoolean("player1turn");

    }

}
