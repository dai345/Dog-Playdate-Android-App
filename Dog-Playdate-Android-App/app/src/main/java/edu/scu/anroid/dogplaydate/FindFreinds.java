package edu.scu.anroid.dogplaydate;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class FindFreinds extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_freinds);

        final EditText sbar = (EditText)findViewById(R.id.friendsSearchBar);
        Button search = (Button)findViewById(R.id.search);
        Button Add = (Button)findViewById(R.id.add);
        final TextView ResultText = (TextView)findViewById(R.id.resultTextView);
        final FrameLayout ResultFrame = (FrameLayout)findViewById(R.id.resultFrameLayout);

        ResultFrame.setVisibility(View.GONE);

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final String username = sbar.getText().toString();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                query.whereEqualTo("username", username);
                query.findInBackground(new FindCallback<ParseObject>() {

                    @Override
                    public void done(List<ParseObject> objectList, ParseException e) {
                        try {
                            ParseObject userObject = objectList.get(0);
                            ResultText.setText(userObject.getString("username"));
                            ResultFrame.setVisibility(View.VISIBLE);

                            Toast.makeText(getApplicationContext(), "Friends Found",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Username Not Found",
                                    Toast.LENGTH_LONG).show();
                        }
                    }


                });

            }
        });

        Add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String Friends = sbar.getText().toString();
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {
                    {
                        currentUser.add("friend", Friends);
                        currentUser.saveInBackground();

                        Toast.makeText(getApplicationContext(), "Friends Has Been Added",
                                Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_freinds, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
