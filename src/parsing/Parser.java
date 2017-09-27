//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package parsing;

import java.util.Date;
import java.util.Map;

public abstract class Parser {
    int frameCount;
    Date[] datesArr;
    float[] qIn;
    float[] qOut;
    float[] alphaIn;
    float[] alphaOut;
    float[][] temperatures;
    Map<String, Integer> parsingKeys;
    private float tempMax;
    private float tempMin;
    private float qMin;
    private float qMax;
    private float alphaMin;
    private float alphaMax;
    public static int temperaturesCount;

    public Parser() {
    }

    public int getFrameCount() {
        return this.frameCount;
    }

    public Date[] getDatesArr() {
        return this.datesArr;
    }

    public float[] getQIn() {
        return this.qIn;
    }

    public float[] getQOut() {
        return this.qOut;
    }

    public float[] getAlphaIn() {
        return this.alphaIn;
    }

    public float[] getAlphaOut() {
        return this.alphaOut;
    }

    public float[][] getTemperaturesArray() {
        return this.temperatures;
    }

    void MinMax() {
        this.tempMax = this.temperatures[0][0];
        this.tempMin = this.temperatures[0][0];
        this.qMin = this.qIn[0];
        this.qMax = this.qIn[0];
        this.alphaMin = this.alphaIn[0];
        this.alphaMax = this.alphaIn[0];
        float[][] var1 = this.temperatures;
        int var2 = var1.length;

        int var3;
        for(var3 = 0; var3 < var2; ++var3) {
            float[] temperature = var1[var3];
            float[] var5 = temperature;
            int var6 = temperature.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                float aTemperature = var5[var7];
                if (aTemperature < this.tempMin) {
                    this.tempMin = aTemperature;
                }

                if (aTemperature > this.tempMax) {
                    this.tempMax = aTemperature;
                }
            }
        }

        float[] var9 = this.qIn;
        var2 = var9.length;

        float anAlphaOut;
        for(var3 = 0; var3 < var2; ++var3) {
            anAlphaOut = var9[var3];
            if (anAlphaOut > this.qMax) {
                this.qMax = anAlphaOut;
            }

            if (anAlphaOut < this.qMin) {
                this.qMin = anAlphaOut;
            }
        }

        var9 = this.qOut;
        var2 = var9.length;

        for(var3 = 0; var3 < var2; ++var3) {
            anAlphaOut = var9[var3];
            if (anAlphaOut > this.qMax) {
                this.qMax = anAlphaOut;
            }

            if (anAlphaOut < this.qMin) {
                this.qMin = anAlphaOut;
            }
        }

        var9 = this.alphaIn;
        var2 = var9.length;

        for(var3 = 0; var3 < var2; ++var3) {
            anAlphaOut = var9[var3];
            if (anAlphaOut > this.alphaMax) {
                this.alphaMax = anAlphaOut;
            }

            if (anAlphaOut < this.alphaMin) {
                this.alphaMin = anAlphaOut;
            }
        }

        var9 = this.alphaOut;
        var2 = var9.length;

        for(var3 = 0; var3 < var2; ++var3) {
            anAlphaOut = var9[var3];
            if (anAlphaOut > this.alphaMax) {
                this.alphaMax = anAlphaOut;
            }

            if (anAlphaOut < this.alphaMin) {
                this.alphaMin = anAlphaOut;
            }
        }

    }

    public float getTempMax() {
        return this.tempMax;
    }

    public float getTempMin() {
        return this.tempMin;
    }

    public float getQMaxAbs() {
        return Math.abs(this.qMin) > this.qMax ? Math.abs(this.qMin) : this.qMax;
    }

    public float getAlphaMaxAbs() {
        return Math.abs(this.alphaMin) > this.alphaMax ? Math.abs(this.alphaMin) : this.alphaMax;
    }
}
