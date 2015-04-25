/**
 * (2015) National Chengchi University, Department of Computer Science
 */
package edu.nccu.cs.pn2.mom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Setup ActiveMQ Broker URL
 * Default: failover:(tcp://localhost:61616)
 * 
 * @author Chun-Feng Liao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Broker
{
    String value() default "tcp://localhost:1883";
}
