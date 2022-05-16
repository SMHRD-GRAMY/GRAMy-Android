package com.example.gramy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gramy.Adapter.GomyshelfAdapter;
import com.example.gramy.Vo_Info.GomyshelfVO;
import com.example.gramy.Vo_Info.GoreportVO;

import java.util.ArrayList;

import GoMgSelf.CustomAdapter;
import GoMgSelf.Dictionary;

public class reportActivity extends AppCompatActivity {

    private ArrayList<Dictionary> mArrayList;
    private CustomAdapter mAdapter;
    private int count = -1;
    com.example.gramy.home.fragHomemain fragHomemain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_report);

        RecyclerView recyclerView =findViewById(R.id.report_recyclerView);

        LinearLayoutManager LayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(LayoutManager);
        GomyshelfAdapter adapter = new GomyshelfAdapter();

//        adapter.addItem(new GoreportVO("2선반","123","123"));

        recyclerView.setAdapter(adapter);


        Button buttonInsert = (Button) findViewById(R.id.btnreport);


        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;

                AlertDialog.Builder builder = new AlertDialog.Builder(reportActivity.this);
                View view = LayoutInflater.from(reportActivity.this).inflate(R.layout.shelf_editbox, null, false);
                builder.setView(view);

                final Button ButtonSubmitcancle = (Button) view.findViewById(R.id.shelf_button_dialog_submit_cancle);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.shelf_button_dialog_submit);
                final EditText editTextName = (EditText) view.findViewById(R.id.shelf_edittext_dialog_name);


                ButtonSubmit.setText("등록");

                final AlertDialog dialog = builder.create();


                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // String strID =  editTextID.getText().toString();
                        String strName = editTextName.getText().toString();

                        Dictionary dict = new Dictionary(strName);
                        mArrayList.add(0, dict);

                        mAdapter.notifyItemInserted(0);

                        dialog.dismiss();
                    }
                });

                ButtonSubmitcancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }

        });

    }
}