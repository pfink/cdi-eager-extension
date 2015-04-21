package de.pfink.cdi.extensions.eager;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Makes a CDI bean eager (= initialized at app startup)
 * @author Thomas Andraschko
 * @see http://ovaraksin.blogspot.nl/2013/02/eager-cdi-beans.html
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
public @interface Eager
{
}
