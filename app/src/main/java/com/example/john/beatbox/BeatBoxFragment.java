package com.example.john.beatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZheWei on 2016/9/26.
 */
public class BeatBoxFragment extends Fragment {

    @BindView(R.id.fragment_beat_box_recycle)
    RecyclerView mFragmentBeatBoxRecycle;

    private BeatBox mBeatBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); //确保fragment不会和activity一起销毁和重建
        mBeatBox = new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beat_box, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    private void initView() {
        //这里使用的LayoutManager 是GridLayoutManager表格布局
        mFragmentBeatBoxRecycle.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mFragmentBeatBoxRecycle.setAdapter(new SoundAdapter(mBeatBox.getSoundList()));
    }


    class SoundAdapter extends RecyclerView.Adapter {
        private List<Sound> mSoundList;

        public SoundAdapter(List<Sound> soundList) {
            mSoundList = soundList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SoundHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SoundHolder soundHolder = (SoundHolder) holder;
            Sound sound = mSoundList.get(position);
            soundHolder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
            return mSoundList.size();
        }

        class SoundHolder extends RecyclerView.ViewHolder {
            Button mBtnSound;
            Sound mSound;

            public SoundHolder(LayoutInflater inflater, ViewGroup container) {
                super(inflater.inflate(R.layout.list_item_sound, container, false));
                mBtnSound = (Button) itemView.findViewById(R.id.btn_sound);
                mBtnSound.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBeatBox.play(mSound);
                    }
                });
            }

            public void bindSound(Sound sound) {
                mSound = sound;
                mBtnSound.setText(sound.getName());
            }
        }
    }
}
