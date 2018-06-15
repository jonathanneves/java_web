package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Disciplina;

@FacesConverter("disciplinaConverter")
public class DisciplinaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if(value != null && !value.isEmpty()) {
			return (Disciplina) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		//Disciplina d = (Disciplina)value;
		//return d.toString();
		if (value instanceof Disciplina) {
			Disciplina d = (Disciplina) value;
			if(d != null && d instanceof Disciplina && d.getId() != null) {
				uiComponent.getAttributes().put( d.getId().toString(), d);
				return d.getId().toString();
			}
		}
		return "";
	}
}
