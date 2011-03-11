package org.kreyssel.tools.loggingagent;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

import org.slf4j.instrumentation.JavassistHelper;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;


/**
 * LoggerAgent add java.util.logging statements to classes at runtime using 
 * <a href="http://www.javassist.org/">javassist</a>.
 *  
 * <p>inspired by http://today.java.net/article/2008/04/22/add-logging-class-load-time-java-instrumentation</p>
 */
public class LoggerAgent implements ClassFileTransformer {

    /** classes to always not to instrument */
    static final String[] DEFAULT_EXCLUDES = new String[] {"com/sun/", "sun/", "java/", "javax/", "org/slf4j" };

    /** only this classes should instrument or leave empty to instrument all classes that not excluded */
    static final String[] INCLUDES = new String[] {
           // "org/bouncycastle/crypto/encodings/", "org/bouncycastle/jce/provider/JCERSACipher"
        };

    /** the jul logger definition */
    static final String def = "private static java.util.logging.Logger _log;";

    /** the jul logging if statement  */
    static final String ifLog = "if (_log.isLoggable(java.util.logging.Level.INFO))";

    /**
     * add agent
     */
    public static void premain( final String agentArgument, final Instrumentation instrumentation ) {
        instrumentation.addTransformer( new LoggerAgent() );
    }

    /**
     * instrument class
     */
    public byte[] transform( final ClassLoader loader, final String className, final Class clazz,
        final java.security.ProtectionDomain domain, final byte[] bytes ) {

        for( String include : INCLUDES ) {

            if( className.startsWith( include ) ) {
                return doClass( className, clazz, bytes );
            }
        }

        for( int i = 0; i < DEFAULT_EXCLUDES.length; i++ ) {

            if( className.startsWith( DEFAULT_EXCLUDES[i] ) ) {
                return bytes;
            }
        }

        return doClass( className, clazz, bytes );
    }

    /**
     * instrument class with javasisst
     */
    private byte[] doClass( final String name, final Class clazz, byte[] b ) {
        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;

        try {
            cl = pool.makeClass( new java.io.ByteArrayInputStream( b ) );

            if( cl.isInterface() == false ) {

                CtField field = CtField.make( def, cl );
                String getLogger = "java.util.logging.Logger.getLogger(" + name.replace( '/', '.' ) +
                    ".class.getName());";
                cl.addField( field, getLogger );

                CtBehavior[] methods = cl.getDeclaredBehaviors();

                for( int i = 0; i < methods.length; i++ ) {

                    if( methods[i].isEmpty() == false ) {
                        doMethod( methods[i] );
                    }
                }

                b = cl.toBytecode();
            }
        } catch( Exception e ) {
            System.err.println( "Could not instrument  " + name + ",  exception : " + e.getMessage() );
        } finally {

            if( cl != null ) {
                cl.detach();
            }
        }

        return b;
    }

    /**
     * modify code and add log statements
     */
    private void doMethod( final CtBehavior method ) throws NotFoundException, CannotCompileException {

        String signature = JavassistHelper.getSignature( method );
        String returnValue = JavassistHelper.returnValue( method );

        method.insertBefore( ifLog + "_log.info(\">> " + signature + "\");" );

        method.insertAfter( ifLog + "_log.info(\"<< " + signature + returnValue + "\");" );
    }
}
