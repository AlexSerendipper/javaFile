package IoC.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 @author Alex
 @create 2023-02-23-10:36
 */

@Service(value = "annotationService")
public class AnnotationService {
    public void add(){
        System.out.println("annotation service add....");
    }
}
