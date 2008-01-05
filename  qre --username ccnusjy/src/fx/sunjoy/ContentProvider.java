package fx.sunjoy;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ContentProvider implements IStructuredContentProvider {

	@SuppressWarnings("unchecked")
	public Object[] getElements(Object arg0) {
		// TODO Auto-generated method stub
		return ((List<List<String>>)arg0).toArray();
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

}
