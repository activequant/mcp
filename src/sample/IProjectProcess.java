package sample;

import sample.model.ProjectObject;

public interface IProjectProcess {
	void processProjectObject(ProjectObject po) throws RenderException;
}
