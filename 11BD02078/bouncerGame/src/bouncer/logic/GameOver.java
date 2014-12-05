package bouncer.logic;



import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameOver extends Activity {
	
	String result;
	
	private static String PREFS_NAME="bouncer_game";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
      
        Intent intent = getIntent();
        result=intent.getStringExtra("result");
       
        TextView et = (TextView) findViewById(R.id.points);
        et.setText(result);
      //  System.out.println("res " + result);        
     
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_over, menu);
        return true;
    }
   

    @TargetApi(9)
    
    public void backgroundApp(){
        
    	this.moveTaskToBack(true);
    	
    }
    
   
    
	 @SuppressLint("WorldReadableFiles")
	private String LoadPreferences(String key){
	   	   
	        String defaultString = "empty";  
	        String location ="";
	        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
	        location =  sharedPreferences.getString( key, defaultString );
	        System.out.println("loadRestore key = " + location);
	        	
	        return location;
	   
	       }
}
