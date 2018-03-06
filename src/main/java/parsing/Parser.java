//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package parsing;

import java.util.Date;
import java.util.Map;

public abstract class Parser {
    protected int frameCount;
    protected Date[] datesArr;
    protected float[] qIn;
    protected float[] qOut;
    protected float[] alphaIn;
    protected float[] alphaOut;
    protected float[][] temperatures;
    protected Map parsingKeys;
    protected float tempMax;
    protected float tempMin;
    protected float qMin;
    protected float qMax;
    protected float alphaMin;
    protected float alphaMax;
    protected static int temperaturesCount;

    private static int getTemperaturesCount() {
        return temperaturesCount;
    }

    public int getFrameCount() {
        return this.frameCount;
    }

    public Date[] getDatesArray() {
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

    public float getTempMax() {
        return this.tempMax;
    }

    public float getTempMin() {
        return this.tempMin;
    }

    public float getQMaxAbs() {
        if (Math.abs(this.qMin) > this.qMax) return Math.abs(this.qMin);
        else return this.qMax;
    }

    public float getAlphaMaxAbs() {
        if (Math.abs(this.alphaMin) > this.alphaMax) return Math.abs(this.alphaMin);
        else return this.alphaMax;
    }
}
