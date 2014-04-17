package com.example.multifunctionaldrawer.undomanager;

import java.util.Stack;

public class CommandInvoker {
    private Stack<ICommand> mUndoBuffer;
    private Stack<ICommand> mRedoBuffer;

    public CommandInvoker() {
         mUndoBuffer = new Stack<ICommand>();
         mRedoBuffer = new Stack<ICommand>();
    }

    public void invoke(ICommand command) {
        command.invoke();
        mRedoBuffer.clear();
        mUndoBuffer.push(command);
    }

    public void undo() {
        if (mUndoBuffer.isEmpty()) {
             return;
        }
        ICommand command = mUndoBuffer.pop();
	command.undo();
	mRedoBuffer.push(command);
    }

    public void redo() {
        if (mRedoBuffer.isEmpty()) {
	    return;
	}
        ICommand command = mRedoBuffer.pop();
	command.redo();
	mUndoBuffer.push(command);
    }
    public void clear() {
        if (mRedoBuffer.isEmpty()) {
	    return;
	}
        ICommand command = mRedoBuffer.pop();
	command.clear();
	mUndoBuffer.push(command);
    }
}