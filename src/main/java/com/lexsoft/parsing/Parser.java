//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lexsoft.parsing;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Parser implements ParserInterface {
    int frameCount = 0;
    int temperaturesCount = 0;
    Map<String, Integer> parsingKeys = new HashMap<>();
    Date[] datesArr;
    float[] qIn;
    float[] qOut;
    float[] alphaIn;
    float[] alphaOut;
    float[][] temperatures;
    private float tempMax;
    private float tempMin;
    private float qMin;
    private float qMax;
    private float alphaMin;
    private float alphaMax;

    @Override
    public int getTemperaturesCount() {
        return temperaturesCount;
    }

    @Override
    public int getFrameCount() {
        return frameCount;
    }

    @Override
    public Date[] getDates() {
        return datesArr;
    }

    @Override
    public float[] getQIn() {
        return qIn;
    }

    @Override
    public float[] getQOut() {
        return qOut;
    }

    @Override
    public float[] getAlphaIn() {
        return alphaIn;
    }

    @Override
    public float[] getAlphaOut() {
        return alphaOut;
    }

    @Override
    public float[][] getTemperatures() {
        return temperatures;
    }

    @Override
    public float getTempMax() {
        return tempMax;
    }

    @Override
    public float getTempMin() {
        return tempMin;
    }

    @Override
    public float getQMaxAbs() {
        float qMinAbs = Math.abs(qMin);
        return (qMinAbs > qMax) ? qMinAbs : qMax;
    }

    @Override
    public float getAlphaMaxAbs() {
        float alphaMinAbs = Math.abs(alphaMin);
        return (alphaMinAbs > alphaMax) ? alphaMinAbs : alphaMax;
    }


    void findMinAndMax() {
        this.tempMax = this.temperatures[0][0];
        this.tempMin = this.temperatures[0][0];
        this.qMin = this.qIn[0];
        this.qMax = this.qIn[0];
        this.alphaMin = this.alphaIn[0];
        this.alphaMax = this.alphaIn[0];

        for (float[] temperatureStrings : this.temperatures) {
            for (float temperature : temperatureStrings) {
                if (temperature < this.tempMin) {
                    this.tempMin = temperature;
                }
                if (temperature > this.tempMax) {
                    this.tempMax = temperature;
                }
            }
        }

        for (float QInVal : this.qIn) {
            if (QInVal > this.qMax) {
                this.qMax = QInVal;
            }
            if (QInVal < this.qMin) {
                this.qMin = QInVal;
            }
        }

        for (float QOutVal : this.qOut) {
            if (QOutVal > this.qMax) {
                this.qMax = QOutVal;
            }
            if (QOutVal < this.qMin) {
                this.qMin = QOutVal;
            }
        }

        for (float anAlphaIn : this.alphaIn) {
            if (anAlphaIn > this.alphaMax) {
                this.alphaMax = anAlphaIn;
            }
            if (anAlphaIn < this.alphaMin) {
                this.alphaMin = anAlphaIn;
            }
        }
        for (float anAlphaOut : this.alphaOut) {
            if (anAlphaOut > this.alphaMax) {
                this.alphaMax = anAlphaOut;
            }
            if (anAlphaOut < this.alphaMin) {
                this.alphaMin = anAlphaOut;
            }
        }
    }
}
