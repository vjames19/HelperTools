/* ============== HelperTools ==============
 * Initial developer: Lukas Diener <lukas.diener@hotmail.com>
 *
 * =====
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE - Version 2
 *
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this license document, and changing it is allowed as long
 * as the name is changed.
 *
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 * TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 * 0. You just DO WHAT THE FUCK YOU WANT TO.
 *
 * =====
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
 *
 */

package info.michaelkohler.helpertools.collections;

import info.michaelkohler.helpertools.string.StringHelper;
import static info.michaelkohler.helpertools.tools.Validator.checkArgument;
import static info.michaelkohler.helpertools.tools.Validator.checkNotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The |CollectionHelper| is a static class which helps
 * performing batch operations on collections.
 *
 * @author Lukas Diener, Victor J. Reventos
 * @version 0.0.2
 */
public final class CollectionHelper {

    /**
     * Empty private constructor, no instantiation needed.
     */
    private CollectionHelper() {
        throw new AssertionError("Cannot instantiate this class");
    }

    /**
     * Collects a specific property of elements in a collection
     * and returns them as a Collection. It's possible to access
     * private properties.
     *
     * @throws NoSuchFieldException Signals that the class doesn't
     * have a field of a specified name.
     *
     * @param collection the collection to be traversed
     * @param <T> typed param for the collection
     * @param property the name of the property to be extracted
     *
     * @return a Collection of the selected properties
     */
    public static <T> Collection<Object> all(Collection<T> collection, String property)
        throws NoSuchFieldException {
        checkNotNull(collection, "collection cannot be null");
        checkArgument(!StringHelper.isNullOrEmpty(property),
                        "property cannot be null or empty");

        Collection<Object> properties = new ArrayList<Object>();
        for (T element : collection) {
            Class<? extends Object> elementClass = element.getClass();
            Field field = elementClass.getDeclaredField(property);

            //Make the field accessible even if private
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            try {
                properties.add(field.get(element));
            } catch (IllegalArgumentException e) {
                // this won't ever happen.
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // this as well.
                e.printStackTrace();
            }
        }

        return properties;
    }

    /**
     * Calls a function for every element in a collection
     * with the element as first parameter and the index as
     * the second.
     *
     * <pre>
     * {@code
     * // be myCollection a |Collection| of class myClass, which
     * // contains a property named myProperty of type |String|.
     * CollectionHelper.each(stringCollection, new IFunction<String>() {
     *   public void execute(String element, int index) {
     *      element.trim();
     *   }
     * });
     * }
     * </pre>
     *
     * @param collection the collection to be traversed
     * @param <T> typed param for the collection
     * @param function the method to be executed
     */
    public static <T> void each(Collection<T> collection, IFunction<T> function) {
        checkNotNull(collection, "collection cannot be null");
        checkNotNull(function, "function cannot be null");

        int index = 0;
        for (T element : collection) {
            function.execute(element, index);
            index++;
        }
    }
}