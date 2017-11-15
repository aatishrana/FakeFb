package com.aatishrana.fakefb.model;

import com.aatishrana.fakefb.utils.Const;

/**
 * Created by Aatish Rana on 15-Nov-17.
 */

public class BoldText extends Text
{
    public BoldText(String textData)
    {
        super(textData, new TextStyle(0, textData.length(), Const.BOLD));
    }
}
