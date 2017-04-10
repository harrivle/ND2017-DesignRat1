package example.handlers;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class crudwindow {

	protected Shell shell;
	private Text txtDesignName;
	private Text txtDesignDescription;
	//String[] requirementList;
	Vector<String> saveREQS = new Vector<String>();
	Vector<String> saveCODE = new Vector<String>();

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			crudwindow window = new crudwindow();
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
		
		Label lblCrudview = new Label(shell, SWT.NONE);
		lblCrudview.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 20, SWT.NORMAL));
		lblCrudview.setBounds(164, 10, 104, 24);
		lblCrudview.setText("CRUDView");
		
		txtDesignName = new Text(shell, SWT.BORDER);
		txtDesignName.setText("Design Name");
		txtDesignName.setBounds(10, 40, 359, 19);
		
		txtDesignDescription = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		txtDesignDescription.setText("Design Description");
		txtDesignDescription.setBounds(10, 65, 359, 113);
		
		Button reqButton = new Button(shell, SWT.NONE);
		reqButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				reqwindow req = new reqwindow();
				req.open();
				if(req.shell.isDisposed())
				{
					System.out.println("Window was closed"); 
					System.out.println(req.saveList.length);
					for(int i=0; i<req.saveList.length;i++){
						String x = req.saveList[i];
						saveREQS.addElement(x);
					}
				}
			}
		});
		reqButton.setBounds(10, 194, 115, 28);
		reqButton.setText("View/Edit Req");
		
		Button codeButton = new Button(shell, SWT.NONE);
		codeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				codewindow code = new codewindow();
				code.open();
				if(code.shell.isDisposed())
				{
					System.out.println("Window was closed"); 
					System.out.println(code.saveList.length);
					for(int i=0; i<code.saveList.length;i++){
						String x = code.saveList[i];
						saveCODE.addElement(x);
					}
				}
				
			}
		});
		codeButton.setBounds(131, 184, 121, 28);
		codeButton.setText("View/Edit Code");
		
		Button saveButton = new Button(shell, SWT.NONE);
		saveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String captureText = txtDesignName.getText();
				String captureDescription= txtDesignDescription.getText();
				System.out.printf("Name: %s Description: %s\n", captureText, captureDescription);
				for(int i =0; i<saveREQS.size();i++)
				{
					System.out.println(saveREQS.get(i));
				}
				for(int x =0; x<saveCODE.size();x++)
				{
					System.out.println(saveCODE.get(x));
				}
			}
		});
		saveButton.setBounds(10, 223, 95, 28);
		saveButton.setText("Save");
		
		Button btnDelete = new Button(shell, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtDesignName.setText("");
				txtDesignDescription.setText("");
				saveCODE.clear();
				saveREQS.clear();
			}
		});
		btnDelete.setBounds(111, 223, 95, 28);
		btnDelete.setText("Delete");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnCancel.setBounds(212, 223, 95, 28);
		btnCancel.setText("Cancel");

	}
}
