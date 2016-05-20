package com.douncoding.readingsalon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.douncoding.readingsalon.data.Contents;
import com.google.gson.Gson;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = DetailActivity.class.getSimpleName();

    Contents mContents;
    ContentsInteractor mInteractor;

    OnKeyBackPressedListener onKeyBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String jsonPost = getIntent().getStringExtra("contents");
        mContents = new Gson().fromJson(jsonPost, Contents.class);
        mInteractor = new ContentsInteractor(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mInteractor.getContens(mContents.getId(), new ContentsInteractor.OnDetailListener() {
            @Override
            public void onLoad(Contents contents) {
                if (contents == null) {
                    Log.w(TAG, "포스트 세부정보를 읽을 수 없음: 기본정보 대체");
                } else {
                    mContents = contents;
                }
                showPostFragment();
            }
        });
    }

    public interface OnKeyBackPressedListener {
        void onBack();
    }

    public void setOnKeyBackPressedListener(OnKeyBackPressedListener listener) {
        this.onKeyBackPressedListener = listener;
    }

    public void showPostFragment() {
       getSupportFragmentManager().beginTransaction()
               .replace(R.id.detail_container,
                        PostFragment.newInstance(new Gson().toJson(mContents)))
               .commit();

        onKeyBackPressedListener = null;
    }

    public void showCommentFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_container,
                        CommentsFragment.newInstance(mContents.getId()))
                .commit();

        onKeyBackPressedListener = new OnKeyBackPressedListener() {
            @Override
            public void onBack() {
                showPostFragment();
            }
        };
    }

    @Override
    public void onBackPressed() {
        if (onKeyBackPressedListener != null) {
            onKeyBackPressedListener.onBack();
        } else {
            super.onBackPressed();
        }
    }
}
