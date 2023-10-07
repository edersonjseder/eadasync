package com.ead.course.specifications;

import com.ead.course.models.Module;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path="title", spec= Like.class)
public interface ModuleSpec extends Specification<Module> {
}
