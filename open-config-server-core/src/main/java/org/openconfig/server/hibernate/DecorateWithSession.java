package org.openconfig.server.hibernate;

import java.lang.annotation.*;

/**
 * To be used at a method level when the method needs to be decorated with a hibernate session around it.
 *
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DecorateWithSession {
}
