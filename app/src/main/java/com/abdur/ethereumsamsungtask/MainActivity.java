package com.abdur.ethereumsamsungtask;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdur.ethereumsamsungtask.adapter.TransactionAdapter;
import com.abdur.ethereumsamsungtask.data.home.HomeViewModel;
import com.abdur.ethereumsamsungtask.data.home.ResultItem;
import com.abdur.ethereumsamsungtask.databinding.ActivityMainBinding;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HomeViewModel homeViewModel;
    private Context context;
    protected BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private String address = "0xddbd2b932c763ba5b1b7ae3b362eac3e8d40121a";

    String intentData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        getBalance(address);
        setListeners();
    }

    private void setListeners() {
        binding.clQRCode.setOnClickListener(v -> {
            binding.clQRCode.setVisibility(View.GONE);
            binding.clScanner.setVisibility(View.VISIBLE);
            if (cameraSource == null) initialiseDetectorsAndSources();
            else {
                try {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_GRANTED) {
                        if (cameraSource == null) {
                            return;
                        }
                        cameraSource.start(binding.surfaceView.getHolder());
                    } else {
                        mPermissionResult.launch(Manifest.permission.CAMERA);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        binding.btnCancelScanner.setOnClickListener(v -> {
            binding.clQRCode.setVisibility(View.VISIBLE);
            binding.clScanner.setVisibility(View.GONE);
            if (cameraSource != null) {
                cameraSource.stop();
            }
        });
        binding.submit.setOnClickListener(v -> {
            if (binding.etAddress.getText().toString().length() == 42 && binding.etAddress.getText().toString().startsWith("0x")) {
                address = binding.etAddress.getText().toString().trim();
                getBalance(address);
            } else
                Toast.makeText(context, "Please enter a valid address", Toast.LENGTH_SHORT).show();
        });
        binding.rbgAction.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == binding.rbBalance.getId()) {
                binding.clTransaction.setVisibility(View.GONE);
                getBalance(address);
            } else {
                binding.clTransaction.setVisibility(View.VISIBLE);
                getTransaction(address);
            }
        });

        binding.btnClose.setOnClickListener(v -> {
            binding.clTransaction.setVisibility(View.GONE);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getBalance(String mAddress) {
        binding.progressCircular.setVisibility(View.VISIBLE);
        homeViewModel.initViewModel(mAddress, "ACTION_BALANCE");
        homeViewModel.getBalanceLiveData().observe(this, apiResponse -> {
            binding.progressCircular.setVisibility(View.GONE);
            if (apiResponse != null) {
                if (!apiResponse.getError()) {
                    Timber.d("APIResponse :%s", apiResponse);
                    binding.tvBalance.setText(apiResponse.getResult());
                } else {

                    Toast.makeText(context, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "No Data found!!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getTransaction(String mAddress) {
        binding.progressCircular.setVisibility(View.VISIBLE);
        homeViewModel.initViewModel(mAddress, "ACTION_TRANSACTION");
        homeViewModel.getTransactionLiveData().observe(this, apiResponse -> {
            binding.progressCircular.setVisibility(View.GONE);
            if (apiResponse != null) {
                if (!apiResponse.getError()) {
                    Timber.d("APIResponse :%s", apiResponse);
                    List<ResultItem> resultItemList = apiResponse.getResult();
                    if (!resultItemList.isEmpty()) {
                        setRVAdapter(resultItemList);
                    } else {
                        Toast.makeText(context, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(context, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "No Data found!!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void setRVAdapter(List<ResultItem> resultItemList) {
        binding.rvTransaction.setHasFixedSize(true);
        binding.rvTransaction.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        TransactionAdapter transactionAdapter = new TransactionAdapter(resultItemList);
        binding.rvTransaction.setAdapter(transactionAdapter);

    }

    private void initialiseDetectorsAndSources() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        binding.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_GRANTED) {
                        if (cameraSource == null) {
                            return;
                        }
                        cameraSource.start(binding.surfaceView.getHolder());
                    } else {
                        mPermissionResult.launch(Manifest.permission.CAMERA);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                if (cameraSource != null) {
                    cameraSource.stop();
                }
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    binding.txtBarcodeValue.post(() -> {
                        try {
                            intentData = barcodes.valueAt(0).displayValue;
                            if (intentData.length() == 42 && intentData.startsWith("0x")) {
                                cameraSource.stop();
                                Timber.d("intentData: %s", intentData);
                                address = intentData.trim();
                                if (binding.rbBalance.isChecked()) {
                                    getBalance(address);
                                } else {
                                    getTransaction(address);
                                }
                            } else
                                Toast.makeText(context, "Please enter a valid address", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Timber.e(e.getCause());
                        }
                    });
                } else {
                    Timber.e("else called barcode size 0");
                }
            }
        });
    }

    private final ActivityResultLauncher<String> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        if (ActivityCompat.checkSelfPermission(context,
                                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            try {
                                cameraSource.start(binding.surfaceView.getHolder());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Timber.e("ScanBarCodeActivity -> %s",
                                "onActivityResult: PERMISSION DENIED");
                    }
                }
            });

    @Override
    protected void onDestroy() {
        if (cameraSource != null) {
            cameraSource.release();
        }
        super.onDestroy();
    }
}