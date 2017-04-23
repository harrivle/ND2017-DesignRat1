package codetaggingtool.parts;

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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;

public class CodeTagger {
	private Label myLabelInView;
	private Text ProjectText;
	private Text FileText;
	private Text MethodText;
	private Text CommentsBox;

	@PostConstruct
	public void createPartControl(Composite parent) {
		System.out.println("Enter in SampleE4View postConstruct");
		parent.setLayout(null);

		myLabelInView = new Label(parent, SWT.BORDER);
		myLabelInView.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		myLabelInView.setBounds(16, 20, 118, 19);
		myLabelInView.setText("Design Rationales");
		
		
		// Add buttons here
		Button tagButton = new Button(parent, SWT.PUSH);
		tagButton.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		tagButton.setText("Tag Code");
		tagButton.setBounds(184, 227, 98, 41);
		
		List DesignChoiceList = new List(parent, SWT.BORDER);
		DesignChoiceList.setItems(new String[] {"Design Choice 1", "Design Choice 2", "Design Choice 3"});
		DesignChoiceList.setBounds(10, 45, 124, 223);
		
		ProjectText = new Text(parent, SWT.BORDER);
		ProjectText.setBounds(165, 76, 134, 28);
		
		FileText = new Text(parent, SWT.BORDER);
		FileText.setBounds(165, 130, 134, 28);
		
		MethodText = new Text(parent, SWT.BORDER);
		MethodText.setBounds(165, 181, 134, 28);
		
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		lblNewLabel.setBounds(200, 56, 69, 14);
		lblNewLabel.setText("Project");
		
		Label lblNewLabel_1 = new Label(parent, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		lblNewLabel_1.setBounds(213, 110, 69, 14);
		lblNewLabel_1.setText("File");
		
		Label lblNewLabel_2 = new Label(parent, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(200, 161, 59, 14);
		lblNewLabel_2.setText("Method");
		
		Label lblNewLabel_3 = new Label(parent, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont(".SF NS Text", 14, SWT.NORMAL));
		lblNewLabel_3.setBounds(165, 18, 150, 28);
		lblNewLabel_3.setText("Code Tagging Tool");
		
		Label lblComment = new Label(parent, SWT.NONE);
		lblComment.setFont(SWTResourceManager.getFont(".SF NS Text", 12, SWT.NORMAL));
		lblComment.setBounds(371, 34, 79, 14);
		lblComment.setText("Comments");
		
		CommentsBox = new Text(parent, SWT.BORDER | SWT.MULTI);
		CommentsBox.setBounds(325, 56, 156, 193);
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
