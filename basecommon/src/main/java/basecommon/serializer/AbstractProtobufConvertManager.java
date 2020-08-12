package basecommon.serializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author 张富华
 * @date 2020/8/6 17:29
 */
public abstract class AbstractProtobufConvertManager {

    private static Map<String, PbConvertor> convertorMap = new ConcurrentHashMap<>();

    private static Map<String, PbConvertor> reverseConvertorMap = new ConcurrentHashMap<>();

    private static Map<String, String> parseClassMap = new ConcurrentHashMap<>();

    public static PbConvertor fetchConvertor(String clazz) {
        return convertorMap.get(clazz);
    }

    public static PbConvertor fetchReversedConvertor(String clazz) {
        return reverseConvertorMap.get(clazz);
    }

    public static String fetchParseFormClass(String className) {
        return parseClassMap.get(className);
    }

    public abstract void initProtobufMapInfo();

    protected void putConvertMapItem(String clazzName, PbConvertor convertor){
        convertorMap.put(clazzName, convertor);
    }

    protected void putReverseConvertorMapItem(String clazzName, PbConvertor convertor) {
        reverseConvertorMap.put(clazzName, convertor);
    }

    protected void putParseClassMap(String className, String parseClassName) {
        parseClassMap.put(className, parseClassName);
    }
}