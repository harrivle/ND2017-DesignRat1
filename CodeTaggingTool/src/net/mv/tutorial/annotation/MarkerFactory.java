package net.mv.tutorial.annotation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jface.text.Position;

public class MarkerFactory {



	public static IMarker createMethodMarker(IResource res, Position position,
			IMethod method, String eventType, String probe, boolean error, String DesignChoice, String Comments)
			throws CoreException {
		IMarker m = createMarker(eventType, probe, res, position,
				error ? AnnotationConstants.ANNOTATION1 : AnnotationConstants.ANNOTATION2, DesignChoice, Comments);
	
		m.setAttribute("name", method.getElementName());
		return m;
	}


	private static IMarker createMarker(String eventType, String probe,
			IResource res, Position position, String identifier, String DesignChoice, String Comments)
			throws CoreException {
		IMarker marker = res.createMarker(identifier);
		marker.setAttribute("charStart", position.getOffset());
		marker.setAttribute("charEnd",
				position.getOffset() + position.getLength());
		String messageText = "\"" + DesignChoice + "\"" + ": " + Comments;
		marker.setAttribute("message", messageText);
		return marker;
	}

}
