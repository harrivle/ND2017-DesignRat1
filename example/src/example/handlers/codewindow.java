package example.handlers;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class codewindow {

	protected Shell shell;
	String[] saveList;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			codewindow window = new codewindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		List allCodeList = new List(shell, SWT.BORDER);
		allCodeList.setBounds(33, 36, 122, 205);
		allCodeList.add("Code1.java");
		allCodeList.add("Code2.java");
		allCodeList.add("Code3.java");
		
		List selectedCodeList = new List(shell, SWT.BORDER);
		selectedCodeList.setBounds(255, 36, 122, 205);
		
		Label lblCodeview = new Label(shell, SWT.NONE);
		lblCodeview.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 20, SWT.NORMAL));
		lblCodeview.setBounds(154, 10, 102, 20);
		lblCodeview.setText("CodeView");
		
		Button addToList = new Button(shell, SWT.NONE);
		addToList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index= allCodeList.getSelectionIndex();
				selectedCodeList.add(allCodeList.getItem(index));
			}
		});
		addToList.setBounds(154, 72, 95, 28);
		addToList.setText("----->");
		
		Button removeFromList = new Button(shell, SWT.NONE);
		removeFromList.setBounds(161, 156, 95, 28);
		removeFromList.setText("<-------");
		removeFromList.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				int index2 = selectedCodeList.getSelectionIndex();
				selectedCodeList.remove(selectedCodeList.getItem(index2));
			}
		});
		
		Button btnSaveexit = new Button(shell, SWT.NONE);
		btnSaveexit.setBounds(161, 240, 95, 28);
		btnSaveexit.setText("Save&Exit");
		btnSaveexit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveList = selectedCodeList.getItems();
				shell.close();
			}
		});

	}

}
