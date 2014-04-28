package com.example.zoomtester006;

import java.util.ArrayList;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public class DrawLine {

    private Path line;
    private ArrayList<PointF> linePoints;
    private Paint paint;
    
    public DrawLine() {
	line = new Path();
	linePoints = new ArrayList<PointF>();
    }
    
    public void setStartLinePoint(PointF p) {
	line.moveTo(p.x, p.y);
	linePoints.add(p);
    }
    public void addLinePoint(PointF p) {
	line.lineTo(p.x, p.y);
	linePoints.add(p);
    }
    public void setLastLinePoint(PointF p) {
	line.setLastPoint(p.x, p.y);
	linePoints.add(p);
    }
    
    /**------------------------------ getter&setter ------------------------------**/
    public Path getLine() {
        return line;
    }
    public ArrayList<PointF> getLinePoints() {
        return linePoints;
    }    
    public Paint getPaint() {
        return paint;
    }
    
    
    public void setLine(Path line) {
        this.line = line;
    }
    public void setLinePoints(ArrayList<PointF> linePoints) {
        this.linePoints = linePoints;
    }
    public void setPaint(Paint paint) {
        this.paint = new Paint(paint);
    }
}
