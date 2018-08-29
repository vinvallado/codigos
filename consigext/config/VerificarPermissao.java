package br.mil.fab.consigext.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.mil.fab.consigext.enums.RoleEnum;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VerificarPermissao {
	
	RoleEnum[] permissoes() default {};
}
