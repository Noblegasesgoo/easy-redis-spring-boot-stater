package com.noblegasesgoo.platform.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author: noblegasesgoo
 * @CreateDate: 2022年11月23日 16:06
 * @ProjectName: easy-redis-spring-boot-starter
 * @version: 0.0.1
 * @FileName: AnnotationResolver
 * @Description: 注解解析器
 */

public class AnnotationResolver {

    private static AnnotationResolver resolver;

    public AnnotationResolver() {
    }

    public static AnnotationResolver newInstance() {
        return resolver == null ? (resolver = new AnnotationResolver()) : resolver;
    }

    public Object resolver(JoinPoint joinPoint, String str) {

        if (str == null) {

            return null;
        } else {

            Object value = null;

            String[] arr = str.split("_");
            String[] arrCopy = arr;
            int length = arr.length;

            for(int i = 0; i < length; i ++) {

                String strA = arrCopy[i];

                if (strA.matches("#\\{\\D*\\}")) {

                    // 先替换 { 为空，再替换 } 为空
                    String newStr = strA.replaceAll("#\\{", "").replaceAll("\\}", "");

                    if (newStr.contains(".")) {
                        try {
                            if (Objects.isNull(value)) {
                                value = value + "_" + this.complexResolver(joinPoint, newStr);
                            } else {
                                value = this.complexResolver(joinPoint, newStr);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (null != value) {
                        value = value + "_" + this.simpleResolver(joinPoint, newStr);
                    } else {
                        value = this.simpleResolver(joinPoint, newStr);
                    }
                } else if (null != value) {
                    value = value + "_" + strA;
                } else {
                    value = strA;
                }
            }

            return value;
        }
    }

    private Object complexResolver(JoinPoint joinPoint, String str) throws Exception {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        // 获取被注解标注的方法名称
        String[] names = methodSignature.getParameterNames();
        // 获取被注解标注的方法的参数
        Object[] args = joinPoint.getArgs();
        // 参数根据.切割
        String[] strs = str.split("\\.");

        for(int i = 0; i < names.length; i ++) {
            // 比对查找当前方法
            if (strs[0].equals(names[i])) {
                Object obj = args[i];
                // 获取对应方法
                Method dmethod = obj.getClass().getDeclaredMethod(this.getMethodName(strs[1]), (Class[])null);
                // 调用该方法
                Object value = dmethod.invoke(args[i]);
                return this.getValue(value, 1, strs);
            }
        }

        return null;
    }

    private Object getValue(Object obj, int index, String[] strs) {
        try {
            if (obj != null && index < strs.length - 1) {
                Method method = obj.getClass().getDeclaredMethod(this.getMethodName(strs[index + 1]), (Class[])null);
                obj = method.invoke(obj);
                this.getValue(obj, index + 1, strs);
            }

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getMethodName(String name) {
        return "get" + name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
    }

    private Object simpleResolver(JoinPoint joinPoint, String str) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for(int i = 0; i < names.length; ++i) {
            if (str.equals(names[i])) {
                return args[i];
            }
        }

        return null;
    }

}
