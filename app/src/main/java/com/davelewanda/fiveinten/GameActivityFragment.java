package com.davelewanda.fiveinten;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class GameActivityFragment extends Fragment {

    final static int GAME_TIME = 10; //10 second game time

    TextView categoryTextView;
    TextView timerTextView;
    Button startStopButton;
    CheckBox firstCheckBox;
    CheckBox secondCheckBox;
    CheckBox thirdCheckBox;
    CheckBox fourthCheckBox;
    CheckBox fifthCheckBox;
    boolean countingDown;
    private CountDownTimer gameTimer;

    public GameActivityFragment() {
        countingDown = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View gameView = inflater.inflate(R.layout.fragment_game, container, false);
        categoryTextView = (TextView) gameView.findViewById(R.id.categoryTextView);
        timerTextView = (TextView) gameView.findViewById(R.id.timerTextView);
        startStopButton = (Button) gameView.findViewById(R.id.startStopButton);
        firstCheckBox = (CheckBox) gameView.findViewById(R.id.firstCheckBox);
        secondCheckBox = (CheckBox) gameView.findViewById(R.id.secondCheckBox);
        thirdCheckBox = (CheckBox) gameView.findViewById(R.id.thirdCheckBox);
        fourthCheckBox = (CheckBox) gameView.findViewById(R.id.fourthCheckBox);
        fifthCheckBox = (CheckBox) gameView.findViewById(R.id.fifthCheckBox);

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countingDown) {
                    GameActivityFragment.this.resetGame();
                } else {
                    startStopButton.setText(R.string.stop);
                    gameTimer = new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            int seconds = (int) (millisUntilFinished / 1000);
                            timerTextView.setText(seconds + " seconds");
                        }

                        @Override
                        public void onFinish() {
                            timerTextView.setText(R.string.zero_seconds);
                            AlertDialog dialog = new AlertDialog.Builder(getActivity()).
                                    setMessage(R.string.timesUp)
                                    .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            resetGame();
                                        }
                                    }).create();
                            dialog.show();
                        }
                    };
                    gameTimer.start();
                }
                countingDown = !countingDown; //toggle state
            }
        });

        firstCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondCheckBox.setEnabled(firstCheckBox.isChecked());
                if (!firstCheckBox.isChecked()) {
                    secondCheckBox.setChecked(false);
                }
            }
        });

        secondCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thirdCheckBox.setEnabled(secondCheckBox.isChecked());
                if (!secondCheckBox.isChecked()) {
                    thirdCheckBox.setChecked(false);
                }
            }
        });

        thirdCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fourthCheckBox.setEnabled(thirdCheckBox.isChecked());
                if (!thirdCheckBox.isChecked()) {
                    fourthCheckBox.setChecked(false);
                }
            }
        });

        fourthCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fifthCheckBox.setEnabled(fourthCheckBox.isChecked());
                if (!fourthCheckBox.isChecked()) {
                    fifthCheckBox.setChecked(false);
                }
            }
        });

        fifthCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstCheckBox.isChecked() && secondCheckBox.isChecked()
                        && thirdCheckBox.isChecked() && fourthCheckBox.isChecked()
                        && fifthCheckBox.isChecked()) {
                    gameTimer.cancel();
                    //TODO: Add score to running totals
                    AlertDialog dialog = new AlertDialog.Builder(getActivity()).
                            setMessage(R.string.winner)
                            .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    resetGame();
                                }
                            }).create();
                    dialog.show();
                }
            }
        });


        return gameView;
    }

    private void resetGame() {
        timerTextView.setText(R.string.clock_not_playing);
        startStopButton.setText(R.string.start);
        firstCheckBox.setChecked(false);
        secondCheckBox.setChecked(false);
        secondCheckBox.setEnabled(false);
        thirdCheckBox.setChecked(false);
        thirdCheckBox.setEnabled(false);
        fourthCheckBox.setChecked(false);
        fourthCheckBox.setEnabled(false);
        fifthCheckBox.setChecked(false);
        fifthCheckBox.setEnabled(false);
    }
}
