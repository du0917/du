package com.example.day03text1;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment implements View.OnClickListener {


    private View view;
    /**
     * Hello blank fragment
     */
    private TextView mTvAddress;
    /**
     * 读联系人
     */
    private Button mRead;
    private ListView mLv;

    public AddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        Log.i("bei", "bei");
        readPersonPhone();
        mRead = (Button) view.findViewById(R.id.read);
        mRead.setOnClickListener(this);
        mLv = (ListView) view.findViewById(R.id.lv);
    }

    private ArrayList<String> readPersonPhone() {

        //ArrayList<PersonInfo> personInfos = new ArrayList<>();
        ArrayList<String> phones = new ArrayList<>();
        Uri datauri = Uri.parse("content://com.android.contacts/data");
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        // 读联系人id 表   uri   字段 contact_id    查询
        Cursor query = getActivity().getContentResolver().query(uri, new String[]{"contact_id"},
                null, null, null);
        if (query != null) {
            while (query.moveToNext()) {

                String id = query.getString(0);
                //PersonInfo personInfo = new PersonInfo();
                String str = "";// 拼接联系人信息  电话 姓名
                Cursor cursor = getActivity().getContentResolver().query(datauri, new String[]{"data1", "mimetype"},
                        "raw_contact_id=?", new String[]{id}, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        // 联系人信息
                        String data = cursor.getString(0);
                        String mimetype = cursor.getString(1);
                        if (mimetype.equals("vnd.android.cursor.item/name")) {
                            Log.i("tag", "联系人名称：" + data);
                            //personInfo.setName(data);// ""
                            str += data + "     "; // 拼接 姓名
                        }
                        if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                            Log.i("tag", "联系人电话：" + data);
                            //personInfo.setPhone(data); // 拼接电话
                            str += data;
                        }
                    }
                }
                phones.add(str);
            /*if (!personInfo.getName().isEmpty() && !personInfo.getPhone().isEmpty()) {
                //personInfos.add(personInfo);
            }*/
            }
        }
        //  读取 另一个张表  信息表     uri    字段 data1 mimetype     查询 raw_contact_id    对照
        return phones;
    }

    private void showPersonPhone() {
        ArrayList<String> personInfos = readPersonPhone();// 读取并获得联系人信息
        // 创建适配器
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, personInfos);
        // 设置适配器
        mLv.setAdapter(stringArrayAdapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 授权
            switch (requestCode) { // 那个在授权
                case 5:// 读取联系人
                    showPersonPhone();
                    break;

            }
        } else {
            // 隐式启动跳转到 设置的授权界面
            Log.i("tag", "不授权不能使用该功能,去手机设置中授权");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.read:
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS},
                            5);
                } else {
                    showPersonPhone();

                }

                break;
        }
    }
}
