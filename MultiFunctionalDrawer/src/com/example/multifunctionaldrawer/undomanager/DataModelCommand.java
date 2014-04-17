package com.example.multifunctionaldrawer.undomanager;

public abstract class DataModelCommand implements ICommand {
    protected DataModel mDataModel;

    public DataModelCommand(DataModel dataModel) {
        mDataModel = dataModel;
    }
}
