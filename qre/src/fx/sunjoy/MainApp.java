package fx.sunjoy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.swtdesigner.ResourceManager;

import fx.sunjoy.ui.AboutDlg;
import fx.sunjoy.ui.DetailsDlg;
import fx.sunjoy.utils.ReTool;

public class MainApp extends ApplicationWindow {

	private Action cleartextAction;
	private StyledText text_2;
	private Button ignoreCaseButton;
	private Button dotButton;
	private TableViewer tableViewer;
	private Text text_1;
	private Table table;
	private Action ignorecaseAction;
	private Action action_4;
	private Action action_2;
	private Action action_1;
	private Action action;
	private List<Integer[]> mc_locations;
	/**
	 * Create the application window
	 */
	public MainApp() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());

		final SashForm sashForm = new SashForm(container, SWT.NONE);

		final Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new FormLayout());

		dotButton = new Button(composite, SWT.CHECK);
		final FormData fd_dotButton = new FormData();
		fd_dotButton.top = new FormAttachment(0, 5);
		fd_dotButton.left = new FormAttachment(0, 5);
		dotButton.setLayoutData(fd_dotButton);
		dotButton.setText("DOTALL");

		ignoreCaseButton = new Button(composite, SWT.CHECK);
		final FormData fd_ignoreCaseButton = new FormData();
		fd_ignoreCaseButton.top = new FormAttachment(dotButton, 0, SWT.TOP);
		fd_ignoreCaseButton.right = new FormAttachment(0, 193);
		fd_ignoreCaseButton.left = new FormAttachment(0, 100);
		ignoreCaseButton.setLayoutData(fd_ignoreCaseButton);
		ignoreCaseButton.setText("IGNORE CASE");

		text_1 = new Text(composite, SWT.MULTI | SWT.BORDER);
		text_1.setToolTipText("此处填入正则表达式");
		final FormData fd_text_1 = new FormData();
		fd_text_1.bottom = new FormAttachment(37, 0);
		fd_text_1.right = new FormAttachment(100, -5);
		fd_text_1.left = new FormAttachment(0, 5);
		fd_text_1.top = new FormAttachment(0, 30);
		text_1.setLayoutData(fd_text_1);

		text_2 = new StyledText(composite, SWT.V_SCROLL | SWT.BORDER | SWT.WRAP);
		text_2.setToolTipText("在此填入要匹配的文本");
		final MenuManager menuM = new MenuManager();
		menuM.add(cleartextAction);
		text_2.setMenu( menuM.createContextMenu(text_2) );
		
		final FormData fd_text_2 = new FormData();
		fd_text_2.bottom = new FormAttachment(100, -5);
		fd_text_2.right = new FormAttachment(text_1, 0, SWT.RIGHT);
		fd_text_2.top = new FormAttachment(text_1, 5, SWT.BOTTOM);
		fd_text_2.left = new FormAttachment(text_1, 0, SWT.LEFT);
		text_2.setLayoutData(fd_text_2);

		tableViewer = new TableViewer(sashForm, SWT.FULL_SELECTION | SWT.BORDER);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				int ix = table.getSelectionIndex();
				if(ix>-1){
					Integer[] mc_location = mc_locations.get(ix);
					System.out.println(mc_location[0]+","+mc_location[1]);
					try{
						text_2.setSelectionRange(mc_location[0], mc_location[1]);
					}catch(Exception ex){
						//text_2.setSelectionRange(mc_location[0], 1);
					}
					text_2.showSelection();
				}
			}
		});
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent arg0) {
				List<String> matches = new ArrayList<String>();
				TableItem item = table.getSelection()[0];
				for(int i=0 ; i<table.getColumnCount(); i++){
					matches.add(item.getText(i));
				}
				DetailsDlg dlg = new DetailsDlg(getShell(),matches);
				dlg.open();
			}
		});
		tableViewer.setLabelProvider(new LabelProvider());
		tableViewer.setContentProvider(new ContentProvider());
		table = tableViewer.getTable();
		
		table.setBounds(168, 71, 100, 100);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		sashForm.setWeights(new int[] {4, 5 });



		//
		return container;
	}

	/**
	* 取文字颜色对象
	* @param startOffset
	* @param length
	* @param color
	* @return
	*/
	private StyleRange getColorStyle(int startOffset, int length, Color color) {
	    StyleRange styleRange = new StyleRange(startOffset, length, color, null);

	    styleRange.fontStyle = SWT.BOLD;

	    return styleRange;
	}
	
	
	
	private void exitApp(){
		this.close();
	}
	/**
	 * Create the actions
	 */
	private void createActions() {

		action = new Action("清空") {
			public void run() {
				text_1.setText("");
				text_2.setText("");
				table.clearAll();
			}
		};
		action.setImageDescriptor(ResourceManager.getImageDescriptor(MainApp.class, "/clear(1).gif"));
		action.setAccelerator(SWT.CTRL | 'D');

		action_1 = new Action("退出") {
			public void run() {
				exitApp();
			}
		};
		action_1.setAccelerator(SWT.CTRL | 'W');

		action_2 = new Action("关于") {
			public void run() {
				AboutDlg dlg = new AboutDlg(getShell());
				dlg.open();
			}
		};
		action_2.setImageDescriptor(ResourceManager.getImageDescriptor(MainApp.class, "/help_contents(1).gif"));

		action_4 = new Action("开始测试") {
			public void run() {
				if(text_1.getText().equals(""))
					return;
				int flag = 0;
				for (TableColumn tc : table.getColumns()) {
					tc.dispose();
				}
				if(dotButton.getSelection())
					flag |= Pattern.DOTALL;
				if(ignoreCaseButton.getSelection())
					flag |= Pattern.CASE_INSENSITIVE;
				
				mc_locations = ReTool.getMatchLocation(text_1.getText(), text_2.getText(), flag);
				
				text_2.setStyleRange(null);
				for(Integer[] location : mc_locations){
					text_2.setStyleRange(getColorStyle(location[0], location[1], getShell().getDisplay().getSystemColor(SWT.COLOR_RED)));
					//text_2.setSelectionRange(location[0],location[1]);
				}
				//text_2.showSelection();
				List<List<String>> result = ReTool.getMatches(text_1.getText(), text_2.getText(), flag);
				if(null != result && result.size()>0){
					int groupSize = result.get(0).size();
					for(int i=0; i<groupSize; i++){
						TableColumn tableColumn = new TableColumn(table,SWT.NONE);
						tableColumn.setText("Group"+i);
						tableColumn.setWidth((table.getSize().x-15)/groupSize);
					}
				}
				table.setRedraw(false);
				tableViewer.setInput(result);
				table.setRedraw(true);
			}
		};
		action_4.setAccelerator(SWT.CTRL | 'R');
		action_4.setImageDescriptor(ResourceManager.getImageDescriptor(MainApp.class, "/run_exc(2).gif"));

		ignorecaseAction = new Action("IgnoreCase") {
			public void run() {
			}
		};
		ignorecaseAction.setChecked(true);

		cleartextAction = new Action("清空") {
			public void run() {
				text_2.setText("");
			}
		};
		// Create the actions
	}

	/**
	 * Create the menu manager
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");

		final MenuManager menuManager_1 = new MenuManager("文件(&F)");
		menuManager.add(menuManager_1);

		menuManager_1.add(action_4);

		menuManager_1.add(action);

		menuManager_1.add(action_1);

		final MenuManager menuManager_2 = new MenuManager("帮助(&H)");
		menuManager.add(menuManager_2);

		menuManager_2.add(action_2);
		return menuManager;
	}

	/**
	 * Create the toolbar manager
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);

		toolBarManager.add(action_4);

		toolBarManager.add(action);
		return toolBarManager;
	}

	/**
	 * Create the status line manager
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		statusLineManager.setMessage(null, "");
		return statusLineManager;
	}

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			MainApp window = new MainApp();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("QRe - 正则表达式测试工具");
		newShell.setSize(500,375);
	}

	/**
	 * Return the initial size of the window
	 
	@Override
	protected Point getInitialSize() {
		return new Point(500, 375);
	}*/

}
