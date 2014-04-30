package com.example.zoomtester008;
public interface ICommand {
    public void invoke();
    public void undo();
    public void redo();
    public void clear();
}