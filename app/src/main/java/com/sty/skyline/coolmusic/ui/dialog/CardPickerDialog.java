package com.sty.skyline.coolmusic.ui.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.sty.skyline.coolmusic.R;

/**
 * Created by Steven.S on 2018/6/6/0006.
 */
public class CardPickerDialog extends DialogFragment implements View.OnClickListener{
    private static final String TAG = CardPickerDialog.class.getSimpleName();
    ImageView[] mCards = new ImageView[8];
    Button mConfirm;
    Button mCancel;

    private int mCurrentTheme;
    private ClickListener mClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert);
        mCurrentTheme = ThemeHelper.getTheme(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_theme_picker, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCancel = view.findViewById(android.R.id.button2);
        mConfirm = view.findViewById(android.R.id.button1);
        mCards[0] = view.findViewById(R.id.theme_pink);
        mCards[1] = view.findViewById(R.id.theme_purple);
        mCards[2] = view.findViewById(R.id.theme_blue);
        mCards[3] = view.findViewById(R.id.theme_green);
        mCards[4] = view.findViewById(R.id.theme_green_light);
        mCards[5] = view.findViewById(R.id.theme_yellow);
        mCards[6] = view.findViewById(R.id.theme_orange);
        mCards[7] = view.findViewById(R.id.theme_red);
        setImageButtons(mCurrentTheme);
        for(ImageView card : mCards){
            card.setOnClickListener(this);
        }
        mCancel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    private void setImageButtons(int mCurrentTheme){
        mCards[0].setSelected(mCurrentTheme == ThemeHelper.CARD_SAKURA);
        mCards[1].setSelected(mCurrentTheme == ThemeHelper.CARD_HOPE);
        mCards[2].setSelected(mCurrentTheme == ThemeHelper.CARD_STORM);
        mCards[3].setSelected(mCurrentTheme == ThemeHelper.CARD_WOOD);
        mCards[4].setSelected(mCurrentTheme == ThemeHelper.CARD_LIGHT);
        mCards[5].setSelected(mCurrentTheme == ThemeHelper.CARD_THUNDER);
        mCards[6].setSelected(mCurrentTheme == ThemeHelper.CARD_SAND);
        mCards[7].setSelected(mCurrentTheme == ThemeHelper.CARD_FIREY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case android.R.id.button1:
                if(mClickListener != null){
                    mClickListener.onConfirm(mCurrentTheme);
                }
                dismiss();
                break;
            case android.R.id.button2:
                dismiss();
                break;
            case R.id.theme_pink:
                mCurrentTheme = ThemeHelper.CARD_SAKURA;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_purple:
                mCurrentTheme = ThemeHelper.CARD_HOPE;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_blue:
                mCurrentTheme = ThemeHelper.CARD_STORM;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_green:
                mCurrentTheme = ThemeHelper.CARD_WOOD;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_green_light:
                mCurrentTheme = ThemeHelper.CARD_LIGHT;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_yellow:
                mCurrentTheme = ThemeHelper.CARD_THUNDER;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_orange:
                mCurrentTheme = ThemeHelper.CARD_SAND;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_red:
                mCurrentTheme = ThemeHelper.CARD_FIREY;
                setImageButtons(mCurrentTheme);
                break;
            default:
                break;
        }
    }

    public interface ClickListener{
        void onConfirm(int currentTheme);
    }

    public void setClickListener(ClickListener clickListener){
        this.mClickListener = clickListener;
    }
}
