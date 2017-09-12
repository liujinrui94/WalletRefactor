package com.chengxiang.pay.framework.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.chengxiang.pay.R;

public class CustomEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher {
    private boolean shouldStopChange = false;
    private final String BLANK_SPACE = " ";
    private int customEditType = -1;

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEditText(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEditText(context, attrs);
    }

    private void initEditText(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        customEditType = array.getInt(R.styleable.CustomEditText_editType, customEditType);
        array.recycle();
        formatEdit(getText());
        shouldStopChange = false;
        setFocusable(true);
        setEnabled(true);
        setFocusableInTouchMode(true);
        setInputMax();
        addTextChangedListener(this);
    }

    private void setInputMax() {
        switch (customEditType) {
            case 0:
                InputFilter[] filters = {new InputFilter.LengthFilter(23)};
                setFilters(filters);
                break;
            case 1:
                InputFilter[] phoneFilters = {new InputFilter.LengthFilter(13)};
                setFilters(phoneFilters);
                break;
            case 2:
                InputFilter[] idFilters = {new InputFilter.LengthFilter(21)};
                setFilters(idFilters);
                break;
        }
    }


    /**
     * 格式化输入状态
     *
     * @param editable 可变文本
     */
    private void formatEdit(Editable editable) {
        if (shouldStopChange) {
            shouldStopChange = false;
            return;
        }

        shouldStopChange = true;

        String editString = editable.toString().trim().replaceAll(BLANK_SPACE, "");

        int len = editString.length();
        int courPosition;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(editString.charAt(i));
            if ((i == 3 || i == 7 || i == 11 || i == 15) && customEditType == 0) {
                if (i != len - 1)
                    builder.append(BLANK_SPACE);
            } else if ((i == 2 || i == 6) && customEditType == 1) {
                if (i != len - 1)
                    builder.append(BLANK_SPACE);
            } else if ((i == 5 || i == 9 || i == 13) && customEditType == 2) {
                if (i != len - 1)
                    builder.append(BLANK_SPACE);
            }
        }
        courPosition = builder.length();
        setText(builder.toString());
        setSelection(courPosition);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        formatEdit(editable);
    }

    public String getCustomEditText() {
        return getText().toString().trim().replaceAll(BLANK_SPACE, "");
    }

}
