package codetaggingtool.parts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jdt.internal.compiler.util.Util;
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
import handlers.reqlist;
import handlers.reqwindow;

import org.eclipse.swt.widgets.Text;

public class CodeTagger implements Observer {
	private Label myLabelInView;
	private Text FileText;
	private Text MethodText;
	private Text CommentsBox;
	private Text PackageText;
	private Text ProjectName;
	public int libIsInitialized;

	ArtifactLibrary lib; // = new ArtifactLibrary();
	List DesignChoiceList;
	HashMap<String, java.util.List<String>> tagInfo;
	private Composite tab1Composite;
	private Composite tab2Composite;
	
	@PostConstruct
	public void createPartControl(Composite parent) {
		libIsInitialized = 0;
		
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
		
		Label projectNameLabel = new Label(parent, SWT.NONE);
		projectNameLabel.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		projectNameLabel.setBounds(40, 10, 360, 15);
		projectNameLabel.setText("Input name of project before initializing Artifact Library:"); 
		
		ProjectName = new Text(parent, SWT.BORDER);
		ProjectName.setBounds(124, 30, 263, 20);
			
		Button startButton = new Button(parent, SWT.PUSH);
		startButton.setBounds(124, 60, 263, 43);
		startButton.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		startButton.setText("Initialize Artifact Library");
		startButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(libIsInitialized == 1) return;
				// TODO Auto-generated method stub
				System.out.println("Start Button Pushed");
				try{
					lib = new ArtifactLibrary(ProjectName.getText());
					updateContent();
					initializeTags();
					libIsInitialized = 1;
				} catch (Exception e2){
					e2.printStackTrace();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}		
		});
		
		Button launchButton = new Button(parent, SWT.PUSH);
		launchButton.setBounds(124, 110, 263, 43);
		launchButton.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		launchButton.setText("Launch Design Decision Library");
		launchButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(libIsInitialized == 0) return;
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
	
		Button reqButton = new Button(parent, SWT.PUSH);
		reqButton.setBounds(124, 160, 263, 43);
		reqButton.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		reqButton.setText("View Requirement Descriptions");
		reqButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(libIsInitialized == 0) return;
				try{
					reqlist reqList = new reqlist();
					reqList.open(lib);
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
		lblNewLabel_3.setFont(SWTResourceManager.getFont(".SF NS Text", 14, SWT.NORMAL));
		lblNewLabel_3.setBounds(165, 18, 150, 28);
		lblNewLabel_3.setText("Code Tagging Tool");
		
		myLabelInView = new Label(parent, SWT.BORDER);
		myLabelInView.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		myLabelInView.setBounds(16, 20, 118, 19);
		myLabelInView.setText("Design Rationales");
		
		DesignChoiceList = new List(parent, SWT.BORDER);
		DesignChoiceList.setBounds(10, 45, 134, 223);	
		//add design decisions from library to list
		DesignChoiceList.setBounds(10, 45, 124, 223);
		
		Label lblNewLabel_1 = new Label(parent, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		lblNewLabel_1.setBounds(213, 110, 69, 14);
		lblNewLabel_1.setText("File");
		
		/*PackageText = new Text(parent, SWT.BORDER);
		PackageText.setBounds(165, 76, 134, 20);
		Label packageLabel = new Label(parent, SWT.NONE);
		packageLabel.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		packageLabel.setBounds(213, 110, 69, 14);
		packageLabel.setText("File");*/
		
		FileText = new Text(parent, SWT.BORDER);
		FileText.setBounds(165, 130, 134, 28);
		
		//method box and label
		Label lblNewLabel_2 = new Label(parent, SWT.NONE);
		lblNewLabel_2.setBounds(5, 129, 48, 16);
		lblNewLabel_2.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(200, 161, 59, 14);
		lblNewLabel_2.setText("Method");
		
		MethodText = new Text(parent, SWT.BORDER);
		MethodText.setBounds(226, 128, 64, 19);
		MethodText.setBounds(165, 181, 134, 28);
		
		//comment box and label
		Label lblComment = new Label(parent, SWT.NONE);
		lblComment.setBounds(5, 152, 65, 16);
		lblComment.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		lblComment.setBounds(371, 34, 79, 14);
		lblComment.setText("Comments");
		
		CommentsBox = new Text(parent, SWT.BORDER | SWT.MULTI);
		CommentsBox.setBounds(226, 152, 66, 16);
		CommentsBox.setBounds(325, 56, 156, 193);
		
	
		DesignChoiceList.addSelectionListener(new SelectionListener() {		
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] choice = DesignChoiceList.getSelection();
				FileText.setText(tagInfo.get(choice[0]).get(0));
				MethodText.setText(tagInfo.get(choice[0]).get(1));
				ProjectName.setText(tagInfo.get(choice[0]).get(2));
				CommentsBox.setText(tagInfo.get(choice[0]).get(3));
			}	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub		
			}
		});
		// Add buttons here
		Button tagButton = new Button(parent, SWT.PUSH);
		tagButton.setBounds(184, 240, 98, 28);
		tagButton.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		tagButton.setText("Tag Code");
		tagButton.setBounds(184, 227, 98, 41);
		tagButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(libIsInitialized == 0) return;
				System.out.println("Button Pushed");
				ArtifactInfo info = new ArtifactInfo("abc", "def");
				
				String file = FileText.getText();
				String method = MethodText.getText();
				String project = ProjectName.getText();
				// String package = PackageText.getText();
				
				info.addAttribute(AnnotationConstants.FILE, file);
				info.addAttribute(AnnotationConstants.LINE, method);
				info.addAttribute(AnnotationConstants.PROJECT, project);
				//if (DesignChoiceList != null) {
				int choice = DesignChoiceList.getSelectionIndex();
				EditorUtil.executeAction(info, DesignChoiceList.getItem(choice), CommentsBox.getText());
				//}
				
				if (DesignChoiceList.getSelection()[0] != null) {
					java.util.List<String> list = new ArrayList();
					list.add(FileText.getText());
					list.add(MethodText.getText());
					list.add(ProjectName.getText());
					list.add(CommentsBox.getText());
					lib.setTagInfo(DesignChoiceList.getSelection()[0], list);
				}
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


public void initializeTags() {
	for(String design : lib.getTagInfo().keySet()) {
		java.util.List<String> contents = lib.getTagInfo().get(design);
	//for(<String, java.util.List<String>> contents : lib.getTagInfo()) {
		ArtifactInfo info = new ArtifactInfo("abc", "def");
		String file = contents.get(0);
		String method = contents.get(1);
		//String pack = "";
		String comments = contents.get(3);
		info.addAttribute(AnnotationConstants.FILE, file);
		info.addAttribute(AnnotationConstants.LINE, method);
		info.addAttribute(AnnotationConstants.PROJECT, ProjectName.getText());
		EditorUtil.executeAction(info, design, comments);
	}
}

	public void updateContent() {
		tagInfo = lib.getTagInfo();
		for (String id : tagInfo.keySet()) {
			DesignChoiceList.add(id);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		updateContent();
	}
}
