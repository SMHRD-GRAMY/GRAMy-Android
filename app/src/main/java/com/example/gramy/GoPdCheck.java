//package com.example.gramy;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.gramy.Vo_Info.ShelfStockVO;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class GoPdCheck extends AppCompatActivity {
//
//    RequestQueue queue;
//    ArrayList<ShelfStockVO> items = new ArrayList<ShelfStockVO>();
//    Button shelfRegisterBtn;
//    Button[] stockbtn=new Button[4];
//    TextView shelfTv;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_go_ck_pd);
//
//
//        shelfTv=findViewById(R.id.tvShelfTitle);
//        shelfRegisterBtn=findViewById(R.id.btnShelfRegister);
//
//        queue = Volley.newRequestQueue(GoPdCheck.this); // GoBoardActivity에 Queue 생성
//
//
//        // 현재 로그인 한 유저 정보 가져오기
//        SharedPreferences sharedPreferences = getSharedPreferences("sf_login", MODE_PRIVATE);
//        String writerName = sharedPreferences.getString("user_name", "");
//        String writerId = sharedPreferences.getString("user_id","");
//
//
//        getStockList(writerId); //  목록 불러오기
//        Log.d("v",items.toString());
//        shelfRegisterBtn.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(GoPdCheck.this, RegisterShelfActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }) ;
//    }
//
//    // 목록 가져오는 메서드
//    private void getStockList(String writerId) {
//        int method = Request.Method.POST;
//        String server_url = "http://172.30.1.42:8082/product/stocklist";
//        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//                    for(int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject listItem = jsonArray.getJSONObject(i);
//                        int shelf_seq = listItem.getInt("shelf_seq");
//                        String shelf_name = listItem.getString("shelf_name");
//                        String user_id = listItem.getString("user_id");
//                        int stock_seq = listItem.getInt("stock_seq");
//                        String stock_name = listItem.getString("stock_name");
//                        ShelfStockVO item = new ShelfStockVO(shelf_seq, shelf_name, user_id, stock_seq, stock_name);
//                        items.add(item);
//                    }
//                    //물품 이름 리스트 배열 만들기
//                    ArrayList<String> nameList=new ArrayList<String>();
//                    //버튼 아이디 리스트 만들기
//
//                    //가져온 데이터 크기가 있는지 여부 판단
//                    if (items.size() > 0) {
//
//                        //선반 번호 가져오기
//                        int shelf_seq=items.get(0).getShelf_seq();
//                        Log.d("v", "test"+shelf_seq);
//                        //선반 이름 가져오기
//                        String shelfName = items.get(0).getShelf_name();
//                        Log.d("v", "test"+shelfName);
//                        //선반에 있는 물품 이름 리스트에 담아주기
//                        for(int i=0;i<items.size();i++){
//                            nameList.add(items.get(i).getStock_name());
//                        };
//                        for(int i=0; i<items.size(); i++) {
//                            String buttonID = "stockbtn" + (i+1);
//                            int resID=getResources().getIdentifier(buttonID,"id",getPackageName());
//                            int stock_seq=items.get(i).getStock_seq();
//                            stockbtn[i]=findViewById(resID);
//                            stockbtn[i].setText(nameList.get(i));
//                            //버튼클릭시 이벤트
//                            stockbtn[i].setOnClickListener(new Button.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent intent=new Intent(GoPdCheck.this, StockCheckActivity.class);
//                                    intent.putExtra("shelf_seq",shelf_seq);
//                                    intent.putExtra("stock_seq",stock_seq);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }) ;
//                        }
//                        for(int i=items.size();i<4;i++){
//                            String buttonID = "stockbtn" + (i+1);
//                            int resID=getResources().getIdentifier(buttonID,"id",getPackageName());
//                            stockbtn[i]=findViewById(resID);
//                            stockbtn[i].setText("물품등록");
//                            stockbtn[i].setOnClickListener(new Button.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent intent=new Intent(GoPdCheck.this,StockActivity.class);
//                                    intent.putExtra("shelf_seq",shelf_seq);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }) ;
//                        }
//                        shelfTv.setText(shelfName);
//                    } else {
//                        shelfTv.setText("선반이 존재하지 않습니다");
//                        Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
//                        for(int i=0; i<4; i++) {
//                            String buttonID = "stockbtn" + (i+1);
//                            int resID=getResources().getIdentifier(buttonID,"id",getPackageName());
//                            stockbtn[i]=findViewById(resID);
//                            stockbtn[i].setText("물품등록");
//                            stockbtn[i].setOnClickListener(new Button.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Toast.makeText(GoPdCheck.this, "선반을 먼저 등록해주세요", Toast.LENGTH_SHORT).show();
////                                    Intent intent=new Intent(GoPdCheck.this,StockActivity.class);
////                                    startActivity(intent);
////                                    finish();
//                                }
//                            }) ;
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                // 보낼 데이터
//                // 1. title, content, user_id, user_name
//                Map<String, String> param = new HashMap<>();
//                param.put("user_id", writerId);
//                return param;
//            }
//        };
//
//        queue.add(request);
//    }
//}