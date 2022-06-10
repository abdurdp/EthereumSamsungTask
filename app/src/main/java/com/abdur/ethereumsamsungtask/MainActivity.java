package com.abdur.ethereumsamsungtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.abdur.ethereumsamsungtask.data.home.HomeViewModel;
import com.abdur.ethereumsamsungtask.databinding.ActivityMainBinding;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HomeViewModel homeViewModel;
    private Context context;

    private String address ="0x18D0dBc5Bbba31782299ce4b83CFE4d6d150f13a";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.initViewModel(address);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.progressCircular.setVisibility(View.VISIBLE);
        homeViewModel.getBalanceLiveData().observe(this, apiResponse -> {
            binding.progressCircular.setVisibility(View.GONE);
            if(apiResponse!=null){
                if(!apiResponse.getError()){
                    Timber.d("APIResponse :%s",apiResponse);
                    binding.tvBalance.setText(apiResponse.getResult());
                }else{

                    Toast.makeText(context, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(context, "No Data found!!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}