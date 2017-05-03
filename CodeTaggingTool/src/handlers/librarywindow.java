package handlers;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

import parser.ArtifactLibrary;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class librarywindow implements Observer {
	protected Shell shell;
	ArtifactLibrary lib;
	List LibraryList;

	public librarywindow(ArtifactLibrary lib) {
		this.lib = lib;
		lib.addObserver(this);
	}

	/**
	 * Open the window.
	 */
	public void open() {
		System.out.println("LIB");
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
	 * 
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(null);

		LibraryList = new List(shell, SWT.BORDER | SWT.V_SCROLL);
		LibraryList.setBounds(10, 51, 426, 191);

		updateLibraryList();

		Label titleLabel = new Label(shell, SWT.CENTER);
		titleLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 20, SWT.NORMAL));
		titleLabel.setBounds(104, 10, 227, 24);
		titleLabel.setText("Design Decision Library");

		Button btnViewedit = new Button(shell, SWT.NONE);
		btnViewedit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = LibraryList.getSelectionIndex();
				String item = LibraryList.getItem(index);
				String delimeters = "[ ,]+";
				item = item.replace("[", "");
				item = item.replace("]", "");
				String[] items = item.split(delimeters);
				crudwindow w1 = new crudwindow();
				w1.open(lib, items);
			}
		});

		btnViewedit.setBounds(10, 248, 95, 28);
		btnViewedit.setText("View/Edit");

		Button btnCreateNew = new Button(shell, SWT.NONE);
		btnCreateNew.setBounds(131, 248, 95, 28);
		btnCreateNew.setText("Create New");
		btnCreateNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] items = { "Design Name", "null" };
				crudwindow w1 = new crudwindow();
				w1.open(lib, items);

			}
		});

	}

	public void updateLibraryList() {
		LibraryList.removeAll();
		for (Entry<String, HashSet<String>> R : lib.getDesignReqLink().map.entrySet()) {
			LibraryList.add(R.getKey().toString() + " " + R.getValue().toString());
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ArtifactLibrary) {
			updateLibraryList();
		}

	}
}
