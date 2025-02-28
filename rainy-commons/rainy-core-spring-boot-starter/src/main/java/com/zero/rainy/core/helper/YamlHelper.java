package com.zero.rainy.core.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Yaml 工具
 *
 * @author Zero.
 * <p> Created on 2025/2/28 07:50 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YamlHelper {

    /**
     * Binds YAML content to a specified class type.
     *
     * @param yamlContent The YAML content as a string
     * @param targetClass The class to bind the properties to
     * @param <T>         The type of class
     * @return An instance of the class with properties set from the YAML content
     * @throws IOException If the YAML content cannot be parsed
     */
    public static <T> T bind(String yamlContent, Class<T> targetClass) throws IOException {
        return bind(yamlContent, "", targetClass);
    }

    /**
     * Binds YAML content to a specified class type with a prefix.
     *
     * @param yamlContent The YAML content as a string
     * @param prefix      The prefix to use for property binding (similar to prefix in @ConfigurationProperties)
     * @param targetClass The class to bind the properties to
     * @param <T>         The type of class
     * @return An instance of the class with properties set from the YAML content
     * @throws IOException If the YAML content cannot be parsed
     */
    public static <T> T bind(String yamlContent, String prefix, Class<T> targetClass) throws IOException {
        if (yamlContent == null || targetClass == null) {
            throw new IllegalArgumentException("YAML content and target class cannot be null");
        }

        // Parse YAML content to property sources
        List<PropertySource<?>> propertySources = parseYaml(yamlContent);

        // Convert property sources to configuration property sources
        Iterable<ConfigurationPropertySource> configurationPropertySources =
                ConfigurationPropertySources.from(propertySources);

        // Create a binder with our configuration property sources
        Binder binder = new Binder(configurationPropertySources,
                new PropertySourcesPlaceholdersResolver(propertySources));

        // Bind the properties to an instance of the target class
        String bindingPrefix = StringUtils.hasText(prefix) ? prefix : "";
        return binder.bindOrCreate(bindingPrefix, targetClass);
    }

    /**
     * Loads YAML content from a string and converts it to Spring PropertySources.
     *
     * @param yamlContent The YAML content as a string
     * @return A list of PropertySource objects representing the YAML content
     * @throws IOException If the YAML content cannot be parsed
     */
    private static List<PropertySource<?>> parseYaml(String yamlContent) throws IOException {
        // Create a resource from the YAML content
        ByteArrayResource resource = new ByteArrayResource(
                yamlContent.getBytes(StandardCharsets.UTF_8), "yamlContent");

        // Use Spring's YamlPropertySourceLoader to parse the YAML
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        return loader.load("yaml-content", resource);
    }

    /**
     * Alternative method that doesn't rely on Spring's YamlPropertySourceLoader.
     * This can be used if the application doesn't have Spring Boot's YAML support.
     * This method requires adding a YAML parser like SnakeYAML to your dependencies.
     *
     * @param yamlContent The YAML content as a string
     * @param targetClass The class to bind the properties to
     * @param <T>         The type of class
     * @return An instance of the class with properties set from the YAML content
     */
    public static <T> T bindWithoutSpringYamlLoader(String yamlContent, Class<T> targetClass) {
        // Parse YAML content to Map (requires SnakeYAML)
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
        Map<String, Object> properties = yaml.load(yamlContent);

        // Flatten nested properties
        Map<String, Object> flattenedMap = flattenMap(properties);

        // Create configuration property source
        ConfigurationPropertySource source = new MapConfigurationPropertySource(flattenedMap);

        // Create a binder with our configuration property source
        Binder binder = new Binder(Collections.singleton(source));

        // Bind the properties to an instance of the target class
        return binder.bindOrCreate("", targetClass);
    }

    /**
     * Flattens a nested map structure to a single-level map with dot-separated keys.
     *
     * @param map The nested map to flatten
     * @return A flattened map
     */
    private static Map<String, Object> flattenMap(Map<String, Object> map) {
        Map<String, Object> result = new LinkedHashMap<>();
        doFlattenMap(result, map, "");
        return result;
    }

    private static void doFlattenMap(Map<String, Object> result, Map<String, Object> source, String path) {
        source.forEach((key, value) -> {
            String newKey = path + (path.isEmpty() ? "" : ".") + key;
            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                doFlattenMap(result, nestedMap, newKey);
            } else {
                result.put(newKey, value);
            }
        });
    }
}
