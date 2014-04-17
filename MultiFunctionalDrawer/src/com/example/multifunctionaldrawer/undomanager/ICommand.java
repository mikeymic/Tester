package com.example.multifunctionaldrawer.undomanager;
public interface ICommand {
    public void invoke();
    public void undo();
    public void redo();
    public void clear();
}