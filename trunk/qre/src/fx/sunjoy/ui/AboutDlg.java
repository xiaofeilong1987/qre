package fx.sunjoy.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AboutDlg extends TitleAreaDialog {

	@Override
	protected Control createContents(Composite parent) {
		// TODO Auto-generated method stub
		Control contents = super.createContents(parent);
		setMessage("Welcome to use QRe!");
		return contents;
	}

	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public AboutDlg(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label qreIsALabel = new Label(container, SWT.NONE);
		qreIsALabel.setText("Description: QRe is a tool to test regular expressions.");
		qreIsALabel.setBounds(29, 28, 341, 12);

		final Label version01Label = new Label(container, SWT.NONE);
		version01Label.setText("Version: 0.1");
		version01Label.setBounds(29, 57, 105, 12);

		final Label emailToSunjunyisoftwareictaccnLabel = new Label(container, SWT.NONE);
		emailToSunjunyisoftwareictaccnLabel.setText("E-Mail to: sunjunyi@software.ict.ac.cn");
		emailToSunjunyisoftwareictaccnLabel.setBounds(31, 93, 242, 12);
		//
		return area;
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
		return new Point(388, 281);
	}

}
