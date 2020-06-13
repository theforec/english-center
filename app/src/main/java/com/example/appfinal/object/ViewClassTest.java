package com.example.appfinal.object;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appfinal.GiaoVienActivity;
import com.example.appfinal.R;
import com.example.appfinal.fragment.giaoVien.GV_LearnFragment;

import java.util.ArrayList;
import java.util.Collections;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewClassTest implements TestAdapter.OnItemListener {
    private TextView textView_lop;
    private RecyclerView recyclerView_test;
    private ArrayList<Test> arrTest;
    private View view;
    private GV_LearnFragment mContext;

    public ViewClassTest(GV_LearnFragment context, ArrayList<Test> testArrayList) {
        mContext = context;
        arrTest = testArrayList;
        LayoutInflater inflater = LayoutInflater.from(context.getContext());
        view = inflater.inflate(R.layout.item_view_class_test, null);
        textView_lop = view.findViewById(R.id.label_name_class_test);
        recyclerView_test = view.findViewById(R.id.layout_list_test);

        if (testArrayList.size() != 0) {
            textView_lop.setText(testArrayList.get(0).getLop());
            Collections.reverse(testArrayList);

            TestAdapter testAdapter = new TestAdapter(context.getContext(), testArrayList);
            testAdapter.setOnItemClickListener(ViewClassTest.this);

            LinearLayoutManager lm = new LinearLayoutManager(context.getContext());
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView_test.setLayoutManager(lm);
            recyclerView_test.setAdapter(testAdapter);
        }

    }

    @Override
    public void onItemTestClick(int position) {
        GiaoVienActivity giaoVienActivity = (GiaoVienActivity) mContext.getActivity();
        if (giaoVienActivity != null) {
            giaoVienActivity.setTest(arrTest.get(position));
            giaoVienActivity.change(GiaoVienActivity.CHANGE_TO_TEST);
        }
    }

    public TextView getTextView_lop() {
        return textView_lop;
    }

    public void setTextView_lop(TextView textView_lop) {
        this.textView_lop = textView_lop;
    }

    public RecyclerView getRecyclerView_test() {
        return recyclerView_test;
    }

    public void setRecyclerView_test(RecyclerView recyclerView_test) {
        this.recyclerView_test = recyclerView_test;
    }

    public ArrayList<Test> getArrTest() {
        return arrTest;
    }

    public void setArrTest(ArrayList<Test> arrTest) {
        this.arrTest = arrTest;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public GV_LearnFragment getmContext() {
        return mContext;
    }

    public void setmContext(GV_LearnFragment mContext) {
        this.mContext = mContext;
    }
}
