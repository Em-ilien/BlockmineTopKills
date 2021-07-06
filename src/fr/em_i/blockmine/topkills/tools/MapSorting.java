package fr.em_i.blockmine.topkills.tools;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
 
public class MapSorting {
    /**
    * Sort the given map by the value in each entry.
    * @param map - map of comparable values.
    * @return A new list with the sort result.
    */
    public static <T, V extends Comparable<V>> List<Entry<T, V>> sortedValues(Map<T, V> map) {
        return sortedValues(map, Comparator.<V>naturalOrder());
    }
 
    /**
    * Sort the given map by the value in each entry.
    * @param map - map of comparable values.
    * @param valueComparator - object for comparing each values.
    * @return A new list with the sort result.
    */
    public static <T, V> List<Entry<T, V>> sortedValues(Map<T, V> map, Comparator<V> valueComparator) {
        return map.entrySet().stream().
          sorted(Comparator.comparing(Entry::getValue, valueComparator)).
          collect(Collectors.toList());
    }
 
    /**
    * Retrieve every key in the entry list in order.
    * @param entryList - the entry list.
    * @return Every key in order.
    */
    public static <T, V> List<T> keys(List<Entry<T, V>> entryList) {
        return entryList.stream().map(Entry::getKey).collect(Collectors.toList());
    }
 
    /**
    * Retrieve every value in the entry list in order.
    * @param entryList - the entry list.
    * @return Every value in order.
    */
    public static <T, V> List<V> values(List<Entry<T, V>> entryList) {
        return entryList.stream().map(Entry::getValue).collect(Collectors.toList());
    }
}