package com.example.john.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZheWei on 2016/9/26.
 */
public class BeatBox {
    private static final String TAG = "BeatBox";//进行日志管理
    private static final String SOUNDS_FOLDER = "sample_sounds"; //assets目录下的folder
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssetManager;

    private List<Sound> mSoundList = new ArrayList<>();

    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAssetManager = context.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames = null;
        try {
            soundNames = mAssetManager.list(SOUNDS_FOLDER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String fileName : soundNames) {
            try {
                String assetName = SOUNDS_FOLDER + "/" + fileName;
                Sound sound = new Sound(assetName);
                load(sound);
                mSoundList.add(sound);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssetManager.openFd(sound.getAssetPath());
        Log.i(TAG, "load: " + (mSoundPool == null));
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public List<Sound> getSoundList() {
        return mSoundList;
    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release() {
        if (mSoundPool != null) {
            mSoundPool.release();
        }
    }
}
