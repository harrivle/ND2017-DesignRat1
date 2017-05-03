package handlers;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import parser.ArtifactLibrary;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class crudwindow implements Observer {
	crudwindow o = this;
	protected Shell shell;
	public Text txtDesignName;
	public Text txtDesignDescription;
	// String[] requirementList;
	Vector<String> saveREQS = new Vector<String>();
	Vector<String> saveCODE = new Vector<String>();
	int newDesign = 0;

	//Open the window.
	public void open(ArtifactLibrary lib, String[] items) {
		System.out.println("CRUD");
		lib.addObserver(this);
		Display display = Display.getDefault();
		createContents(lib, items);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	//Create contents of the window.
	protected void createContents(ArtifactLibrary lib, String[] items) {
		shell = new Shell();
		shell.setSize(406, 309);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(3, false));
		new Label(shell, SWT.NONE);

		Label lblCrudview = new Label(shell, SWT.NONE);
		lblCrudview.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblCrudview.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 20, SWT.NORMAL));
		lblCrudview.setText("CRUDView");

		txtDesignName = new Text(shell, SWT.BORDER);
		GridData gd_txtDesignName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_txtDesignName.widthHint = 375;
		txtDesignName.setLayoutData(gd_txtDesignName);
		txtDesignName.setText(items[0]);

		txtDesignDescription = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_txtDesignDescription = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_txtDesignDescription.heightHint = 111;
		gd_txtDesignDescription.widthHint = 361;
		txtDesignDescription.setLayoutData(gd_txtDesignDescription);
		if (items[1] == "null") {
			txtDesignDescription.setText("Enter description here");
			newDesign = 1;
		}
		else {
			txtDesignDescription.setText(lib.getDesignDesc(items[0]));
		}

		// put initial requirements into saveREQS
		for (int i = 1; i < items.length; i++) {
			saveREQS.addElement(items[i]);
		}
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
				Button codeButton = new Button(shell, SWT.NONE);
				codeButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						codewindow code = new codewindow();
						code.open(lib, saveCODE);
						if (code.shell.isDisposed()) {
							// System.out.println("Window was closed");
							System.out.println(code.saveList.length);
							for (int i = 0; i < code.saveList.length; i++) {
								String x = code.saveList[i];
								saveCODE.addElement(x);
							}
						}

					}
				});
				codeButton.setText("View/Edit Code");
		Button reqButton = new Button(shell, SWT.NONE);
		reqButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				reqwindow req = new reqwindow(lib);
				// System.out.println(items[0]);
				req.open(lib, items[0]);
				if (req.shell.isDisposed()) {
					System.out.println("Window was closed");
					if (req.saveList != null) {
						System.out.println(req.saveList.length);
						for (int i = 0; i < req.saveList.length; i++) {
							String x = req.saveList[i];
							saveREQS.addElement(x);
						}
					}
				}
			}
		});
		reqButton.setText("View/Edit Requirements");
		new Label(shell, SWT.NONE);
		
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
				btnDelete.setText("Delete");
		
				Button btnCancel = new Button(shell, SWT.NONE);
				btnCancel.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						shell.close();
					}
				});
				btnCancel.setText("Cancel");
		
				// Save Button
				Button saveButton = new Button(shell, SWT.NONE);
				saveButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						String captureText = txtDesignName.getText();
						String captureDescription = txtDesignDescription.getText();
						if(newDesign ==0) {
							lib.setDesignDesc(captureText, captureDescription);
						} else {
							lib.addDesignDecision(captureText, captureDescription);
						}
					}
				});
				saveButton.setText("Save");

	}

	@Override
	public void update(Observable o, Object arg) {
		//updateContents();
	}
}