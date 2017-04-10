package example.handlers;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class reqwindow {

	protected Shell shell;
	String[] saveList;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			reqwindow window = new reqwindow();
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
		
		Label lblReqwindow = new Label(shell, SWT.NONE);
		lblReqwindow.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 20, SWT.NORMAL));
		lblReqwindow.setBounds(152, 10, 126, 24);
		lblReqwindow.setText("ReqWindow");
		
		List wholeList = new List(shell, SWT.BORDER);
		wholeList.setBounds(39, 47, 110, 191);
		wholeList.add("Req 1", 0);
		wholeList.add("Req 2", 1);
		wholeList.add("Req 3", 2);
		
		
		List list = new List(shell, SWT.BORDER);
		list.setBounds(255, 47, 115, 191);
		
		Button moveRightButton = new Button(shell, SWT.NONE);
		moveRightButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = wholeList.getSelectionIndex();
				list.add(wholeList.getItem(index));
			}
		});
		moveRightButton.setBounds(154, 82, 95, 28);
		moveRightButton.setText("----->");
		
		Button button = new Button(shell, SWT.NONE);
		button.setBounds(154, 174, 95, 28);
		button.setText("<------");
		
		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveList = list.getItems();
				shell.close();
			}
		});
		btnSave.setBounds(275, 240, 95, 28);
		btnSave.setText("Save");
		button.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				int index2 = list.getSelectionIndex();
				list.remove(list.getItem(index2));
			}
		});

	}
}
