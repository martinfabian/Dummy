package my.fabian.progresstest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarTestActivity extends Activity 
{
	class MyAsyncTask extends AsyncTask<Void, Integer, Void>
	{
		static final int NUMSLEEPS = 7;
		StringBuilder builder = new StringBuilder();
		
		ProgressBar the_bar = null;
		TextView the_view = null;
		
		MyAsyncTask(ProgressBar progressbar, TextView textview)
		{
			the_bar = progressbar;
			the_view = textview;
		}
		
		@Override
		protected void onPreExecute()
		{
			setProgressBarIndeterminateVisibility(true);
			
			the_bar.setIndeterminate(true);
			the_bar.setVisibility(ProgressBar.VISIBLE);
		}
		
		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				for(int i = 0; i < NUMSLEEPS; i++)
				{
					publishProgress(i);
					Thread.sleep(1000);
				}
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
				return null;
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... i)
		{
			builder.append("Progress " + (i[0]+1) + " of " + NUMSLEEPS + "\n");
			the_view.setText(builder.toString());
		}
		
		@Override
		protected void onPostExecute(Void fck)
		{
			the_bar.setVisibility(ProgressBar.GONE);
			setProgressBarIndeterminateVisibility(false);
		}
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
        setContentView(R.layout.main);
        
        final TextView textview = (TextView)findViewById(R.id.textview1);
        
        final ProgressBar progressbar = (ProgressBar) findViewById(R.id.progressBar1);
        progressbar.setVisibility(ProgressBar.GONE);
		setProgressBarIndeterminateVisibility(false);
		
        final Button exebutton = (Button)findViewById(R.id.button1);
        exebutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new MyAsyncTask(progressbar, textview).execute();
			}
		});
    }
}