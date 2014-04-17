package com.example.multifunctionaldrawer.undomanager;
public class AddLineCommand extends DataModelCommand {

    Line line;

    public AddLineCommand(DataModel dataModel, Line line) {
        super(dataModel);
        this.line = line;
    }

    public void invoke() {
        mDataModel.lines.add(line);
    }

    public void redo() {
        mDataModel.lines.add(line);
    }

    public void undo() {
        mDataModel.lines.remove(line);
    }

	@Override
	public void clear() {

	}

}