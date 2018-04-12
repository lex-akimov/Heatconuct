package com.lexsoft.Heatconduct.parsing;

import java.util.Date;

public interface ParserInterface {
    int getTemperaturesCount();

    int getFrameCount();

    Date[] getDates();

    float[][] getTemperatures();

    float[] getQIn();

    float[] getQOut();

    float[] getAlphaIn();

    float[] getAlphaOut();

    float getTempMax();

    float getTempMin();

    float getQMaxAbs();

    float getAlphaMaxAbs();

}
