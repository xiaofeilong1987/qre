package fx.sunjoy.ui;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DetailsDlg extends Dialog {

	private Text text;
	private String showData;
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public DetailsDlg(Shell parentShell) {
		super(parentShell);
	}

	public DetailsDlg(Shell parentShell,List<String> matches){
		super(parentShell);
		StringBuilder sb = new StringBuilder();
		for(String s : matches){
			sb.append(s);
			sb.append("\r\n====================================================\r\n");
		}
		showData = sb.toString();
	}
	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FillLayout());

		text = new Text(container, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER | SWT.WRAP);
		//
		text.setText(showData);
		return container;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 375);
	}

}
