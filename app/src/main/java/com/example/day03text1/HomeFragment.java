package com.example.day03text1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    private View view;
    private RecyclerView mRecyclerView;
    private ArrayList<Bean.ResultsBean> mDatasBeans;
    private RecyAdpter mRecyAdpter;
    /**
     * 修改
     */
    private EditText mUpdate;
    private Button mDelete;
    private Button mBtUpdate;
    private Button mBtNo;
    private PopupWindow mPopupWindow;
    private FrameLayout mFrame;
    private int mposition;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        mDatasBeans = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyAdpter = new RecyAdpter(mDatasBeans, getContext());
        mRecyclerView.setAdapter(mRecyAdpter);
        initData();
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.layout_popu, null);
        mUpdate = (EditText) view1.findViewById(R.id.update);
        mDelete = (Button) view1.findViewById(R.id.Delete);
        mDelete.setOnClickListener(this);
        mBtUpdate = (Button) view1.findViewById(R.id.bt_update);
        mBtUpdate.setOnClickListener(this);
        mBtNo = (Button) view1.findViewById(R.id.bt_no);
        mBtNo.setOnClickListener(this);
        mPopupWindow = new PopupWindow(view1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRecyAdpter.setOnclick(new RecyAdpter.Onclick() {
            @Override
            public void onclick(int position) {
                mposition=position;
                mPopupWindow.showAtLocation(mFrame, Gravity.CENTER,0,0);
                String desc = mDatasBeans.get(position).getDesc();
                mUpdate.setText(desc);
            }
        });
        mPopupWindow.setFocusable(true);
        mFrame = (FrameLayout) view.findViewById(R.id.Frame);
    }

    private void initData() {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(ApiService.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        build.create(ApiService.class).mBean().enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                mDatasBeans.addAll(response.body().getResults());
                mRecyAdpter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Bean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.Delete:
                mDatasBeans.remove(mposition);
                mRecyAdpter.notifyDataSetChanged();
                mPopupWindow.dismiss();
                break;
            case R.id.bt_update:
                String update = mUpdate.getText().toString();
                mDatasBeans.get(mposition).setDesc(update);
                mRecyAdpter.notifyDataSetChanged();
                mPopupWindow.dismiss();
                break;
            case R.id.bt_no:
                mPopupWindow.dismiss();

                break;
        }
    }
}
