package handlers;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import parser.ArtifactLibrary;
import parser.Requirement;

import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.viewers.ListViewer;

public class reqwindow implements Observer {
	protected Shell shell;
	String[] saveList;
	List wholeList;
	List reqList;
	ArtifactLibrary lib;
	
	reqwindow(ArtifactLibrary lib) {
		this.lib = lib;
		lib.addObserver(this);
	}

	/**
	 * Open the window.
	 */
	public void open(ArtifactLibrary lib, String designId) {
		System.out.println("REQ");
		
		Display display = Display.getDefault();
		createContents(designId);
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
	 * 
	 * @wbp.parser.entryPoint
	 */
	protected void createContents(String designId) {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");

		Label lblReqwindow = new Label(shell, SWT.CENTER);
		lblReqwindow.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 20, SWT.NORMAL));
		lblReqwindow.setBounds(23, 10, 391, 24);
		lblReqwindow.setText("Select Requirements for " + designId);

		wholeList = new List(shell, SWT.BORDER | SWT.V_SCROLL);
		wholeList.setBounds(23, 47, 142, 221);

		List reqList = new List(shell, SWT.BORDER | SWT.V_SCROLL);
		reqList.setBounds(262, 47, 152, 187);
		
		for (Entry<String, String> R : lib.getRequirements().map.entrySet()) {
			wholeList.add(R.getKey());// + ": " + R.getValue().description);
		}
		for (String str : lib.getReqIdList(designId)) {
			reqList.add(str);
		}

		Button moveRightButton = new Button(shell, SWT.CENTER);
		moveRightButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = wholeList.getSelectionIndex();
				reqList.add(wholeList.getItem(index));
			}
		});
		moveRightButton.setBounds(171, 82, 86, 28);
		moveRightButton.setText("----->");

		Button button = new Button(shell, SWT.NONE);
		button.setBounds(171, 174, 86, 28);
		button.setText("<------");

		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// on save, add items to set and send to library
				saveList = reqList.getItems();
				HashSet<String> saveSet = new HashSet<String>();
				for (String id : saveList)
					saveSet.add(id);
				lib.setReqIdList(designId, saveSet);
				shell.close();
			}
		});
		btnSave.setBounds(295, 240, 95, 28);
		btnSave.setText("Save");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index2 = reqList.getSelectionIndex();
				reqList.remove(reqList.getItem(index2));
			}
		});

		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnCancel.setBounds(223, 240, 95, 28);
		btnCancel.setText("Cancel");

	}
	
	public void updateContents(String designId) {
		for (Entry<String, String> R : lib.getRequirements().map.entrySet()) {
			wholeList.add(R.getKey());// + ": " + R.getValue().description);
		}
		for (String str : lib.getReqIdList(designId)) {
			reqList.add(str);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}
