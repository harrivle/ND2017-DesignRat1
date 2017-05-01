package codetaggingtool.parts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import net.mv.tutorial.annotation.AnnotationConstants;
import net.mv.tutorial.annotation.ArtifactInfo;
import net.mv.tutorial.annotation.EditorUtil;
import parser.ArtifactLibrary;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;

import handlers.librarywindow;

import org.eclipse.swt.widgets.Text;

public class CodeTagger {
	private Label myLabelInView;
	private Text ProjectText;
	private Text FileText;
	private Text MethodText;
	private Text CommentsBox;

	ArtifactLibrary lib; // = new ArtifactLibrary();
	private Composite tab1Composite;
	private Composite tab2Composite;
	
	@PostConstruct
	public void createPartControl(Composite parent) {
		//make artifact library
		lib = new ArtifactLibrary();
		
		System.out.println("Enter in SampleE4View postConstruct");
		parent.setLayout(null);		
	
		TabFolder folder = new TabFolder(parent, SWT.BORDER);
		folder.setSize(501, 349);
		folder.setLocation(0, 10);
		TabItem tab1 = new TabItem(folder, SWT.NONE);
		tab1.setText("Code Tagger");
		tab1Composite = new Composite(folder, SWT.NONE);
		makeCodeTagger(tab1Composite);
		tab1.setControl(tab1Composite);
		
		TabItem tab2 = new TabItem(folder, SWT.NONE);
		tab2.setText("Main Menu");
		tab2Composite = new Composite(folder, SWT.NONE);
		makeMainMenu(tab2Composite);

		tab2.setControl(tab2Composite);
		
		folder.pack();
		
	}
	
	public void makeMainMenu(Composite parent) {
		System.out.println("Building the main menu");
		tab2Composite.setLayout(null);
		
		Button launchButton = new Button(parent, SWT.PUSH);
		launchButton.setBounds(124, 10, 263, 43);
		launchButton.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		launchButton.setText("Launch Design Decision Library");
		launchButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Launch Button Pushed");
				try{
					librarywindow window = new librarywindow(lib);
					window.open();
				} catch (Exception e2){
					e2.printStackTrace();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	public void makeCodeTagger(Composite parent) {
		System.out.println("Building the code tagger.");
		tab1Composite.setLayout(null);
		
		Label lblNewLabel_3 = new Label(parent, SWT.NONE);
		lblNewLabel_3.setBounds(5, 5, 125, 18);
		lblNewLabel_3.setFont(SWTResourceManager.getFont(".SF NS Text", 14, SWT.NORMAL));
		lblNewLabel_3.setBounds(165, 18, 150, 28);
		lblNewLabel_3.setText("Code Tagging Tool");
		
				myLabelInView = new Label(parent, SWT.BORDER);
				myLabelInView.setBounds(5, 43, 106, 16);
				myLabelInView.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
				myLabelInView.setBounds(16, 20, 118, 19);
				myLabelInView.setText("Design Rationales");
		
		List DesignChoiceList = new List(parent, SWT.BORDER);
		DesignChoiceList.setBounds(10, 45, 134, 223);
		//ArrayList<String> S = new ArrayList<>();
		for (String id : lib.getDesignDecisions().map.keySet()) {
			DesignChoiceList.add(id);;
		}
		//DesignChoiceList.setItem(S);
		DesignChoiceList.setBounds(10, 45, 124, 223);
		
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setBounds(5, 81, 45, 16);
		lblNewLabel.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		lblNewLabel.setBounds(200, 56, 69, 14);
		lblNewLabel.setText("Project");
		
		ProjectText = new Text(parent, SWT.BORDER);
		ProjectText.setBounds(226, 80, 64, 19);
		ProjectText.setBounds(165, 76, 134, 28);
		
		Label lblNewLabel_1 = new Label(parent, SWT.NONE);
		lblNewLabel_1.setBounds(5, 105, 24, 16);
		lblNewLabel_1.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		lblNewLabel_1.setBounds(213, 110, 69, 14);
		lblNewLabel_1.setText("File");
		
		FileText = new Text(parent, SWT.BORDER);
		FileText.setBounds(226, 104, 64, 19);
		FileText.setBounds(165, 130, 134, 28);
		
		Label lblNewLabel_2 = new Label(parent, SWT.NONE);
		lblNewLabel_2.setBounds(5, 129, 48, 16);
		lblNewLabel_2.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(200, 161, 59, 14);
		lblNewLabel_2.setText("Method");
		
		MethodText = new Text(parent, SWT.BORDER);
		MethodText.setBounds(226, 128, 64, 19);
		MethodText.setBounds(165, 181, 134, 28);
		
		Label lblComment = new Label(parent, SWT.NONE);
		lblComment.setBounds(5, 152, 65, 16);
		lblComment.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		lblComment.setBounds(371, 34, 79, 14);
		lblComment.setText("Comments");
		
		CommentsBox = new Text(parent, SWT.BORDER | SWT.MULTI);
		CommentsBox.setBounds(226, 152, 66, 16);
		CommentsBox.setBounds(325, 56, 156, 193);
		
		
		// Add buttons here
		Button tagButton = new Button(parent, SWT.PUSH);
		tagButton.setBounds(135, 173, 86, 28);
		tagButton.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		tagButton.setText("Tag Code");
		tagButton.setBounds(184, 227, 98, 41);
		tagButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Button Pushed");
				ArtifactInfo info = new ArtifactInfo("abc", "def");
				info.addAttribute(AnnotationConstants.FILE, FileText.getText());
				info.addAttribute(AnnotationConstants.LINE, MethodText.getText());
				info.addAttribute(AnnotationConstants.PROJECT, ProjectText.getText());
				int choice = DesignChoiceList.getSelectionIndex();
				EditorUtil.executeAction(info, DesignChoiceList.getItem(choice), CommentsBox.getText());
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	@Focus
	public void setFocus() {
		myLabelInView.setFocus();

	}

	/**
	 * This method is kept for E3 compatiblity. You can remove it if you do not
	 * mix E3 and E4 code. <br/>
	 * With E4 code you will set directly the selection in ESelectionService and
	 * you do not receive a ISelection
	 * 
	 * @param s
	 *            the selection received from JFace (E3 mode)
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection s) {
		if (s.isEmpty())
			return;

		if (s instanceof IStructuredSelection) {
			IStructuredSelection iss = (IStructuredSelection) s;
			if (iss.size() == 1)
				setSelection(iss.getFirstElement());
			else
				setSelection(iss.toArray());
		}
	}

	/**
	 * This method manages the selection of your current object. In this example
	 * we listen to a single Object (even the ISelection already captured in E3
	 * mode). <br/>
	 * You should change the parameter type of your received Object to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current object received
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object o) {

		// Remove the 2 following lines in pure E4 mode, keep them in mixed mode
		if (o instanceof ISelection) // Already captured
			return;

		// Test if label exists (inject methods are called before PostConstruct)
		if (myLabelInView != null)
			myLabelInView.setText("Current single selection class is : " + o.getClass());
	}

	/**
	 * This method manages the multiple selection of your current objects. <br/>
	 * You should change the parameter type of your array of Objects to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current array of objects received in case of multiple selection
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object[] selectedObjects) {

		// Test if label exists (inject methods are called before PostConstruct)
		if (myLabelInView != null)
			myLabelInView.setText("This is a multiple selection of " + selectedObjects.length + " objects");
	}
}
