package server.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class JRuntimeLoader {
    private static final Logger logger = LoggerFactory.getLogger(JRuntimeLoader.class);
    volatile ClassLoader classLoader;

    public JRuntimeLoader() {
        classLoader = getClass().getClassLoader();
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public Object runScript(String className, String method, Object... args) throws Exception {
        Object obj = classLoader.loadClass(className).newInstance();
        return invokeObjMethod(obj, method, args);
    }

    public Object newObject(String className) throws Exception {
        Object obj = classLoader.loadClass(className).newInstance();
        return obj;
    }

    public Object newObject(String className, Object... args) throws Exception {
        Class cls = classLoader.loadClass(className);
        Constructor constructor = cls.getConstructor(getClassType(args));
        return constructor.newInstance(args);
    }

    private static Object invokeObjMethod(Object obj, String methodName, Object... arg) throws Exception {
        Class[] cs = getClassType(arg);
        Method method = obj.getClass().getMethod(methodName, cs);
        Object result = method.invoke(obj, arg);
        return result;
    }

    private static Class[] getClassType(Object... arg) {
        Class[] cs = new Class[arg.length];
        for (int i = 0; i < arg.length; i++) {
            cs[i] = primitiveClass(arg[i]);
        }
        return cs;
    }

    private static Class primitiveClass(Object object) {
        Class cls = object.getClass();
        if (cls == Integer.class) {
            return int.class;
        } else if (cls == Short.class) {
            return short.class;
        } else if (cls == Long.class) {
            return long.class;
        } else if (cls == Float.class) {
            return float.class;
        } else if (cls == Double.class) {
            return double.class;
        } else if (cls == Byte.class) {
            return byte.class;
        } else {
            return cls;
        }
    }
}
