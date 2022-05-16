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
import com.example.gramy.home.fragHomemain;

import java.util.ArrayList;

import GoMgSelf.CustomAdapter;
import GoMgSelf.Dictionary;

public class GoMgShelfActivity extends AppCompatActivity {
        private ArrayList<Dictionary> mArrayList;
        private CustomAdapter mAdapter;
        private int count = -1;
        fragHomemain fragHomemain;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_go_mg_shelf);

            RecyclerView recyclerView =findViewById(R.id.report_recyclerView);

            LinearLayoutManager LayoutManager =
                        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(LayoutManager);
            GomyshelfAdapter adapter = new GomyshelfAdapter();

            adapter.addItem(new GomyshelfVO("1선반"));

            recyclerView.setAdapter(adapter);


            Button buttonInsert = (Button) findViewById(R.id.btnshelf_enroll);
            Button buttonCancel = (Button) findViewById(R.id.btnshelf_cancle);

            buttonInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;

                    AlertDialog.Builder builder = new AlertDialog.Builder(GoMgShelfActivity.this);
                    View view = LayoutInflater.from(GoMgShelfActivity.this).inflate(R.layout.shelf_editbox, null, false);
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

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
}