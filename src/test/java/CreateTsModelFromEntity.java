import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.Column;

import com.doc.manage.entity.Role;

public class CreateTsModelFromEntity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Class aClass = Role.class;
		Field[] fields = aClass.getDeclaredFields();
		
		for(Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			for(Annotation annotation : annotations){
			    if(annotation instanceof Column){
//			    	Column myAnnotation = (Column) annotation;
//			        System.out.println("name: " + myAnnotation.name());
			        switch(field.getType().getSimpleName()) {
			        case "String":
			        	System.out.println(field.getName() + ": string;");
			        	break;
			        case "Integer":
			        	System.out.println(field.getName() + ": number;");
			        	break;
			        default:
			        	System.out.println(field.getName() + ":" + field.getType().getSimpleName()+ ";");
			        	break;
			        }
			        
			    }
			}
		}
		

	}

}
