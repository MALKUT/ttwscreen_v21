package com.android.ttwscreen_v21.Helper;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

public class Helper {
    public String idPatio;
    public Boolean cdContainer=false;
    public String idContainer;
    public String PreenCampo= "Campo Vazio";
    public String CamVazio= "Número do Container Incorreto ! ";
    public ShapeDrawable shapeDrawable(){
        ShapeDrawable shape= new ShapeDrawable(new RectShape());
        shape.getPaint().setColor(Color.RED);
        shape.getPaint().setStyle(Paint.Style.STROKE);
        shape.getPaint().setStrokeWidth(40);
        return shape;
    }

}
