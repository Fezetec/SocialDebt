package drunkendevs.socialdebt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public int totalDebt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        CalculateAndSetTotalDebt(totalDebt);
    }

    private void CalculateAndSetTotalDebt(int totalDebt) {
        TextView txtTotalDebt = findViewById(R.id.txtTotalDebt);
        txtTotalDebt.setText(totalDebt < 0 ? "-" + totalDebt + "T" : totalDebt + "T");
        txtTotalDebt.setTextColor(totalDebt < 0 ? getResources().getColor(R.color.totalDebtMinus) : getResources().getColor(R.color.totalDebtPlus));
    }

    public void btnPayOffDebt_Clicked(View v){
        // Go to view PayOffDebt, send in totalDebt
    }

    public void btnAddDebt_Clicked(View v){
        // Go to view AddDebt, send in totalDebt
    }
}
